package org.adelbs.iso8583.clientserver;

import java.io.IOException;

import org.adelbs.iso8583.exception.ConnectionException;
import org.adelbs.iso8583.exception.ParseException;

class MockISOServer extends MockISOConnection{
	
	public MockISOServer(final String host, final int port) throws IOException, ConnectionException{
		this.conn = new ISOConnection(true, host, port, 1000);
		conn.setIsoConfig(ISOCONFIG);
		conn.setCallback(new MockServerCallback(conn,ISOCONFIG));
		conn.connect();
		System.out.println("Server: Connected");
	}
	
	public void process() throws MockISOException{
		try {
			conn.processNextPayload(false, 0);
		} catch (ParseException e) {
			terminate();
			System.out.println("Server: "+e.getMessage());
			throw new MockISOException(e.getMessage());
		}catch(InterruptedException e){
			System.out.println("Server: "+e.getMessage());
			terminate();
		}catch (NullPointerException e) {
			System.out.println("Server: "+e.getMessage());
		}
	}
}
