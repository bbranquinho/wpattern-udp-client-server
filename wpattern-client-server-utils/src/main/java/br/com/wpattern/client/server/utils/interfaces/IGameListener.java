package br.com.wpattern.client.server.utils.interfaces;

import br.com.wpattern.client.server.utils.beans.CharacterPosition;

public interface IGameListener {

	void onCharacterMove(CharacterPosition position);

}
