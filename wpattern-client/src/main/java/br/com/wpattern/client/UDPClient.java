package br.com.wpattern.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.wpattern.client.interfaces.IMainWindow;
import br.com.wpattern.client.interfaces.IUDPClient;
import br.com.wpattern.client.server.utils.beans.CharacterPosition;

@Named
public class UDPClient implements IUDPClient {

	private static final Logger logger = Logger.getLogger(UDPClient.class);

	private static final String MESSAGE_DELIMITER = ":";

	@Inject
	private IMainWindow mainWindow;

	private DatagramSocket clientSocket;
	private byte[] buffer = new byte[1024];
	private StringTokenizer dataMessageTokenizer;

	public UDPClient() {
		try {
			this.clientSocket = new DatagramSocket(6789);
		} catch (SocketException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void startClient() {
		Thread thread = new Thread(new UDPClientRunnable(), "UDPClientRunnable");
		thread.start();
	}

	private CharacterPosition getCharacterPosition(byte[] messageData) {
		String message = new String(messageData, Charset.forName("UTF-8"));

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Client receiving the data [%s].", message));
		}

		this.dataMessageTokenizer = new StringTokenizer(message, MESSAGE_DELIMITER);

		// Message identifier
		this.dataMessageTokenizer.nextElement();

		// X
		String xData = ((String)this.dataMessageTokenizer.nextElement()).trim();
		int xPosition = Integer.valueOf(xData);

		// Y
		String yData = ((String)this.dataMessageTokenizer.nextElement()).trim();
		int yPosition = Integer.valueOf(yData);

		return new CharacterPosition(xPosition, yPosition);
	}

	class UDPClientRunnable implements Runnable {

		@Override
		public void run() {
			CharacterPosition newCharacterPosition;
			while (true) {
				try {
					DatagramPacket p = new DatagramPacket(UDPClient.this.buffer, UDPClient.this.buffer.length);

					UDPClient.this.clientSocket.receive(p);

					newCharacterPosition = UDPClient.this.getCharacterPosition(p.getData());

					UDPClient.this.mainWindow.setCharacterPosition(newCharacterPosition);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

	}

}
