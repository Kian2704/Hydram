package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.scene.layout.Pane;


public class Map {
	public static Tile[][] tiles; //cols - rows
	public static Tile[] tiles1d;
	public static Vec2 pacmanSpawn;
	public static int numberPathTiles;
	public static Vec2 ghostSpawn;
	public static Tile[][] getMap(int level,int cols, int rows)
	{
		numberPathTiles = 0;
		try {
		      File mapFile = new File("resources/maps/map_" + level + ".pacmanmap");
		      
		      
		      Scanner myReader = new Scanner(mapFile);
		      if(tiles == null)
	        	{
	        		System.out.println("Error Tile Array not initialized!");
	        		myReader.close();
	        		return null;
	        	}
		      int count = 0;
		      while (myReader.hasNextLine()) {
		    	  if(count >= rows)
		    	  {
		    		  System.out.println("Map size doesn't match Screen size!");
		    	  }
		        String line = myReader.nextLine();
		        for(int i = 0; i < line.length();i++)
		        {
		        	if(i > cols)
		        	{
			    		  System.out.println("Map size doesn't match Screen size!");
			    		  myReader.close();
			    		  return null;
			    	}

		        	switch(line.charAt(i))
		        	{ 
		        	case 'X' : {
		        		tiles[i][count] = new Tile(Game.tileSize*i,Game.tileSize*count,Game.tileSize,Game.tileSize,1);
		        		tiles[i][count].setEnt(new Point());
		        		break;
		        	}
		        	case 'N' : {
		        		tiles[i][count] = new Tile(Game.tileSize*i,Game.tileSize*count,Game.tileSize,Game.tileSize,4);
		        		break;
		        	}
		        	case 'W' : tiles[i][count] = new Tile(Game.tileSize*i,Game.tileSize*count,Game.tileSize,Game.tileSize,0);break;
		        	case 'P' : {
		        		tiles[i][count] = new Tile(Game.tileSize*i,Game.tileSize*count,Game.tileSize,Game.tileSize,3);
		        		pacmanSpawn = new Vec2(tiles[i][count].getX(),tiles[i][count].getY());
		        		break;
		        	}
		        	case 'G' : {
		        		tiles[i][count] = new Tile(Game.tileSize*i,Game.tileSize*count,Game.tileSize,Game.tileSize,2);
		        		ghostSpawn = new Vec2(tiles[i][count].getX(),tiles[i][count].getY());
		        		break;
		        	}
		        	case 'B' : {
		        		tiles[i][count] = new Tile(Game.tileSize*i,Game.tileSize*count,Game.tileSize,Game.tileSize,5);
		        		tiles[i][count].setEnt(new SpawnBlocker());
		        		break;
		        	}
		        	case 'U' : {
		        		tiles[i][count] = new Tile(Game.tileSize*i,Game.tileSize*count,Game.tileSize,Game.tileSize,1);
		        		tiles[i][count].setEnt(new Powerup());
		        		break;
		        	}
		        	}
		        	tiles[i][count].col = i;
		        	tiles[i][count].row = count;
		        }
		        count++;
		        
		      }
		      
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		return tiles;
		  }
	
	public static void loadMap(Pane gameScene)
	{
		for(int i = 0; i < tiles[0].length; i++)
		{
			for(int j = 0; j < tiles.length;j++)
			{
				gameScene.getChildren().addAll(tiles[j][i]);
				if(tiles[j][i].getEnt() != null)
				{
					gameScene.getChildren().addAll(tiles[j][i].getEnt());
				}
			}
		}
		
		
	}}


