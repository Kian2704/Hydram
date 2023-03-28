package main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;



public class Debug extends Game {

	public static boolean debugMode = false;
	public static int getTileRow(Tile tile)
	{
		return tile.row;
	}

	
	public static void forceWin()
	{
		Main.currentGame.gameScene.setOnKeyPressed(e ->
		{
			if(e.getCode() == KeyCode.NUMPAD5)
			{
				Main.currentGame.pointsLeft = 1;
			}
		});
	}
	
	
	
	public static int getTileCol(Tile tile)
	{
		return tile.col;
	}
	
	public static void tileDebugEventHandler(Tile tile)
	{
		tile.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("Tile Row/Col: " + tile.row + " " + tile.col);
				System.out.println("Tile Coordinates: " + tile.getX() + " " + tile.getY());	
				System.out.println("Tile Type: " + tile.type);
				if(tile.getEnt() != null)
				{
					System.out.println("Tile Point: " + tile.getEnt().getLayoutX());
				}
			}
		});
	}	
	
	public static void entityDebugEventHandler(Entity ent)
	{
		ent.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("Entity Coordinates: " + ent.getLayoutX() + " " + ent.getLayoutY());	
				System.out.println("Entity Type: " + ent.type);
			}
		});
	}	
	public static void mapCreator(Pane gameScene)
	{
		gameScene.setOnKeyPressed(e -> {
		    if (e.getCode() == KeyCode.A) {
		        System.out.println("The 'A' key was pressed");
		    }
		});
	}

}
