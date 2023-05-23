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
	
	String hostname = "db4free.net:3306";
	String database = "hydram";
	String username = "hydramadmin";
	String password = "!K14n12tC007!";
	Connection connection;
	private MySQLHandler handler;
	private  List<String> errorMessages = new ArrayList<String>();
	
	private User user = null;
	
	/*
	 Fügt eine Fehlermeldung hinzu welche später im Login Bildschirm angezeigt wird.
	 */
	public void addErrorMessage(String message)
	{
		errorMessages.add(message);
	}
	/*
	 Ruft vorhandene Fehlermeldungen ab und gibt diese als String Liste zurück.
	 */
	public List<String> getErrorMessages()
	{
		List<String> temp = errorMessages;
		return temp;
		
	}
	/*
	 Ruft vorhandene Fehlermeldungen ab und gibt diese als Label Liste zurück.Löscht danach alle vorhandenen Fehlermeldungen.
	 */
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
	/*
	 Überprüft ob der Nutzer eine Verbindung zu der Datenbank herstellen kann
	 */
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
	
	/*
	 Konstruktor der Klasse. Baut die Verbindung zur Datenbank auf
	 */
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
	}
	/*
	 Gibt ein Objekt vom Typ User zurück
	 */
	public User getUser()
	{
		return user;
	}
	/*
	 Verschlüsselt das Passwort
	 */
	public static String hashPassword(String username,String password)
	{
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
	
	/*
	 Überprüft die Anmeldedaten des Nutzers und meldet ihn an wenn die Daten mit der Datenbank übereinstimmen. Sonst return false
	 */
	
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
	/*
	 Registriert einen Nutzer in der Datenbank.
	 */
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


}
