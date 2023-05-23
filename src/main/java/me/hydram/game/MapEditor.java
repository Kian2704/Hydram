package me.hydram.game;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;

public class MapEditor {

	private static boolean active	=	true;
	
	private static int tool;
	private static int menuOpen;
//Erstellt Tastenkombinationen zum speichern und laden der Karte(Level)
	public static void start(Scene scene)
	{
		scene.setOnKeyPressed(e -> {
		    if (e.getCode() == KeyCode.S && e.isControlDown()) {
		        saveMap(Map.tiles);
		    }
		    if (e.getCode() == KeyCode.O && e.isControlDown()) {
		        loadMap(Map.tiles);
		    }
		});
	}
	
	//Läd eine Spielkarte(Level) aus einer Datei
	public static void loadMap(Tile[][] tiles)
	{
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter =  new FileChooser.ExtensionFilter("Map Files (*.pacmanmap)","*.pacmanmap");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Main.stage);
    
        Scanner reader;
		try {
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			return;
		}
        int count = 0;
        while (reader.hasNextLine()) {//tiles[i][count]
	        String line = reader.nextLine();
	        for(int i = 0; i < line.length();i++)
	        {
	        	Tile tile = tiles[i][count];
	        	switch(line.charAt(i))
	        	{
	        	case 'X' : {
	        		tile.type = 1;
					tile.setFill(Color.BLACK);
	        		break;
	        	}
	        	case 'N' : {
	        		tile.type=4;
					tile.setFill(Color.RED);
	        		break;
	        	}
	        	case 'W' : {
	        		tile.type = 0;
					tile.setFill(Main.wallTilePattern);
	        		break;
	        	}
	        	case 'P' : {
	        		tile.type=3;
					tile.setFill(Color.YELLOW);
	        		break;
	        	}
	        	case 'G' : {
	        		tile.type=2;
					tile.setFill(Color.BROWN);
	        		break;
	        	}
	        	case 'B' : {
	        		tile.type=5;
					tile.setFill(new ImagePattern(Game.spawnBlockerTexture));
	        		break;
	        	}
	        	case 'U' : {
	        		tile.type=6;
					tile.setFill(Color.AQUA);
	        		break;
	        	}
	        	case 'D' : {
	        		tile.type=7;
					tile.setFill(Main.breakableWallTilePattern);
	        		break;
	        	}
	        	}
	        }
	        count++;
	        
	      }
        reader.close();
	}
	
	
	//Aktiviert die Funktion, die angeklickten Felder zu verändern.
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
					}else if(event.isShiftDown())
					{
						tile.type = 6;
						tile.setFill(Color.AQUA);
					}else if(event.isAltDown())
					{
						tile.type = 7;
						tile.setFill(Main.breakableWallTilePattern);
					}
					else
					{
						tile.type = 0;
						tile.setFill(Main.wallTilePattern);
					}
					
				} else if(event.getButton() == MouseButton.SECONDARY)
				{
					if(event.isControlDown())
					{
						tile.type=2;
						tile.setFill(Color.BROWN);
					}else if(event.isShiftDown())
					{
					
						tile.type=4;
						tile.setFill(Color.RED);
						
					}else
					{
						tile.type = 1;
						tile.setFill(Color.BLACK);
					}
					
				}else if(event.getButton() == MouseButton.MIDDLE)
				{
					tile.type = 5;
					tile.setFill(new ImagePattern(Game.spawnBlockerTexture));
				}
				
				
			}
		});
	}
	//Speichert die erstellte Map in einer Datei mit der Dateiendung .pacmanmap
	public static int saveMap(Tile[][] tiles)
	{
        try {  
        	FileChooser fileChooser = new FileChooser();
	        FileChooser.ExtensionFilter extFilter =  new FileChooser.ExtensionFilter("Map Files (*.pacmanmap)","*.pacmanmap");
	        fileChooser.getExtensionFilters().add(extFilter);
	        fileChooser.setInitialFileName("map.pacmanmap");
	        File file = fileChooser.showSaveDialog(Main.stage);
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
            		case 5: myWriter.write("B");break;
            		case 6: myWriter.write("U");break;
            		case 7: myWriter.write("D");break;
            		}
            		
            	}
            	myWriter.write("\n");
            	
            }
            myWriter.close();
            
            
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return 0;
            
            
          } 
        return 1;
	}
}
