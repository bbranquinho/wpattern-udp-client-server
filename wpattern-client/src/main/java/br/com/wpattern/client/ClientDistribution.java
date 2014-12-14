package br.com.wpattern.client;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import br.com.wpattern.client.interfaces.IClientDistribution;
import br.com.wpattern.client.interfaces.IMainWindow;
import br.com.wpattern.client.interfaces.IUDPClient;

@Named
public class ClientDistribution implements IClientDistribution {

	private static final Logger logger = Logger.getLogger(ClientDistribution.class);

	@Inject
	private IMainWindow mainWindow;

	@Inject
	private IUDPClient udpcClient;

	@Override
	public void startDistribution() {
		try {
			this.mainWindow.showWindow();
			this.udpcClient.startClient();
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
			System.exit(0);
		}

		logger.info("Client started.");
	}

}
