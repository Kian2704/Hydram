package main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;



public class Debug extends Game {

	public static boolean debugMode = false;
	
	public static void tileDebugEventHandler(Tile tile)
	{
		tile.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("Tile Coordinates: " + tile.getX() + " " + tile.getY());	
				System.out.println("Tile Type: " + tile.type);
				System.out.println("Tile Neighbours: " + tile.getNeighbors()[0].type + " " + tile.getNeighbors()[1].type );
				if(tile.getEnt() != null)
				{
					System.out.println("Entity Type: " + tile.getEnt().type);
					System.out.println("Entity Size: " +tile.getEnt().sizeFactor);
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
