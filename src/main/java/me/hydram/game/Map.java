package me.hydram.game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Scanner;

import javafx.scene.layout.Pane;


public class Map {
	public static Tile[][] tiles; //cols - rows
	public static Tile[] tiles1d;
	public static Vec2 pacmanSpawn = null;
	public static Vec2 pacmanSpawn2 = null;
	public static int numberPathTiles;
	public static Vec2 ghostSpawn;
	public static Tile[][] getMap(int level,int cols, int rows)
	{
		numberPathTiles = 0;
		try {
			InputStream is;
			if(Main.enableMultiplayer == false)
			is = Map.class.getResourceAsStream("maps/map_" + level + ".pacmanmap");
			else
			is = Map.class.getResourceAsStream("maps/map_localmultiplayer_" + level + ".pacmanmap");
		      //File mapFile = new File("maps/map_" + level + ".pacmanmap");
			BufferedReader mapFile = new BufferedReader(new InputStreamReader(is));
		      
		      
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
		        		if(pacmanSpawn == null)
		        		{	
		        			pacmanSpawn = new Vec2(tiles[i][count].getX(),tiles[i][count].getY());
		        		}
		        		else if(pacmanSpawn != null && Main.enableMultiplayer == true)
		        		{
		        			pacmanSpawn2 = new Vec2(tiles[i][count].getX(),tiles[i][count].getY());
		        		}
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
		        	case 'D' : {
		        		tiles[i][count] = new Tile(Game.tileSize*i,Game.tileSize*count,Game.tileSize,Game.tileSize,7);
		        		break;
		        	}
		        	}
		        	tiles[i][count].col = i;
		        	tiles[i][count].row = count;
		        }
		        count++;
		        
		      }
		      myReader.close();
		    } catch (Exception e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		
		if(pacmanSpawn == null || (Main.enableMultiplayer == true && pacmanSpawn2 == null))
		{
			System.out.println("ERRRRRORRRRRORROROROROROR");
			Scenes.setMainMenuScene();
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


