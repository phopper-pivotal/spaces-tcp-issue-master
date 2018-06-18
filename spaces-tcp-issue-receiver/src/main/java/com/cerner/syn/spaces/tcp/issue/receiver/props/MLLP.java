package com.cerner.syn.spaces.tcp.issue.receiver.props;

import java.io.ByteArrayOutputStream;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="receiver.mllp", ignoreUnknownFields=false)
public class MLLP
{
	private String endBytes;
	
	public byte[] getActualEndBytes()
	{
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		for(String b : endBytes.split(","))
		{
			b = b.trim();
			if(b.length() != 0)
			{
				bos.write(Byte.parseByte(b));
			}
		}
		return bos.toByteArray();
	}
	
	public String getEndBytes()
	{
		return endBytes;
	}
	public void setEndBytes(String endBytes)
	{
		this.endBytes = endBytes;
	}
}
