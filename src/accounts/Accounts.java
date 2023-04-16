package accounts;

import java.math.BigInteger;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Accounts {
	
	String hostname = "db4free.net:3306";
	String database = "hydram";
	String username = "hydramadmin";
	String password = "!K14n12tC007!";
	Connection connection;
	private MySQLHandler handler;
	
	private User user = null;
	
	public Accounts()
	{
		
		String url = String.format("jdbc:mysql://%s/%s", hostname,database);
		
			try {
				connection = DriverManager.getConnection(url, username, password);
			} catch (Exception e) {
				connection = null;
				e.printStackTrace();
			}
			handler = new MySQLHandler(this);
			//sampleAccount();
	}
	
	public User getUser()
	{
		return user;
	}
	
	public static String hashPassword(String username,String password)
	{
		//peter;1  	[B@169f7064
		byte[] salt = username.getBytes();
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 512);
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			byte[] hash = factory.generateSecret(spec).getEncoded();
			String hashString = new String(hash);
			return new BigInteger(hash).toString();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public boolean login(String username,String password)
	{
		User user = handler.login(username, hashPassword(username,password));
		if(user != null)
		{
			this.user = user;
			return true;
		}
		else
		{
			return false;
		}
			
	}
	
	public boolean register(String username,String password)
	{
		boolean valid = handler.register(username, hashPassword(username,password));
		return valid;
		
	}
	
	
	
	
	
	
	
	public boolean sampleAccount()
	{
		String uuid = UUID.randomUUID().toString();
		String sql = "INSERT INTO accounts(uuid,username,password) VALUES ('" + uuid + "','FettsackLP5','Hallo123')";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
			
		}
		
	}

}
