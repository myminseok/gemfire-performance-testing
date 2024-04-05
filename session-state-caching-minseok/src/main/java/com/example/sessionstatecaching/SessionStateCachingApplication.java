package com.example.sessionstatecaching;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.geode.config.annotation.EnableClusterAware;

@SpringBootApplication
@EnableClusterAware
public class SessionStateCachingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SessionStateCachingApplication.class, args);
	}

}

