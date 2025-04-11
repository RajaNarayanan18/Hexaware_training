package myproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class meta {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection ct=DriverManager.getConnection("jdbc:mysql://localhost:3306/hexaware","root","Raja@2003");
		
		System.out.println("Connection established");
		 Statement st=ct.createStatement();
		 ResultSet resultSet = st.executeQuery("select * from Students");
		 
		ResultSetMetaData rs = resultSet.getMetaData();

		int cc=rs.getColumnCount();
		System.out.println("total column: "+ cc);
		Scanner sc=new Scanner(System.in);
		
		System.out.println("enter the option to proceed 1= getdata and 2=insert");
		int opt=sc.nextInt();
		if(opt==1) {
		
		for (int i = 1; i <= rs.getColumnCount(); i++) {
		    System.out.print(rs.getColumnName(i)+"\t");
		   
		}
		 System.out.println(" ");
		while(resultSet.next()) {
			   System.out.println(resultSet.getInt(1)+"\t"+resultSet.getString(2));

		}
		}else {
		/* inline input or static query	
		 String sql  = "INSERT INTO students VALUES (3, 'Prince')";
			int x=st.executeUpdate(sql);
			System.out.println(x+ " rows got inserted");
*/
           PreparedStatement s= ct.prepareStatement("Insert into students values(?,?)");		
			
			//getting inputs in runtime
			System.out.println("enter the stdid:");
			int stid=sc.nextInt();
			System.out.println("enter the student name");
			String stname=sc.next();
			
			s.setInt(1, stid);
			s.setString(2, stname);
			int x=s.executeUpdate();
			
			System.out.println(x+ "rows got inserted");

		}
		
	}

}
