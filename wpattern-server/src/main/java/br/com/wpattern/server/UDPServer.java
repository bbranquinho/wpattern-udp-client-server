package br.com.wpattern.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.wpattern.client.server.utils.beans.CharacterPosition;
import br.com.wpattern.client.server.utils.interfaces.IGameListener;
import br.com.wpattern.server.game.interfaces.IGameManager;
import br.com.wpattern.server.game.interfaces.IServer;

@Named
public class UDPServer implements IServer, IGameListener {

	private static final Logger logger = Logger.getLogger(UDPServer.class);

	private DatagramSocket udpSocket = null;
	private byte[] buffer;
	private int messageIdentifier = 0;

	@Inject
	private IGameManager gameManager;

	public UDPServer() {
		try {
			this.udpSocket = new DatagramSocket();
		} catch (SocketException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void startServer() {
		this.gameManager.addListener(this);
	}

	@Override
	public void onCharacterMove(CharacterPosition position) {
		String message = String.format("%s:%s:%s:", ++this.messageIdentifier, position.getXPosition(), position.getYPosition());

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Server sending the data [%s].", message));
		}

		this.buffer = message.getBytes(Charset.forName("UTF-8"));

		try {
			this.udpSocket.send(new DatagramPacket(this.buffer, this.buffer.length, InetAddress.getLocalHost(), 6789));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
