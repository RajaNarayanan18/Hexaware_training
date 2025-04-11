package dbconnection;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;
 

public class db {
	
	
	
	
	
	
		
		public static void main(String[] args) {
			try {
			//load the driver class
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("driver class is loaded");
			
			//establish the connection
			
		//	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hexware", "root", "root");
			
			System.out.println("connection also got established");
			//create the object for Statement interface - to send the sql queries from java to db
			
		//	Statement statement = connection.createStatement();
			
		//	String sql  = "insert into student values(100,'rk')";
			
		//	int x = statement.executeUpdate(sql);
			
		//	System.out.println(x + "row(s) inserted");
			
			
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
	 
	
}
