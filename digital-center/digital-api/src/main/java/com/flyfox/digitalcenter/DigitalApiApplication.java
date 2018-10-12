package com.flyfox.digitalcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages={"com.flyfox"})
public class DigitalApiApplication{
    public static void main( String[] args )
    {
    	SpringApplication.run(DigitalApiApplication.class, args);
    }
}
