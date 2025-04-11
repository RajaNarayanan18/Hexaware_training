package myproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class indynamic {
	
	public static void main(String [] args) throws ClassNotFoundException, SQLException {
		//load driver class
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection c =DriverManager.getConnection("jdbc:mysql://localhost:3306/hexaware","root","Raja@2003");
		System.out.println("Connection created");
		
		Scanner sc=new Scanner(System.in);
		System.out.println("1 = insert and 2= delete");
		int opt=sc.nextInt();
		
		
		//dynamic data input
		if(opt==1) {
		// M2	String s1="Insert into students values(?,?)";
			PreparedStatement s= c.prepareStatement("Insert into students values(?,?)");		
			
			//getting inputs in runtime
			System.out.println("enter the stdid:");
			int stid=sc.nextInt();
			System.out.println("enter the student name");
			String stname=sc.next();
			
			s.setInt(1, stid);
			s.setString(2, stname);
			int x=s.executeUpdate();
			
			System.out.println(x+ "rows got inserted");
			
			
		}else {
			//delete 1,3,4..
			
			String s2="delete from students where stid=?";
			PreparedStatement a=c.prepareStatement(s2);
			 System.out.println("Enter Student ID to delete:");
	         int stid = sc.nextInt();

	         a.setInt(1, stid);
	         int x = a.executeUpdate();
	         System.out.println(x + " rows got deleted");
		}
			
		}
		
		
		
		

}

