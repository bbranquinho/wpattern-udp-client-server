package br.com.wpattern.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.wpattern.client.interfaces.IClientDistribution;

public class ClientMain {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("/ctx-wpattern-client.xml");

		IClientDistribution clientDistribution = context.getBean(IClientDistribution.class);

		clientDistribution.startDistribution();
	}

}
