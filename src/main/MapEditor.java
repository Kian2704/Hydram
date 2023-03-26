package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class MapEditor {

	private static boolean active	=	true;
	
	private static int tool;
	private static int menuOpen;

	public static void start(Scene scene)
	{
		scene.setOnKeyPressed(e -> {
		    if (e.getCode() == KeyCode.S && e.isControlDown()) {
		        saveMap(Map.tiles);
		    }
		});
	}
	
	
	public static void makeEditable(Tile tile)
	{
		tile.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.PRIMARY)
				{
					if(event.isControlDown())
					{
						tile.type=3;
						tile.setFill(Color.YELLOW);
					}else
					{
						tile.type = 0;
						tile.setFill(Main.wallTilePattern);
					}
					
				} else if(event.getButton() == MouseButton.SECONDARY)
				{
					if(event.isControlDown())
					{
						tile.type=2;
						tile.setFill(Color.AQUA);
					}else if(event.isShiftDown())
					{
					
						tile.type=4;
						tile.setFill(Color.RED);
						
					}else
					{
						tile.type = 1;
						tile.setFill(Color.BLACK);
					}
					
				}
				
				
			}
		});
	}
	public static int saveMap(Tile[][] tiles)
	{
		System.out.println(tiles[0].length + " " + tiles.length);
        try {  
        	FileChooser fileChooser = new FileChooser();
	        File file = fileChooser.showSaveDialog(Main.stage);
	        fileChooser.setInitialFileName("map.pacmanmap");
	        if (file == null)
	        	return 0;
            FileWriter myWriter = new FileWriter(file);
            
            //tiles[0] height tiles width
            for(int i = 0; i < tiles[0].length; i++)
            {
            	for(int j = 0; j < tiles.length;j++)
            	{
            		switch(tiles[j][i].type)
            		{
            		case 0: myWriter.write("W");break;
            		case 1: myWriter.write("X");break;
            		case 2: myWriter.write("G");break;
            		case 3: myWriter.write("P");break;
            		case 4: myWriter.write("N");break;
            		}
            		
            	}
            	myWriter.write("\n");
            	
            }
            myWriter.close();
            
            
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
        	  
        	  
            System.out.println("An error occurred.");
            e.printStackTrace();
            return 0;
            
            
          } 
        return 1;
	}
	
	
	public static void main(String[] args) {
	}

}
