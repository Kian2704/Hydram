package me.hydram.accounts;

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
	
	public void resetLevel()
	{
		currentLevel = 1;
		handler.updateValues(this);
	}
	
	public void setHighscore(int newHighscore)
	{
		highscore = newHighscore;
		handler.updateValues(this);
	}
	public void setHighestLevel(int newLevel)
	{	
		highestLevel = newLevel;
		handler.updateValues(this);
	}
}
