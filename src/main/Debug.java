package main;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;

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
