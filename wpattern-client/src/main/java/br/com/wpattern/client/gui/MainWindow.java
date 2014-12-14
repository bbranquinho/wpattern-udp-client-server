package br.com.wpattern.client.gui;

import javax.inject.Named;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;
import br.com.wpattern.client.gui.panel.MapPanel;
import br.com.wpattern.client.interfaces.IMainWindow;
import br.com.wpattern.client.server.utils.beans.CharacterPosition;

@Named
public class MainWindow implements IMainWindow {

	private JFrame frame;
	private MapPanel mapPanel;

	@Override
	public void showWindow() {
		this.frame.setVisible(true);
	}

	@Override
	public void setCharacterPosition(CharacterPosition currentPosition) {
		this.mapPanel.setCharacterPosition(currentPosition);
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.frame = new JFrame();
		this.frame.setBounds(100, 100, 844, 665);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));

		this.mapPanel = new MapPanel();
		this.frame.getContentPane().add(this.mapPanel, "cell 0 0,grow");
	}

}
