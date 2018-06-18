package com.cerner.syn.spaces.tcp.issue.receiver.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cerner.syn.spaces.tcp.issue.receiver.props.Inbound;
import com.cerner.syn.spaces.tcp.issue.receiver.props.MLLP;

@Service
public class TcpReceiverService implements InitializingBean, DisposableBean
{
	private static final Logger logger = LoggerFactory.getLogger(TcpReceiverService.class);
	
	@Autowired Inbound in;
	@Autowired MLLP mllp;
	
	@Autowired ExecutorService executor;
	ServerSocket ss;
	
	@Override
	public void afterPropertiesSet() throws Exception
	{
		ss = new ServerSocket(in.getPort());
		ss.setSoTimeout(in.getTimeout_ms());
		executor.submit(() ->
		{
			try
			{
				while(true)
				{
					try(Socket socket = ss.accept())//Single-threaded app, but suitable for our purposes
					{
						logger.info("New Connection");
						final StringBuilder buf = new StringBuilder();
						try(InputStream is = socket.getInputStream();
							OutputStream os = socket.getOutputStream())
						{
							try
							{
								final byte[] eb = mllp.getActualEndBytes();
								int nextByte, ebPos = 0;
								while(ebPos < mllp.getActualEndBytes().length && -1 != (nextByte = is.read()))
								{
									if(buf.length() != 0)
									{
										buf.append(',');
									}
									buf.append(Integer.toString(nextByte));
									os.write(nextByte);//Echo everything back to the sender
									ebPos = nextByte == eb[ebPos] ? (ebPos+1) : 0;
								}
							}
							finally
							{
								logger.info("All bytes received from sender: {}", buf);
								os.flush();
							}
						}
						catch(@SuppressWarnings("unused") SocketTimeoutException e)
						{
							logger.error("Timed out when reading from socket! Closing client connection...");
						}
					}
					catch(@SuppressWarnings("unused") SocketTimeoutException e) {/*No connections yet, try again*/}
				}
			}
			catch(Throwable t)
			{
				logger.error("Unexpected error, closing...", t);
			}
		});
	}
	
	@Override
	public void destroy() throws Exception
	{
		if(ss != null)
		{
			ss.close();
		}
	}
}
