package br.com.wpattern.client.server.utils.beans;

public class CharacterPosition extends BaseBean {

	private static final long serialVersionUID = 3085338015908392047L;

	private int xPosition;

	private int yPosition;

	public CharacterPosition() {
	}

	public CharacterPosition(int xPosition, int yPosition) {
		super();
		this.xPosition = xPosition;
		this.yPosition = yPosition;
	}

	public int getXPosition() {
		return this.xPosition;
	}

	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getYPosition() {
		return this.yPosition;
	}

	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	@Override
	public CharacterPosition clone() {
		return new CharacterPosition(this.xPosition, this.yPosition);
	}

}
