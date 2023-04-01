package main;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Ghost extends MovingEntity {

	private boolean eatable;
	public static int numberGhosts;
	private int id;
	private static int randomMoveTimer = 0;
	private static int randomMoveCounter = 0;
	private boolean axisSwitch;
	private boolean isEatable = false;
	static boolean test = true;
	public Ghost()
	{
		type = 1;
		setLayoutX(Map.ghostSpawn.x);
		setLayoutY(Map.ghostSpawn.y);
		id = numberGhosts;
		numberGhosts++;
		setFitHeight(Game.tileSize*sizeFactor);
		height = getFitHeight();
		setFitWidth(Game.tileSize*sizeFactor);
		width = getFitWidth();
		changeSkin();
		
		
		
	}
	
	private ArrayList<Vec2> getPath(Cell[][] cellDetails, Vec2 dest)
	{
		
		
			int row = (int) dest.y;
			int col = (int) dest.x;
			if(dest != currentTile && !(row == (int)currentTile.y && col == (int)currentTile.x))
			{
			ArrayList<Vec2> path = new ArrayList<>();
			ArrayList<Vec2> returnPath = new ArrayList<>();

				while(!(cellDetails[col][row].parent_j == row && cellDetails[col][row].parent_i == col))
				{
					
					
					path.add(new Vec2(col,row));
					int temp_row = cellDetails[col][row].parent_j;
					int temp_col = cellDetails[col][row].parent_i;
					row = temp_row;
					col = temp_col;
				}
				path.add(new Vec2(col,row));
				while(!path.isEmpty())
				{
					Vec2 p = path.get(path.size()-1);
					Map.tiles[(int) p.x][(int) p.y].setFill(Color.RED);
					returnPath.add(path.get(path.size()-1));
					path.remove(path.size()-1);
					
					
				}

				return returnPath;
		}
			return null;
	}
	
	
	

	public ArrayList<Vec2> getWayToPacman(Pacman pacman)
	{
		if(currentTile == null || pacman.currentTile == null)
		{
			return null;
		}
		Boolean[][] closedList = new Boolean[Map.tiles.length][Map.tiles[0].length];
		Cell[][] cellDetails = new Cell[Map.tiles.length][Map.tiles[0].length];
		for(int i = 0; i < Map.tiles.length;i++)
		{
			for(int j = 0; j < Map.tiles[0].length;j++)
			{
				closedList[i][j] = false;
				cellDetails[i][j] = new Cell(Double.MAX_VALUE,Double.MAX_VALUE,Double.MAX_VALUE,-1,-1);
				cellDetails[i][j].col = i;
				cellDetails[i][j].row = j;
			}
		}
		int i=(int) currentTile.x;//col value
		int j=(int) currentTile.y;//row value
		cellDetails[i][j].f = 0;
		cellDetails[i][j].g = 0;
		cellDetails[i][j].h = 0;
		cellDetails[i][j].parent_i = i;
		cellDetails[i][j].parent_j = j;
		PriorityQueue<Cell> openList = new PriorityQueue<Cell>();
		openList.add(cellDetails[i][j]);
		boolean foundDest = false;
		while(!openList.isEmpty())
		{
			Cell p = openList.poll();
			i=p.col;
			j=p.row;
			closedList[i][j] = true;
			double gNew,hNew,fNew;
			if(Cell.isValid(i-1, j))
			{
				if(i-1 == pacman.currentTile.x && j == pacman.currentTile.y)
				{
					cellDetails[i-1][j].parent_i = i;
					cellDetails[i-1][j].parent_j = j;
					getPath(cellDetails,pacman.currentTile);
					foundDest = true;
					return getPath(cellDetails,pacman.currentTile);
					
				}else if(closedList[i-1][j] == false && Map.tiles[i-1][j].type != 0 && Map.tiles[i-1][j].type != 5 )
				{
					gNew = cellDetails[i][j].g + 1.0;
	                hNew = Vec2.getMHDistance(new Vec2(i-1,j),pacman.currentTile);
	                fNew = gNew + hNew;
	                
	                if(cellDetails[i-1][j].f == Double.MAX_VALUE || cellDetails[i-1][j].f > fNew)
	                {
	                	
	                	openList.add(cellDetails[i-1][j]);
	                	cellDetails[i - 1][j].f = fNew;
	                    cellDetails[i - 1][j].g = gNew;
	                    cellDetails[i - 1][j].h = hNew;
	                    cellDetails[i - 1][j].parent_i = i;
	                    cellDetails[i - 1][j].parent_j = j;
	                	
	                }
				}
				
				
				
			}

			if(Cell.isValid(i+1, j))
			{
				if(i+1 == pacman.currentTile.x && j == pacman.currentTile.y)
				{
					cellDetails[i+1][j].parent_i = i;
					cellDetails[i+1][j].parent_j = j;
					getPath(cellDetails,pacman.currentTile);
					foundDest = true;
					return getPath(cellDetails,pacman.currentTile);
					
				}else if(closedList[i+1][j] == false && Map.tiles[i+1][j].type != 0 && Map.tiles[i+1][j].type != 5 )
				{
					gNew = cellDetails[i][j].g + 1.0;
	                hNew = Vec2.getMHDistance(new Vec2(i+1,j),pacman.currentTile);
	                fNew = gNew + hNew;
	                
	                if(cellDetails[i+1][j].f == Double.MAX_VALUE || cellDetails[i+1][j].f > fNew)
	                {
	                	openList.add(cellDetails[i+1][j]);
	                	cellDetails[i+1][j].f = fNew;
	                    cellDetails[i+1][j].g = gNew;
	                    cellDetails[i+1][j].h = hNew;
	                    cellDetails[i+1][j].parent_i = i;
	                    cellDetails[i+1][j].parent_j = j;
	                	
	                }
				}
				
				
				
			}
			if(Cell.isValid(i, j+1))
			{
				if(i == pacman.currentTile.x && j+1 == pacman.currentTile.y)
				{
					cellDetails[i][j+1].parent_i = i;
					cellDetails[i][j+1].parent_j = j;
					getPath(cellDetails,pacman.currentTile);
					foundDest = true;
					return getPath(cellDetails,pacman.currentTile);
					
				}else if(closedList[i][j+1] == false && Map.tiles[i][j+1].type != 0 && Map.tiles[i][j+1].type != 5 )
				{
					gNew = cellDetails[i][j].g + 1.0;
	                hNew = Vec2.getMHDistance(new Vec2(i,j+1),pacman.currentTile);
	                fNew = gNew + hNew;
	                
	                if(cellDetails[i][j+1].f == Double.MAX_VALUE || cellDetails[i][j+1].f > fNew)
	                {
	                	openList.add(cellDetails[i][j+1]);
	                	cellDetails[i][j+1].f = fNew;
	                    cellDetails[i][j+1].g = gNew;
	                    cellDetails[i][j+1].h = hNew;
	                    cellDetails[i][j+1].parent_i = i;
	                    cellDetails[i][j+1].parent_j = j;
	                	
	                }
				}
				
				
				
			}
			if(Cell.isValid(i, j-1))
			{
				if(i == pacman.currentTile.x && j-1 == pacman.currentTile.y)
				{
					cellDetails[i][j-1].parent_i = i;
					cellDetails[i][j-1].parent_j = j;
					foundDest = true;
					return getPath(cellDetails,pacman.currentTile);
					
				}else if(closedList[i][j-1] == false && Map.tiles[i][j-1].type != 0 && Map.tiles[i][j-1].type != 5 )
				{
					gNew = cellDetails[i][j].g + 1.0;
	                hNew = Vec2.getMHDistance(new Vec2(i,j-1),pacman.currentTile);
	                fNew = gNew + hNew;
	                
	                if(cellDetails[i][j-1].f == Double.MAX_VALUE || cellDetails[i][j-1].f > fNew)
	                {
	                	openList.add(cellDetails[i][j-1]);
	                	cellDetails[i][j-1].f = fNew;
	                    cellDetails[i][j-1].g = gNew;
	                    cellDetails[i ][j-1].h = hNew;
	                    cellDetails[i][j-1].parent_i = i;
	                    cellDetails[i][j-1].parent_j = j;
	                	
	                }
				}
				
				
				
			}
			
			
		}
		
		return null;
	}
	
	
	public void reset()
	{
		setEatable(false);
		setLayoutX(Map.ghostSpawn.x+((Game.tileSize-Game.tileSize*sizeFactor)/2));
		setLayoutY(Map.ghostSpawn.y+((Game.tileSize-Game.tileSize*sizeFactor)/2));
		nextMoveDirection = 2;
	}
	
	public boolean isEatable()
	{
		return isEatable;
	}
	
	public void setEatable(boolean bool)
	{
		isEatable = bool;
		changeSkin();
	}
	
	
	public void changeSkin()
	{
		Image img = null;
		if(isEatable == false)
		{
			switch(id)
			{
			case 0: img = new Image("file:graphics/blue.gif");break;
			case 1:	img = new Image("file:graphics/green.gif");break;
			case 2:	img = new Image("file:graphics/red.gif");break;
			case 3:	img = new Image("file:graphics/purple.gif");break;
			case 4:	img = new Image("file:graphics/orange.gif");break;
			}
		}
		else
		{
			img = Game.eatableGhost;
		}
		if(img != null)
		{
			setImage(img);
		}
		
		
	}
	
	
	public void setMoves()
	{
		Pacman pacman = Main.currentGame.getPacman();
		double pacmanX = pacman.getLayoutX();
		double pacmanY = pacman.getLayoutY();
		boolean pacmanIsRight = (pacmanX > getLayoutX());
		boolean pacmanIsUnder = (pacmanY > getLayoutY());
		boolean moveXAxis = true;
		double pacmanDistance = Math.sqrt( ((pacmanX-getLayoutX()) * (pacmanX-getLayoutX())) + ((pacmanY-getLayoutY()) * (pacmanY-getLayoutY())) );
		Vec2 direction = new Vec2(pacmanX-getLayoutX(),pacmanY-getLayoutY());
		Vec2 positionPacman = new Vec2(pacman.getLayoutX(),pacman.getLayoutY());
		Vec2 positionGhost = new Vec2(getLayoutX(),getLayoutY());
		if(pacmanDistance >= 200)
		{
				
			 if(randomMoveTimer == 0)
			{
				randomMoveTimer = Main.random.nextInt(1,51);
			}

				if(move() == false)
				{
					nextMoveDirection = Main.random.nextInt(0, 4);
				}
				if(randomMoveCounter == randomMoveTimer)
				{
					
					nextMoveDirection = Main.getRandomWithExclusion(Main.random, 0, 4, getOppositeDirection());
					
					randomMoveTimer = Main.random.nextInt(1,51);
					randomMoveCounter = 0;
				}
				randomMoveCounter++;
		}
		else
		{
			    	for(int i = 0; i < Map.tiles.length;i++)
			    	{
			    		for(int j = 0; j < Map.tiles[0].length;j++)
			    		{
			    			if(Map.tiles[i][j].type == 1)
			    			{
			    				Map.tiles[i][j].setFill(Color.BLACK);
			    			}
			    		}
			    	}
			    	
			    	ArrayList<Vec2> path = getWayToPacman(pacman);
			    	if(path != null)
			    	{
			    		System.out.println(path.get(1).x + " " + path.get(1).y);
			    		System.out.println(currentTile.x + " " + currentTile.y);
			    		System.out.println(moveDirection);
			    		if(path.get(1) == currentTile)
			    		{
			    			path.remove(path.get(1));
			    		}
			    		if(path.get(1).isAbove(currentTile))
			    		{
			    			System.out.println("daw12");
			    			nextMoveDirection = 2;
			    		}else if(path.get(1).isUnder(currentTile))
			    		{
			    			System.out.println("daw34");
			    			nextMoveDirection = 0;
			    		}
			    		else if(path.get(1).isLeftTo(currentTile))
			    		{
			    			System.out.println("daw45");
			    			nextMoveDirection = 1;
			    		}
			    		else if(path.get(1).isRightTo(currentTile))
			    		{
			    			System.out.println("daw56");
			    			nextMoveDirection = 3;
			    		}
			    		if(nextMoveDirection == moveDirection)
			    		{
			    			path.remove(path.get(1));
			    		}
			    	}
			    	move();
			    	
			    }
				
		}
		
		}

	
