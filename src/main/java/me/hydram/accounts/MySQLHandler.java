package me.hydram.accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * Klasse zur Kommunikation mit der Datenbank
 */


public class MySQLHandler {
	
	Connection connection;
	Accounts account;
	ExecutorService executor = Executors.newSingleThreadExecutor();
	//Konstruktor
	MySQLHandler(Accounts accounts)
	{
		this.connection = accounts.connection;
		account = accounts;
	}
	/*
		Datenbankabfrage der eingegebenen Nutzerdaten, gibt den Nutzer zurück oder null wenn die Daten nicht übereinstimment
	 */
	protected User login(String username,String password)
	{
		String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?"; 
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet results = stmt.executeQuery();
			if(!results.next())
			{
				account.addErrorMessage("Invalid Username or Password!");
				return null;
			}
			String uuid = results.getString("uuid");
			int highestLevel = results.getInt("highestLevel");
			int currentLevel = results.getInt("currentLevel");
			int highscore = results.getInt("highscore");
			boolean isDeveloper = results.getBoolean("developer");
			
			return new User(username,uuid,highscore,highestLevel,currentLevel,isDeveloper,this);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	/*
	 * Datenbankabfrage zu der Verfügbarkeit eines Nutzernamen. true wenn verfügbar, sonst false
	 */
	protected boolean usernameAvailable(String username)
	{
		String sql = "SELECT * FROM accounts WHERE username = ?";
		try
		{
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet results = stmt.executeQuery();
			if (!results.next())
			{
				return true;
			}
			return false;
		}catch(Exception e)
		{
			return false;
		}
	}
	
	/*
	 * Erstellt einen Datenbankeintrag für die eingegebenen Nutzerdaten-
	 */
	
	protected boolean register(String username,String password)
	{
		String uuid = UUID.randomUUID().toString();
		if(password == null)
			return false;
		if(!usernameAvailable(username))
		{
			account.addErrorMessage("Username already taken!");
			return false;
		}
		String sql = "INSERT INTO accounts (uuid,username,password) VALUES (?,?,?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, uuid);
			stmt.setString(2, username);
			stmt.setString(3, password);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Aktualisiert die Nutzerdaten wie den Highscore oder das höchste erreichte LEvel
	 */
	protected void updateValues(User user)
	{
		
		executor.submit(() -> {
			String uuid = user.getUUID();
			
			String sql = String.format("UPDATE accounts SET highscore = %d,highestLevel = %d, currentLevel = %d WHERE uuid='%s'", user.getHighscore(),user.getHighestLevel(),user.getCurrentLevel(),uuid);
			
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				return false;
			}
		});
		
		
		
	}

}
