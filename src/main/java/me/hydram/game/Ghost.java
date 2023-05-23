package me.hydram.game;
import java.util.ArrayList;
import java.util.PriorityQueue;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;

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
		freeze(3000+id*2000);
		
		
		
	}
	
	public int getGhostId()
	{ 
		return id;
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
					//Map.tiles[(int) p.x][(int) p.y].setFill(Color.RED);
					returnPath.add(path.get(path.size()-1));
					path.remove(path.size()-1);
					
					
				}

				return returnPath;
		}
			return null;
	}
	
	
	

	public ArrayList<Vec2> findWayToVec2(Vec2 tile)
	{
		if(currentTile == null || tile == null)
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
				if(i-1 == tile.x && j == tile.y)
				{
					cellDetails[i-1][j].parent_i = i;
					cellDetails[i-1][j].parent_j = j;
					getPath(cellDetails,tile);
					foundDest = true;
					return getPath(cellDetails,tile);
					
				}else if(closedList[i-1][j] == false && Map.tiles[i-1][j].type != 0 && Map.tiles[i-1][j].type != 5 && Map.tiles[i-1][j].type != 7 )
				{
					gNew = cellDetails[i][j].g + 1.0;
	                hNew = Vec2.getMHDistance(new Vec2(i-1,j),tile);
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
				if(i+1 == tile.x && j == tile.y)
				{
					cellDetails[i+1][j].parent_i = i;
					cellDetails[i+1][j].parent_j = j;
					getPath(cellDetails,tile);
					foundDest = true;
					return getPath(cellDetails,tile);
					
				}else if(closedList[i+1][j] == false && Map.tiles[i+1][j].type != 0 && Map.tiles[i+1][j].type != 5 && Map.tiles[i-1][j].type != 7 )
				{
					gNew = cellDetails[i][j].g + 1.0;
	                hNew = Vec2.getMHDistance(new Vec2(i+1,j),tile);
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
				if(i == tile.x && j+1 == tile.y)
				{
					cellDetails[i][j+1].parent_i = i;
					cellDetails[i][j+1].parent_j = j;
					getPath(cellDetails,tile);
					foundDest = true;
					return getPath(cellDetails,tile);
					
				}else if(closedList[i][j+1] == false && Map.tiles[i][j+1].type != 0 && Map.tiles[i][j+1].type != 5 && Map.tiles[i-1][j].type != 7 )
				{
					gNew = cellDetails[i][j].g + 1.0;
	                hNew = Vec2.getMHDistance(new Vec2(i,j+1),tile);
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
				if(i == tile.x && j-1 == tile.y)
				{
					cellDetails[i][j-1].parent_i = i;
					cellDetails[i][j-1].parent_j = j;
					foundDest = true;
					return getPath(cellDetails,tile);
					
				}else if(closedList[i][j-1] == false && Map.tiles[i][j-1].type != 0 && Map.tiles[i][j-1].type != 5 && Map.tiles[i-1][j].type != 7 )
				{
					gNew = cellDetails[i][j].g + 1.0;
	                hNew = Vec2.getMHDistance(new Vec2(i,j-1),tile);
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
		setNextMoveDirection(2);
		freeze(3000+2000*id);
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
		Image img = Graphics.getGhostTexture(this);
		if(img != null)
		{
			this.setImage(img);
		}
		
		
	}
	
	
	public void setMoves()
	{

		Bounds bounds = this.getBoundsInParent();
//		boolean outOfMap = false;
//		if(bounds.getMinX() < 0 || bounds.getMaxX() > Main.screenWidth || bounds.getMaxY() > Main.screenHeight || bounds.getMinY() < 0)
//		{
//			outOfMap = true;
//		}
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
				randomMoveTimer = Main.random.nextInt(50);
			}

				if(move() == false )
				{
					setNextMoveDirection(Main.random.nextInt(5)); 
				}
				if(randomMoveCounter == randomMoveTimer )
				{
					
					setNextMoveDirection(Main.getRandomWithExclusion(Main.random, 0, 4, getOppositeDirection()));
					
					randomMoveTimer = Main.random.nextInt(50);
					randomMoveCounter = 0;
				}
				randomMoveCounter++;
		}
		else
		{
			    	
			    	ArrayList<Vec2> path = findWayToVec2(pacman.currentTile);
			    	if(path != null)
			    	{

			    		if(path.get(1) == currentTile)
			    		{
			    			path.remove(path.get(1));
			    		}
			    		if(path.get(1).isAbove(currentTile))
			    		{
			    			setNextMoveDirection(2);
			    		}else if(path.get(1).isUnder(currentTile))
			    		{
			    			setNextMoveDirection(0);
			    		}
			    		else if(path.get(1).isLeftTo(currentTile))
			    		{
			    			setNextMoveDirection(1);
			    		}
			    		else if(path.get(1).isRightTo(currentTile))
			    		{
			    			setNextMoveDirection(3);
			    		}
			    		if(getNextMoveDirection() == moveDirection)
			    		{
			    			path.remove(path.get(1));
			    		}
			    	}
			    	move();
			    	
			    }
				
		}
		
		}

	
