package br.com.wpattern.test.server;

import javax.inject.Inject;

import org.junit.Test;

import br.com.wpattern.server.game.interfaces.IServerDistribution;

public class GameDistribution extends AbstractBase {

	@Inject
	private IServerDistribution serverDistribution;

	@Test
	public void startDisbution() {
		this.serverDistribution.startDistribution();
	}

}
