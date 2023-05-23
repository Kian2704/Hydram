package me.hydram.accounts;



//Klasse welche Daten über den Nutzer enthält
public class User {
	String username;
	String uuid;
	int highscore;
	int highestLevel;
	int currentLevel;
	boolean developer;
	MySQLHandler handler;
	
	public User(String username,String uuid,int highscore,int highestLevel,int currentLevel,boolean developer,MySQLHandler handler)
	{
		this.username = username;
		this.uuid = uuid;
		this.highscore = highscore;
		this.highestLevel = highestLevel;
		this.handler = handler;
		this.developer = developer;
		this.currentLevel = currentLevel;
	}
	
	
	/*
	 * Getter Methoden
	 */
	
	public String getUsername()
	{
		return username;
	}
	public String getUUID()
	{
		return uuid;
	}
	public boolean isDeveloper()
	{
		return this.developer;
	}
	public int getHighscore()
	{
		return highscore;
	}
	public int getCurrentLevel()
	{
		return currentLevel;
	}
	public int getHighestLevel()
	{
		return highestLevel;
	}
	
	
	//Erhöht das Level des Nutzers
	public int increaseLevel()
	{
		currentLevel++;
		if(currentLevel > highestLevel)
		{
			highestLevel = currentLevel;
			handler.updateValues(this);
		}
		return currentLevel;
	}
	
	//Setzt das aktuelle Level zurück auf 1
	public void resetLevel()
	{
		currentLevel = 1;
		handler.updateValues(this);
	}
	
	//Setzt den Highscore auf den aktuellen höchsten Punktestand
	public void setHighscore(int newHighscore)
	{
		highscore = newHighscore;
		handler.updateValues(this);
	}
	//Setzt das höchste Level auf das aktuelle Level
	public void setHighestLevel(int newLevel)
	{	
		highestLevel = newLevel;
		handler.updateValues(this);
	}
}
