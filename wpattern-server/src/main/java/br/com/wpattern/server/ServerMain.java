package br.com.wpattern.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.wpattern.server.game.interfaces.IServerDistribution;

public class ServerMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/ctx-wpattern-server.xml");

		IServerDistribution serverDistribution = context.getBean(IServerDistribution.class);

		serverDistribution.startDistribution();
	}

}
