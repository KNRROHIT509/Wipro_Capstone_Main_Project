package com.example.knr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServersApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServersApplication.class, args);
	}

}
