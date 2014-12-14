package br.com.wpattern.server;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.wpattern.server.game.interfaces.IGameManager;
import br.com.wpattern.server.game.interfaces.IServer;
import br.com.wpattern.server.game.interfaces.IServerDistribution;

@Named
public class ServerDistribution implements IServerDistribution {

	private static final Logger logger = Logger.getLogger(ServerDistribution.class);

	@Inject
	private IGameManager gameManager;

	@Inject
	private IServer server;

	@Override
	public void startDistribution() {
		try {
			this.server.startServer();
			this.gameManager.startGame();
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			System.exit(0);
		}

		logger.info("Server started.");
	}

}
