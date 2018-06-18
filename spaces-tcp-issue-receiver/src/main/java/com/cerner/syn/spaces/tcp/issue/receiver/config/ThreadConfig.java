package com.cerner.syn.spaces.tcp.issue.receiver.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfig
{
	@Bean(destroyMethod="shutdownNow")
	public ExecutorService executor()
	{
		return Executors.newCachedThreadPool();
	}
}
