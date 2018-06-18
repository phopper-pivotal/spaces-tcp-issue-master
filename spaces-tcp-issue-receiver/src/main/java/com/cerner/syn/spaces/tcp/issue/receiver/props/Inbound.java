package com.cerner.syn.spaces.tcp.issue.receiver.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="receiver.inbound", ignoreUnknownFields=false)
public class Inbound
{
	private int port;
	private int timeout_ms = 10_000;
	
	public int getPort()
	{
		return port;
	}
	public void setPort(int port)
	{
		this.port = port;
	}
	public int getTimeout_ms()
	{
		return timeout_ms;
	}
	public void setTimeout_ms(int timeout_ms)
	{
		this.timeout_ms = timeout_ms;
	}
}
