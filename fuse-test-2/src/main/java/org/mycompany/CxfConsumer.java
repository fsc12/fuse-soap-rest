package org.mycompany;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cxf.common.message.CxfConstants;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.cxf.binding.soap.SoapFault;
import org.springframework.stereotype.Component;

@Component
public class CxfConsumer extends RouteBuilder  {

	@Override
	public void configure() throws Exception {
		
		
		
		from("seda:processBlz").onException(SoapFault.class).maximumRedeliveries(0).handled(true).process(new Processor() {

            public void process(Exchange exchange) throws Exception {
                SoapFault fault = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, SoapFault.class);
                exchange.getOut().setFault(true);
                StringBuilder errMsg = new StringBuilder();
                errMsg.append("[{").append("\"").append("Fehler").append("\"").append(":").append("\"").append(fault.getMessage()).append("\"").append("}]");
                exchange.getOut().setBody(errMsg.toString());
                exchange.getOut().setHeader("Content-Type", "application/json");
            }
        }).end()
		
		.setHeader(CxfConstants.OPERATION_NAME,	constant("getBank"))
		.setHeader(CxfConstants.OPERATION_NAMESPACE,constant("http://thomas-bayer.com/blz/"))

		.to("cxf://http://www.thomas-bayer.com/axis2/services/BLZService"
				+ "?serviceClass=com.thomas_bayer.blz.BLZServicePortType"
				+ "&wsdlURL=/wsdl/BookService.wsdl")
		
//		.convertBodyTo(DetailsType.class)

		.log("Die Bank ist: ${body[0].getBezeichnung} in  ${body[0].getPlz}  ${body[0].getOrt}")
		.marshal().json(JsonLibrary.Jackson);




	}
	



}


