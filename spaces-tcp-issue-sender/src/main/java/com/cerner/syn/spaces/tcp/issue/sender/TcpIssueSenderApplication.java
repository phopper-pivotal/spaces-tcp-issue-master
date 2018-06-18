package com.cerner.syn.spaces.tcp.issue.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class TcpIssueSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcpIssueSenderApplication.class, args);
	}
}
