package com.cerner.syn.spaces.tcp.issue.sender.props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="sender.mllp", ignoreUnknownFields=false)
public class MLLP {
	private String startBytes;
	private String endBytes;
	private String payload;
	
	@Override
	public String toString() {
		return "MLLP [startBytes=" + getStartBytes() + ", payload=" + getPayload() + ", endBytes=" + getEndBytes() + "]";
	}
	
	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {

	    this.payload = payload;
	}

	public String getEndBytes() {

	    return endBytes;
	}

	public void setEndBytes(String endBytes) {

	    this.endBytes = endBytes;
	}

	public String getStartBytes() {

	    return startBytes;
	}

	public void setStartBytes(String startBytes) {

	    this.startBytes = startBytes;
	}
}
