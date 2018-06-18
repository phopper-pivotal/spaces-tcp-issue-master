package com.cerner.syn.spaces.tcp.issue.sender.service;

import com.cerner.syn.spaces.tcp.issue.sender.props.Outbound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Service
public class TCPSender implements Sender {
	@Autowired Outbound out;

	@Override
	public void trySend(byte[] startBytes, byte[] payload, byte[] endBytes) throws IOException {
		try(Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(out.getHost(), out.getPort()), out.getTimeout_ms());
			socket.setSoTimeout(out.getTimeout_ms());
			
			try(OutputStream os = socket.getOutputStream();
				InputStream is = socket.getInputStream()) {
				final int size = startBytes.length+payload.length+endBytes.length;
				final byte[] sent = ByteBuffer.allocate(size).put(startBytes).put(payload).put(endBytes).array();
				final byte[] received = new byte[size];
				
				os.write(sent);
				os.flush();
				int read;
				for(read = 0; read < size;) {
					final int readThisTime = is.read(received, read, size-read);
					if (readThisTime > 0) {
						read += readThisTime;
					} else { //We didn't receive any data
						break;
					}
				}
				
				assertArrayEquals(sent, Arrays.copyOf(received, read), "Expecting the receiver to echo the bytes back to us");
				try  {
					assertEquals(-1,  is.read(), "Not expecting any more data!");
				} catch(@SuppressWarnings("unused") SocketTimeoutException e){/*expected*/}
			}
		}
	}
	
}
