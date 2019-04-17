package com.hxqh.ma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lin
 */


/**
 * Debug
 * SpringBoot mode
 */
//@SpringBootApplication
//@ComponentScan(basePackages = "com.hxqh.ma.**.*")
//@RestController
//public class MarketAnalysisServer extends SpringBootServletInitializer implements EmbeddedServletContainerCustomizer {
//    public static void main(String[] args) {
//        SpringApplication.run(MarketAnalysisServer.class, args);
//    }
//
//    @Override
//    public void customize(ConfigurableEmbeddedServletContainer container) {
//        container.setPort(8090);
//    }
//}


/**
 * Release
 * Tomcat mode
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.hxqh.ma.**.*")
@RestController
public class MarketAnalysisServer extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(MarketAnalysisServer.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}