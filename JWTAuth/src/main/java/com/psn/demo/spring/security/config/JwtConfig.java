package com.psn.demo.spring.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "application.jwt")
public class JwtConfig {

	private String secret;
	private Integer tokenValidityInDays;
	
	public JwtConfig() {
		// TODO Auto-generated constructor stub
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Integer getTokenValidityInDays() {
		return tokenValidityInDays;
	}

	public void setTokenValidityInDays(Integer tokenValidityInDays) {
		this.tokenValidityInDays = tokenValidityInDays;
	}
	
}
