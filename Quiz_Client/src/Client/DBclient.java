package Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class DBclient {
	static Connection conn;
	public static String host;
	public static String db_name;
	public static String username;
	public static String password;
	public static int port;
	public static String url;
	
	
	DBclient() throws SQLException, ClassNotFoundException{
		
		
		try {
			FileInputStream fin = new FileInputStream(new File("files/db.txt"));
			Scanner sc = new Scanner(fin);
			
			host = sc.nextLine();
			db_name = sc.nextLine();
			username = sc.nextLine();
			password = sc.nextLine();
			port = Integer.parseInt(sc.nextLine());
			
			sc.close();
			url = "jdbc:mysql://"+ host +":"+ port +"/"+ db_name;
		} catch (FileNotFoundException e) {
			Const.showError("Error", "Database entry file not found !");
		}
		
		
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conn = DriverManager.getConnection(url,username,password);
	}
	
	
	public Connection getConnection()  {
			return conn;
	}
	
	// CHECKING FOR READY
	public boolean checkReady() throws SQLException {
	
		String q = "SELECT * FROM checker WHERE s_no = 1;";
		boolean isReady = false;
		
		ResultSet rset = conn.createStatement().executeQuery(q);
		
		while(rset.next()){
			
			String name = rset.getString(2);
			
			if(name.equalsIgnoreCase("true")) {
				isReady = true;
			}
		
		}
		if(isReady) {
			
			return true;
		}
		return false;
	
	}
	
	// CHECKING FOR start
		
	public boolean checkStart() throws SQLException {
			
		String q = "SELECT * FROM checker WHERE s_no = 1;";
		boolean isReady = false;
		
		ResultSet rset = conn.createStatement().executeQuery(q);
		
		while(rset.next()){
			
			String name = rset.getString(3);
			
			if(name.equalsIgnoreCase("true")) {
				isReady = true;
			}
		
		}
		if(isReady) {
			
			return true;
		}
		return false;
	
	}

	// FETCHING THE DATA
	public  ResultSet getAllData() throws SQLException {
		
		String query = "SELECT * FROM questions";
		
		ResultSet set = conn.createStatement().executeQuery(query);
		
		return set;
		
	}
	
	public ArrayList<String> getAllQuestions() throws SQLException {
		String query = "SELECT * FROM questions";
		
		ResultSet set = conn.createStatement().executeQuery(query);
		
		ArrayList<String> questions = new ArrayList<>();
		
		while(set.next()) {
			questions.add(set.getString(2));
		}
		
		return questions;
	}
	
	public ArrayList<ArrayList<String>> getAllOptions() throws SQLException {
		String query = "SELECT * FROM questions";
		
		ResultSet set = conn.createStatement().executeQuery(query);
		
		ArrayList<ArrayList<String>> options = new ArrayList<>();
		
		while(set.next()) {
			ArrayList<String> opt = new ArrayList<>();
			opt.add(set.getString(3));
			opt.add(set.getString(4));
			opt.add(set.getString(5));
			opt.add(set.getString(6));
			
			options.add(opt);
			
		}
		
		
		
		return options;
		
	}
	
	public ArrayList<String> getAllAnswers() throws SQLException {
		String query = "SELECT * FROM questions";
		
		ResultSet set = conn.createStatement().executeQuery(query);
		
		ArrayList<String> answers = new ArrayList<>();
		
		while(set.next()) {
			answers.add(set.getString(7));
		}
		
		return answers;
	}
	
	
	public void closeConnection() throws SQLException {
		conn.close();
	}
	
	
	public void submitAnswer(String name, String roll, int marks) throws SQLException {
		
		String query = "INSERT INTO `connections` (`s_no`, `name`, `rollno`, `marks`) VALUES (NULL, '"+ name +"', '"+ roll +"', '"+ marks +"');";
		
		conn.prepareStatement(query).executeUpdate();
		
	}
	
	
}
