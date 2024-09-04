package com.eCommerce.configurations;


import org.apache.catalina.connector.Connector;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.cloudinary.Cloudinary;

@Configuration
@EnableAsync
public class ProjectConfig {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	ConfigurableServletWebServerFactory webServerFactory() {
	    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
	    factory.addConnectorCustomizers(new TomcatConnectorCustomizer() {
	        @Override
	        public void customize(Connector connector) {
	            connector.setProperty("relaxedQueryChars", "|{}[]");
	        }
	    });
	    return factory;
	}
	
	@Bean
	Cloudinary getCloudinary() {
		Cloudinary cloudinary = new Cloudinary();
		return cloudinary;
	}
}
