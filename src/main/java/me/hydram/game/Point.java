package me.hydram.game;

//Klasse der Goldenen Punkte welche Pacman aufsammeln muss
public class Point extends StaticEntity {
	
	//Erstellt den Punkt
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
}
