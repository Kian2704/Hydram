package main;

import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class Game {

	private int score = 0;
	private boolean isGameOver = false;
	private boolean isPaused = false;
	public static double tileSize = 40;
	public static Pane gameScene;
	private Pacman pacman;

	public boolean isGameOver()
	{
		if(isGameOver)
			return true;
		return false;
	}
	
	
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
							Thread.sleep(15);
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
	public Game()
	{
		
		pacman = new Pacman();
		gameScene.getChildren().add(pacman);
		pacman.enableControl();
		play();
	}
}
