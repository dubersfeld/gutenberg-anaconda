package com.dub.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import com.dub.spring.controller.config.ServiceConfig;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableBinding(Sink.class)
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);	
	
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
		
		logger.debug("--Application started--");
	
	}
	
	@Autowired 
	private ServiceConfig serviceConfig;
		
	@Bean 
	public RestOperations restTemplate() {
		return new RestTemplate();
	}
		
	
	
}

