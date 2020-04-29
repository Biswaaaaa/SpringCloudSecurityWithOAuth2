package com.barman;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//This class is not requored. This was needed for JSP page

@Configuration
public class WebContextConfiguration {

	
	//This class and the following bean was created to avoid the jaxb issue. As per StackOverflow
	@Bean
	  public TomcatServletWebServerFactory tomcatFactory() {
	    return new TomcatServletWebServerFactory() {
	      @Override
	      protected void postProcessContext(Context context) {
	        ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
	      }
	    };
	  }
}
