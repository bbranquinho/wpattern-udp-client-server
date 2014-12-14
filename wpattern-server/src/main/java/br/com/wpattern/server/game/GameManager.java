package br.com.wpattern.server.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.inject.Named;

import br.com.wpattern.client.server.utils.beans.CharacterPosition;
import br.com.wpattern.client.server.utils.interfaces.IGameListener;
import br.com.wpattern.server.game.interfaces.IGameManager;

@Named
public class GameManager implements IGameManager {

	private final int enlapsedTime = 1000;

	private final int mapWidhtSize = 20;

	private final int mapHeightSize = 20;

	private final ReadWriteLock listenersLock = new ReentrantReadWriteLock();

	private final List<IGameListener> listeners = new ArrayList<IGameListener>();

	private final CharacterPosition currentCharacterPosition = new CharacterPosition(10, 10);

	@Override
	public void addListener(IGameListener listener) {
		listener.onCharacterMove(this.getCurrentPosition());
		this.addGameListener(listener);
	}

	@Override
	public void startGame() {
		Timer timerScheduler = new Timer("GameRunnable");

		timerScheduler.scheduleAtFixedRate(new GameRunnable(), new Date(), this.enlapsedTime);
	}

	private void addGameListener(IGameListener listener) {
		try {
			this.listenersLock.writeLock().lock();
			this.listeners.add(listener);
		} finally {
			this.listenersLock.writeLock().unlock();
		}
	}

	private void notifyListener() {
		try {
			this.listenersLock.readLock().lock();
			CharacterPosition currentPosition = this.getCurrentPosition();

			for (IGameListener listener : this.listeners) {
				listener.onCharacterMove(currentPosition);
			}
		} finally {
			this.listenersLock.readLock().unlock();
		}
	}

	private void setCurrentPosition(int x, int y) {
		try {
			this.listenersLock.writeLock().lock();
			this.currentCharacterPosition.setXPosition(x);
			this.currentCharacterPosition.setYPosition(y);
		} finally {
			this.listenersLock.writeLock().unlock();
		}
	}

	private CharacterPosition getCurrentPosition() {
		try {
			this.listenersLock.readLock().lock();
			return this.currentCharacterPosition.clone();
		} finally {
			this.listenersLock.readLock().unlock();
		}
	}

	class GameRunnable extends TimerTask {

		private Random random = new Random(System.currentTimeMillis());

		@Override
		public void run() {
			CharacterPosition currentPosition = GameManager.this.getCurrentPosition();
			int newXPosition = this.getNewPosition(currentPosition.getXPosition(), GameManager.this.mapWidhtSize);
			int newYPosition = this.getNewPosition(currentPosition.getYPosition(), GameManager.this.mapHeightSize);

			GameManager.this.setCurrentPosition(newXPosition, newYPosition);

			GameManager.this.notifyListener();
		}

		private int getNewPosition(int currentPosition, int maxValue) {
			int newPosition;

			do {
				int randomPosition = this.random.nextInt(3) - 1;

				newPosition = currentPosition + randomPosition;
			} while ((newPosition < 0) || (newPosition > maxValue));

			return newPosition;
		}

	}

}
