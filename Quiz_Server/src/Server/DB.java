package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class DB {

	
	static Connection conn;
	public static  String host;
	public static  String db_name;
	public static  String username;
	public static  String password;
	public static int port;
	
	public static  String url;
	
	DB() throws SQLException, ClassNotFoundException{
		

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
	
	public void createTables() throws SQLException {
		
		String table1 = "CREATE TABLE `"+ db_name +"`.`connections` ( `s_no` INT(5) NOT NULL AUTO_INCREMENT ,  `name` VARCHAR(255) NOT NULL ,  `rollno` VARCHAR(20) NOT NULL ,  `marks` INT(3) NOT NULL ,    PRIMARY KEY  (`s_no`)) ENGINE = InnoDB;";
		conn.prepareStatement(table1).executeUpdate();
				
		String table2 = "CREATE TABLE `"+ db_name +"`.`checker` ( `s_no` INT(3) NOT NULL AUTO_INCREMENT ,  `is_ready` VARCHAR(9) NOT NULL ,  `is_start` VARCHAR(9) NOT NULL ,    PRIMARY KEY  (`s_no`)) ENGINE = InnoDB;";
		conn.prepareStatement(table2).executeUpdate();
		
		String table3 = "CREATE TABLE `"+ db_name +"`.`questions` ( `s_no` INT(5) NOT NULL AUTO_INCREMENT ,  `question` VARCHAR(1000) NOT NULL ,  `option_a` VARCHAR(255) NOT NULL ,  `option_b` VARCHAR(255) NOT NULL ,  `option_c` VARCHAR(255) NOT NULL ,  `option_d` VARCHAR(255) NOT NULL ,  `answer` VARCHAR(255) NOT NULL ,    PRIMARY KEY  (`s_no`)) ENGINE = InnoDB;";
		conn.prepareStatement(table3).executeUpdate();
		
		
		String dataquery = "INSERT INTO `checker` (`s_no`, `is_ready`, `is_start`) VALUES ('1', 'false', 'false');";

		PreparedStatement pstmt = conn.prepareStatement(dataquery);
		pstmt.executeUpdate();  
		
	
	}
	
	
	public Connection createConnection()  {
			return conn;
	}
	public void closeConnection() throws SQLException {
		conn.close();
	}
	
	public void addQuestion(String question , String optionA, String optionB, String optionC, String optionD, String answer) throws SQLException {
		
		String query = "INSERT INTO `questions` (`s_no`, `question`, `option_a`, `option_b`, `option_c`, `option_d`, `answer`) VALUES (NULL, '"+ question +"', '"+ optionA +"', '"+ optionB +"', '"+ optionC +"', '" + optionD + "', '"+answer+"');";
		
		PreparedStatement pstmt = conn.prepareStatement(query);
		
		pstmt.executeUpdate();  
		
		
	}
	public void deleteResult() throws SQLException {
		String query = "TRUNCATE TABLE `connections`;";
		
		conn.prepareStatement(query).executeUpdate();
		
	}
	
	public void deleteAllQuestions() throws SQLException {
		String query = "TRUNCATE TABLE `questions`;";
		
		conn.prepareStatement(query).executeUpdate();
		
	}
	
	public static ResultSet getAllData() throws SQLException {
		
		String query = "SELECT * FROM questions";
		
		ResultSet set = conn.createStatement().executeQuery(query);
		
		return set;
		
	}
	
	public String isReady() throws SQLException {
		String query = "SELECT is_ready FROM checker WHERE s_no = 1 ";
		
		ResultSet set = conn.createStatement().executeQuery(query);
		
		String result = null;
		
		while(set.next()) {
			result = set.getString(1);
		}
		
		return result;
	}
	
	public void readyToBegin() throws SQLException{
		String query = "UPDATE `checker` SET `is_ready` = 'true' WHERE `checker`.`s_no` = 1;";
		conn.prepareStatement(query).executeUpdate();
	}
	
	
	public void readyTimeOver() throws SQLException{
		String query = "UPDATE `checker` SET `is_ready` = 'false' WHERE `checker`.`s_no` = 1;";
		conn.prepareStatement(query).executeUpdate();
	}
	
	public void startExam() throws SQLException{
		String query = "UPDATE `checker` SET `is_start` = 'true' WHERE `checker`.`s_no` = 1;";
		conn.prepareStatement(query).executeUpdate();
	}
	public void startExamOver() throws SQLException{
		String query = "UPDATE `checker` SET `is_start` = 'false' WHERE `checker`.`s_no` = 1;";
		conn.prepareStatement(query).executeUpdate();
	}
	
	public void restoreDefault() throws SQLException {
		readyTimeOver();
		startExamOver();
	}
	
	public String[][] getResult() throws SQLException {
		
		String query = "SELECT * FROM connections";
		ResultSet set = conn.createStatement().executeQuery(query);
		
		ArrayList<String[]> list = new ArrayList<>();
		
		while(set.next()) {
			
			String[] arr = {set.getString(2),
							set.getString(3),
							set.getString(4)};
			list.add(arr);
			
		}
		
		
		
		String[][] arra = new String[list.size()][3];
		for(int i = 0 ; i < list.size() ; i++) {

			for(int j = 0; j< list.get(i).length;j++) {
				 arra[i][j] = list.get(i)[j];
			}
		}
		
		
		
		return arra;
	}
	
	
	
}
