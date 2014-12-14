package br.com.wpattern.client.gui.panel;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.apache.log4j.Logger;

import br.com.wpattern.client.server.utils.beans.CharacterPosition;

public class MapPanel extends JPanel {

	private static final long serialVersionUID = 3151851774585487434L;
	private static final Logger logger = Logger.getLogger(MapPanel.class);

	// Map fields.
	private final int mapHeight = 600;
	private final int mapWidth = 800;
	private final int tileWidth = 20;
	private final int tileHeight = 20;

	// Character fields.
	private volatile int currentXPosition = 0;
	private volatile int currentYPosition = 0;

	private String characterPath = "src\\main\\resources\\character.png";
	private BufferedImage characterImage;

	// Panel
	private Canvas canvasMap;

	/**
	 * Create the panel.
	 */
	public MapPanel() {
		try {
			URL characterUrl = ((new File(this.characterPath)).toURI()).toURL();
			this.characterImage = ImageIO.read(characterUrl);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		this.setLayout(new MigLayout("", "[800.00:800.00:800.00]", "[600.00:600.00:600.00]"));

		this.canvasMap = new Canvas() {
			private static final long serialVersionUID = 7491201174291072428L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				MapPanel.this.drawMap(g);
				MapPanel.this.drawCharacter(g);
			}
		};

		this.canvasMap.setBackground(Color.WHITE);
		this.add(this.canvasMap, "cell 0 0,grow");
	}

	public void setCharacterPosition(CharacterPosition currentPosition) {
		this.currentXPosition = currentPosition.getXPosition();
		this.currentYPosition = currentPosition.getYPosition();
		this.canvasMap.repaint();
	}

	private void drawMap(Graphics g) {
		// Columns
		for (int i = 1; i < (this.mapWidth / this.tileWidth); i++) {
			g.drawLine(i * this.tileWidth, 0, i * this.tileWidth, this.mapHeight);
		}

		// Rows
		for (int i = 1; i < (this.mapHeight / this.tileHeight); i++) {
			g.drawLine(0, i * this.tileHeight, this.mapWidth, i * this.tileHeight);
		}
	}

	private void drawCharacter(Graphics g) {
		g.drawImage(this.characterImage, this.currentXPosition * this.tileHeight,
				this.currentYPosition * this.tileWidth, null);
	}

}
