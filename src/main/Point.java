package main;

public class Point extends StaticEntity {

	public Point()
	{
		super();
		type = 3;
		sizeFactor = .2;
		setFitHeight(Game.tileSize*sizeFactor);
		height = getFitHeight();
		setFitWidth(Game.tileSize*sizeFactor);
		width = getFitWidth();
		setImage(Game.pointTexture);
	}
	
	public void collect()
	{
		
	}
	
}
