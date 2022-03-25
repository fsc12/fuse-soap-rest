package org.mycompany;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestProducer extends RouteBuilder {



	@Override
	public void configure() throws Exception {

		from("rest:get:blz:/{blz}").transform().simple("${header.blz}").to("seda:processBlz");
		

	}

}
