package game;

import enums.GameStates;
import enums.ObjectTypes;

/**
 * globalState for the robotron Game.
 * 
 * 
 * @author Carl Stika
 *
 */
public class GlobalState {
	
	private static GlobalState instance = null;
	
	private int curScore = 0;
	private int waveNumber = 1;
	private int[] highscores = new int[20];
	private GameStates gameState;
	private boolean paused = false;
	private int numLives = 4;
	
	public int playerDyingTime=0;
	
	public int enemiesAnimatingOn = 0;
	public int playertAnimatingOn = 0;
	
	public int numRobotsWave = 0;
	public int man = 0;
	public int woman = 0;
	public int mikey = 0;
	public int hulkBots = 0;
	public int tanks = 0;
	
	private GlobalState() {
		//init the highscores
		for(int i=0;i<highscores.length;i++) {
			highscores[i] = 100;
		}
		gameState = GameStates.TITLE_SEQUENCE;
	}
	
	public static GlobalState getInstance() {
		if(instance==null) {
			instance = new GlobalState();
		}
		
		return instance;
	}
	
	public int getScore() {
		return curScore;
	}

	public void addScore(int value) {
		curScore += value;
	}

	public void clearScore() {
		curScore = 0;
	}

	public void increaseWaveNumber() {
		waveNumber++;
	}

	public void clearWaveNumber() {
		waveNumber = 1;
	}
	
	public GameStates getGameState() {
		return gameState;
	}

	public void setGameState(GameStates gameState) {
		this.gameState = gameState;
	}
	
	public boolean isGamePaused() {
		return paused;
	}

	public int getWaveNumber() {
		// TODO Auto-generated method stub
		return waveNumber;
	}

	public void removePlayerLife() {
		numLives--;
		playerDyingTime = 60;
	}

	public int getNumLives() {
		return numLives;
	}
}
