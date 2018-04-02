package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import Effects.ScreenColorWipe;
import baseframework.BaseGame;
import baseframework.SpriteBase;
import enums.GameStates;
import enums.ObjectTypes;
import interfaces.DrawObject;
import interfaces.ScreenWipe;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import objects.BadRobot;
import objects.FontBase;
import objects.HulkBots;
import objects.Man;
import objects.Mikey;
import objects.Tank;
import objects.Title;
import objects.TronPlayer;
import objects.VectorObject;
import objects.Woman;
import utilitys.GameUtils;

/**
 * A simple Robotron implemntation
 * 
 * @author Carl Stika
 *
 */

public class Tron extends BaseGame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8429412010979563695L;
	
	private static final int BORDER_TOP_OFFSET = 12;
	private static final int BORDER_OFFSET = 4;
	private static final int BORDER_WIDTH = 2;

	private BufferedImage tronTexture = null;
	private TronPlayer tronPlayer = null;
	private VectorObject border = null;
	private List<DrawObject> gameObjects = new ArrayList<>();
	
	private Random random = new Random();
//	private boolean playing = false;
	
	private boolean inScreenWipe = false;
	private ScreenWipe screenWipe = null;
	
	private int screenWidth;
	private int screenHeight;
	
	
	private Controller gameController = null; 
	public float[] leftStick = {0.0f, 0.0f};
	public float[] rightStick = {0.0f, 0.0f};
	
	public Tron(String title, int width, int height) {
		super(title, width, height);
		this.screenWidth = width;
		this.screenHeight = height;

		initGameController();
		
				
		//Image used for all the textures
		tronTexture = GameUtils.loadImage("robotronsprites.png");

		//Main players object
		tronPlayer = new TronPlayer(tronTexture);

		border = new VectorObject();
		border.addLine(BORDER_OFFSET, BORDER_TOP_OFFSET, 639-BORDER_OFFSET, BORDER_TOP_OFFSET, BORDER_WIDTH);
		border.addLine(639-BORDER_OFFSET, BORDER_TOP_OFFSET, 639-BORDER_OFFSET, 479-BORDER_OFFSET, BORDER_WIDTH );
		border.addLine(BORDER_OFFSET, 479-BORDER_OFFSET, 639-BORDER_OFFSET, 479-BORDER_OFFSET, BORDER_WIDTH);
		border.addLine(BORDER_OFFSET, BORDER_TOP_OFFSET, BORDER_OFFSET, 479-BORDER_OFFSET, BORDER_WIDTH);
		border.finish();
		border.setColor(1.0f, 0.0f,  0.0f, 1.0f);
		
		GlobalState  globalState = GlobalState.getInstance(); //init global state
		
		initGamestate(globalState);
	}

	/**
	 * initGamestate
	 * 
	 * @param globalState
	 */
	private void initGamestate(GlobalState globalState) {
		switch(globalState.getGameState()) {
			case GAME_MENU:
				break;
			case TITLE_SEQUENCE:
				populateForTitleScreen();
				break;
			default :
				populateBadRobotsForWave();
				break;
		}
	}

	/**
	 * Initialize the game controller. 
	 */
	private void initGameController() {
		ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment(); 
		Controller[] cs = ce.getControllers(); 
		for (int i = 0; i < cs.length; i++) { 
			if(cs[i].getType().equals(Controller.Type.GAMEPAD)){
				gameController = cs[i];
				System.out.printf("found game pad at %d", i);
			}
			System.out.println(i + ". " + cs[i].getName() + ", " + cs[i].getType() ); 
		}
		
		if(gameController!=null) {
			Component[] components = gameController.getComponents();
			
			for(int i=0;i<components.length;i++) {
				System.out.println(i + ". " + components[i].getName() + ", " + components[i].isAnalog() ); 
				
			}
		}
	}

	/**
	 * 
	 */
	private void populateForTitleScreen() {
		gameObjects.clear();
//		FontBase stringtest2;

/*		stringtest2 = new FontBase(tronTexture, 608, 512);
		stringtest2.addString("Carl Tron");
		stringtest2.setPos(100, 130);*/
//		gameObjects.add(stringtest2);
		
//		gameObjects.add(new LaserWriter("tron"));
		
//		LaserWriter title = new LaserWriter("Carl Tron");
//		title.setPos(100, 200);
//		gameObjects.add(title);
		gameObjects.add(new Title());
		populateForMenu();
		
	}

	private void populateForMenu() {
//		FontBase stringtest2;

/*		stringtest2 = new FontBase(tronTexture, 608, 512);
		stringtest2.addString("Carl Tron");
		stringtest2.setPos(100, 130);*/
//		gameObjects.add(stringtest2);
		
//		gameObjects.add(new LaserWriter("tron"));
		
//		title.setPos(100, 200);
		FontBase startGame = new FontBase(tronTexture);
		startGame.addString("Start Game");
		startGame.setPos(320-40, 300);
		gameObjects.add(startGame);
		
	}
	
	private void populateBadRobotsForWave() {

//		inScreenWipe = true;
		gameObjects.clear();
		
		 
/*		FontBase stringtest;

		stringtest = new FontBase(tronTexture); // 608, 512);
		stringtest.addString(""+tronPlayer.getScore());
		stringtest.setPos(100, 10);
		gameObjects.add(stringtest);
	*/
		tronPlayer.clear();
		gameObjects.add(tronPlayer);

		int waveNumber = GlobalState.getInstance().getWaveNumber();
		
		int numRobotsWave = 12 + ((waveNumber-1)*5);
		int man = 1;
		int woman = 1;
		int mikey = 1;
		int hulkBots = 0;
		int tanks = 0;
		if(waveNumber>1) {
			hulkBots = 3;
			tanks = 1;
		}
		
		//add obstacles
		for(int i=0;i<numRobotsWave;i++) {
			SpriteBase enemy = new BadRobot(tronTexture);
			enemy.setPos((random.nextFloat()*610.0f)+10.0f, (random.nextFloat()*450.0f)+10.0f);

			gameObjects.add(enemy);
		}
		
		for(int i=0;i<hulkBots;i++) {
			SpriteBase enemy = new HulkBots(tronTexture);
			enemy.setPos((random.nextFloat()*610.0f)+10.0f, (random.nextFloat()*450.0f)+10.0f);

			gameObjects.add(enemy);
		}

		for(int i=0;i<tanks;i++) {
			SpriteBase enemy = new Tank(tronTexture);
			enemy.setPos((random.nextFloat()*610.0f)+10.0f, (random.nextFloat()*450.0f)+10.0f);

			gameObjects.add(enemy);
		}
		for(int i=0;i<man;i++) {
			SpriteBase enemy = new Man(tronTexture);
			enemy.setPos((random.nextFloat()*610.0f)+10.0f, (random.nextFloat()*450.0f)+10.0f);

			gameObjects.add(enemy);
		}

		for(int i=0;i<woman;i++) {
			SpriteBase enemy = new Woman(tronTexture);
			enemy.setPos((random.nextFloat()*610.0f)+10.0f, (random.nextFloat()*450.0f)+10.0f);

			gameObjects.add(enemy);
		}

		for(int i=0;i<mikey;i++) {
			SpriteBase enemy = new Mikey(tronTexture);
			enemy.setPos((random.nextFloat()*610.0f)+10.0f, (random.nextFloat()*450.0f)+10.0f);

			gameObjects.add(enemy);
		}
	}

	
	
	@Override
	public void updateGame(long interval) {
		if(gameController!=null) checkControllersForGame();

		List<DrawObject> newSprites = new ArrayList<>();
		if(!inScreenWipe) {
			if(GlobalState.getInstance().playerDyingTime == 0) {
				gameObjects.stream().forEach(t -> t.update(gameObjects, newSprites));
	
		        //Add any new connected.
		        gameObjects.addAll(newSprites);
		 //       testquad.render();
		        //remove object if dead
		        gameObjects.removeIf(t->t.isDead());
	
		        switch(GlobalState.getInstance().getGameState()) {
		        	 case GAME_PLAYING:
		             {
				       	checkControllersForGame();
				
				       	//Put logic somewhere else
				      	long numGrunts = gameObjects.stream().filter(t->t.getObjectType()==ObjectTypes.OBJECT_TYPE_GRUNT).count();
				        if(numGrunts==0) {
							gameObjects.clear();
							inScreenWipe = true;
							screenWipe = new ScreenColorWipe(screenWidth, screenHeight, 6);
	
				    		GlobalState.getInstance().increaseWaveNumber();
	//						populateBadRobotsForWave();
						}
				    }
				    break;
		        	case TITLE_SEQUENCE :
		        	break;
		   
		        	default:
		        	break;
		        }
			}
			else {
				GlobalState.getInstance().playerDyingTime--;
				
				if(GlobalState.getInstance().playerDyingTime==0) {
					tronPlayer.setPos(640/2,  480/2);
					//Do player return on screen animation
				}
			}
		}
		else {
			int percent = screenWipe.update();
			if(percent==50) {
				populateBadRobotsForWave();
			}
			if(percent>=100) {
				inScreenWipe = false;
			}
		}

	}

	@Override
	public void renderGame(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, 800, 600);

		g2d.setColor(Color.WHITE);
		g2d.drawString(""+GlobalState.getInstance().getScore(), 100, 10);

		render(g2d);
	}

	/**
	 * Render the board
	 * @param g2d
	 */
	protected void render(Graphics2D g2d) {
		border.render(g2d);
		gameObjects.stream().forEach(t -> t.render(g2d));

		if(inScreenWipe) {
			screenWipe.render(g2d); 
		}
	}

	/*
	 * 
	 */
	private void checkControllersForGame() {
		float deadZone = 0.1f;
		gameController.poll();
		Component[] components = gameController.getComponents();
		
		
		leftStick[1] = components[0].getPollData();
		if(leftStick[1]<deadZone && leftStick[1]>-deadZone) {
			leftStick[1] = 0.0f;
		}
		leftStick[0] = components[1].getPollData();
		if(leftStick[0]<deadZone && leftStick[0]>-deadZone) {
			leftStick[0] = 0.0f;
		}
		rightStick[1] = components[2].getPollData();
		if(rightStick[1]<deadZone && rightStick[1]>-deadZone) {
			rightStick[1] = 0.0f;
		}
		rightStick[0] = components[3].getPollData();
		if(rightStick[0]<deadZone && rightStick[0]>-deadZone) {
			rightStick[0] = 0.0f;
		}
		
		float aButton = components[5].getPollData();
	
		switch(GlobalState.getInstance().getGameState()) {
			case GAME_PLAYING:
				tronPlayer.controllerLeftStick(leftStick[0], -leftStick[1]);
				tronPlayer.controllerRightStick(rightStick[0], -rightStick[1]);
				break;
			case TITLE_SEQUENCE:
				if(aButton>=0.99f) {
					GlobalState.getInstance().setGameState(GameStates.GAME_PLAYING);
					gameObjects.clear();
					inScreenWipe = true;
					screenWipe = new ScreenColorWipe(screenWidth, screenHeight, 6);
				}
				break;
			default:
				break;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Tron("Swing Tron", 640+10, 480+30);
			}
		});	
	}

	@Override
	public void mousePosition(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseButtonPress(int buttons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseButtonRelease(int buttons) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyboardPressed(int key) {
		switch(key) {
			case 38: //arrow up
				tronPlayer.controllerRightStick(0.0f, 1.0f);
				break;
			case 40: //arrow down
				tronPlayer.controllerRightStick(0.0f, -1.0f);
				break;
			case 37: //arrow left
				tronPlayer.controllerRightStick(-1.0f, 0.0f);
				break;
			case 39: //arrow right
				tronPlayer.controllerRightStick(1.0f, 0.0f);
				break;
			case 87: //w
				tronPlayer.controllerLeftStick(0.0f, 2.0f);
				break;
			case 83: //s
				tronPlayer.controllerLeftStick(0.0f, -2.0f);
				break;
			case 65: //a
				tronPlayer.controllerLeftStick(-2.0f, 0.0f);
				break;
			case 68: //d
				tronPlayer.controllerLeftStick(2.0f, 0.0f);
				break;
		}
//		public void controllerLeftStick(float xAxis, float yAxis) {
	}

	@Override
	public void keyboardReleased(int key) {
		if(key==32) {
			switch(GlobalState.getInstance().getGameState()) {
				case GAME_PLAYING:
						break;
					case TITLE_SEQUENCE:
						GlobalState.getInstance().setGameState(GameStates.GAME_PLAYING);
						break;
					default:
						break;
			}
		}
	}

}
