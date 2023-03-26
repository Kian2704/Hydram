package main;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class Game {

	private int score = 0;
	private boolean isGameOver = false;
	private boolean isPaused = false;
	public static double tileSize = 40;
	public static Pane gameScene;
	private Pacman pacman;

	private void play()
	{
		Runnable task = new Runnable()
				{
				public void run()
				{

					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        while(!isGameOver)
			        {
			        	try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	pacman.move();
			        }
				}};
				Thread gameThread = new Thread(task);
				gameThread.setDaemon(true);
				gameThread.start();

	}
	private void enableControl()
	{
		Main.stage.getScene().setOnKeyPressed(e -> {
		    if (e.getCode() == KeyCode.DOWN) {
		    	pacman.moveDirection = 0;
		    } else if(e.getCode() == KeyCode.LEFT)
		    {
		    	pacman.moveDirection = 1;
		    } else if(e.getCode() == KeyCode.UP)
		    {
		    	pacman.moveDirection = 2;
		    }else if(e.getCode() == KeyCode.RIGHT)
		    {
		    	pacman.moveDirection = 3;
		    }
		});
	}
	public Game()
	{
		
		pacman = new Pacman();
		gameScene.getChildren().add(pacman);
		pacman.setX(Map.pacmanSpawn.x);
		pacman.setY(Map.pacmanSpawn.y);
		for(int i = 0; i < Map.wallTiles.length; i++)
		{
			System.out.println(Map.wallTiles[i]);
		}
		enableControl();
		play();
	}
}
