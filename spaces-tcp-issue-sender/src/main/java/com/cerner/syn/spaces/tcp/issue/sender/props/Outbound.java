package com.cerner.syn.spaces.tcp.issue.sender.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="sender.outbound", ignoreUnknownFields=false)
public class Outbound {
	private String host;
	private int port;
	private int timeout_ms = 10_000;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {

		this.host = host;
	}

	public int getPort() {

		return port;
	}

	public void setPort(int port) {

		this.port = port;
	}

	public int getTimeout_ms() {

		return timeout_ms;
	}

	public void setTimeout_ms(int timeout_ms) {

		this.timeout_ms = timeout_ms;
	}
}
