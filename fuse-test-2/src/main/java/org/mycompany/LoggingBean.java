package org.mycompany;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingBean  {
	
	@Bean (name = Bus.DEFAULT_BUS_ID)
	public SpringBus springBus() {
	    SpringBus springBus = new SpringBus();
	    LoggingFeature logFeature = new LoggingFeature();
	    logFeature.setPrettyLogging(true);
	    logFeature.initialize(springBus);
	    springBus.getFeatures().add(logFeature);
	    return springBus;
	}
	

}
