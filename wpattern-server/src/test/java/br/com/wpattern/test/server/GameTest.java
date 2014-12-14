package br.com.wpattern.test.server;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Test;

import br.com.wpattern.client.server.utils.beans.CharacterPosition;
import br.com.wpattern.client.server.utils.interfaces.IGameListener;
import br.com.wpattern.server.game.interfaces.IGameManager;

public class GameTest extends AbstractBase {

	private static final Logger logger = Logger.getLogger(GameTest.class);

	@Inject
	private IGameManager gameManager;

	@Test
	public void testGameManager() {
		this.gameManager.addListener(new IGameListener() {

			@Override
			public void onCharacterMove(CharacterPosition position) {
				logger.debug(position);
			}
		});

		this.gameManager.startGame();

		Object waitObject = new Object();

		synchronized (waitObject) {
			try {
				waitObject.wait();
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
