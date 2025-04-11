package myproject;
import java.sql.*;

public class jdbcexample {
	
	public static void main(String [] args) throws ClassNotFoundException, SQLException {
		//load driver class
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hexaware","root","Raja@2003");
		System.out.println("Connection created");
		
		
		
		Statement smt = con.createStatement();
/*
	//	String sql  = "INSERT INTO students VALUES (3, 'Prince')";
	//	String sql1  = "INSERT INTO students VALUES (4, 'Raja')";
     //   String sql2 = "DELETE FROM students WHERE stid = 3";
        String sql3 = "UPDATE students SET stname = 'Ram' WHERE stid = 4";

		//int x = smt.executeUpdate(sql);
	//	int x2 = smt.executeUpdate(sql1);
		//int x1 = smt.executeUpdate(sql2);
		int x3 = smt.executeUpdate(sql3);

		
	//	System.out.println(x + " row(s) inserted"); 
	//	System.out.println(x2 + " row(s) inserted");
		//System.out.println(x1 + " row(s) deleted");
		System.out.println(x3 + " row(s) updated");*/
	
		con.setAutoCommit(false);
		
		 try(Statement stmt = con.createStatement()) {
		stmt.addBatch("INSERT INTO Students VALUES (3, 'Prince')");
     stmt.addBatch("INSERT INTO Students VALUES (5, 'Raja')");
     stmt.addBatch("INSERT INTO Students VALUES (1, 'alice')");
       //  stmt.addBatch("DELETE FROM Students WHERE stid = 3");
     ResultSet rs=smt.executeQuery("select * from students");
     while(rs.next()) {
    	 System.out.println(rs.getInt(1)+" "+rs.getString(2));
     }
            
       
    //   stmt.addBatch("DELETE FROM Students WHERE stid = 4");

        // Execute batch
        int[] results = stmt.executeBatch();
        con.commit(); // Commit changes

        System.out.println(results.length + " statements executed successfully.");
        }catch(Exception e) {
        	System.out.println(e);
        }


		 
		 }}
	


