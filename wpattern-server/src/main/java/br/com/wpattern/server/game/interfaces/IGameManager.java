package br.com.wpattern.server.game.interfaces;

import br.com.wpattern.client.server.utils.interfaces.IGameListener;

public interface IGameManager {

	void addListener(IGameListener listener);

	void startGame();

}
