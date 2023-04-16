package accounts;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySQLHandler {
	
	Connection connection;
	ExecutorService executor = Executors.newSingleThreadExecutor();
	MySQLHandler(Accounts accounts)
	{
		this.connection = accounts.connection;
	}
	
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
	
	protected boolean register(String username,String password)
	{
		String uuid = UUID.randomUUID().toString();
		if(password == null)
			return false;
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
