package com.dub.spring.controller.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {

	@Value("${baseBooksUrl}")
	private String baseBooksURL;
	
	@Value("${baseOrdersUrl}")
	private String baseOrdersURL;
	
	@Value("${baseReviewsUrl}")
	private String baseReviewsURL;
	
	@Value("${baseUsersUrl}")
	private String baseUsersURL;
	
	@Value("${spring.redis.host}")
	private String redisServer;
	
	@Value("${spring.redis.port}")
	private int redisPort;


	public String getBaseBooksURL() {
		return baseBooksURL;
	}

	public void setBaseBooksURL(String baseBooksURL) {
		this.baseBooksURL = baseBooksURL;
	}

	public String getBaseOrdersURL() {
		return baseOrdersURL;
	}

	public void setBaseOrdersURL(String baseOrdersURL) {
		this.baseOrdersURL = baseOrdersURL;
	}

	public String getRedisServer() {
		return redisServer;
	}

	public void setRedisServer(String redisServer) {
		this.redisServer = redisServer;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	public String getBaseReviewsURL() {
		return baseReviewsURL;
	}

	public void setBaseReviewsURL(String baseReviewsURL) {
		this.baseReviewsURL = baseReviewsURL;
	}

	public String getBaseUsersURL() {
		return baseUsersURL;
	}

	public void setBaseUsersURL(String baseUsersURL) {
		this.baseUsersURL = baseUsersURL;
	}	
	
	
}
