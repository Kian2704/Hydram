package me.hydram.accounts;

import java.math.BigInteger;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class Accounts {
	
	String hostname = "localhost";
	String database = "root";
	String username = "root";
	String password = "password";
	Connection connection;
	private MySQLHandler handler;
	private  List<String> errorMessages = new ArrayList<String>();
	
	private User user = null;
	
	
	public void addErrorMessage(String message)
	{
		errorMessages.add(message);
	}
	public List<String> getErrorMessages()
	{
		List<String> temp = errorMessages;
		return temp;
		
	}
	public List<Label> getErrorLabels()
	{
		List<Label> labels = new ArrayList<Label>();
		for(String error : getErrorMessages())
		{
			Label label = new Label(error);
			label.setTextFill(Color.DARKRED);
			labels.add(label);
		}
		errorMessages.clear();
		return labels;
		
		
	}
	
	public boolean isTimedOut()
	{
		try {
			if(!connection.isValid(1))
			{
				return true;
			}
		} catch (SQLException e) {
			return true;
		}
		return false;
	}
	
	
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
	
	public boolean register(String username,String password,String passwordCheck)
	{
		if(!password.equals(passwordCheck))
		{
			this.addErrorMessage("Passwords not matching!");
			return false;
		}
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
