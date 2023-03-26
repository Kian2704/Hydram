package main;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class Debug {

	
	public static int getTileRow(Tile tile)
	{
		return tile.row;
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
				System.out.println("Tile Point: " + tile.getEnt().getX());
			}
		});
	}	
	
	public static void entityDebugEventHandler(Entity ent)
	{
		ent.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("Entity Coordinates: " + ent.getX() + " " + ent.getY());	
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
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
