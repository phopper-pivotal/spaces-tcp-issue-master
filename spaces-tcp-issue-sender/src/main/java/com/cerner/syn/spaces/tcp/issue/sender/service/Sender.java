package com.cerner.syn.spaces.tcp.issue.sender.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

public interface Sender {
	/**
	 * @param startBytes exact start bytes of MLLP Envelope
	 * @param payload exact bytes of payload
	 * @param endBytes exact end bytes of MLLP Envelope
	 */
	public void trySend(byte[] startBytes, byte[] payload, byte[] endBytes) throws IOException;
	
	/**
	 * @param startBytesCommaDelimited a comma-delimited String of decimal bytes indicating the start bytes of MLLP Envelope
	 * @param payload the contents of the envelope
	 * @param endBytesCommaDelimited a comma-delimited String of decimal bytes indicating the end bytes of MLLP Envelope
	 */
	public default void trySend(String startBytesCommaDelimited, String payload, String endBytesCommaDelimited) throws IOException {
		trySend(parseBytes(startBytesCommaDelimited), payload.getBytes(UTF_8), parseBytes(endBytesCommaDelimited));
	}
	
	static byte[] parseBytes(String commaDelimited) {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		for(String b : commaDelimited.split(","))
		{
			b = b.trim();
			if(b.length() != 0)
			{
				bos.write(Byte.parseByte(b));
			}
		}
		return bos.toByteArray();
	}
}