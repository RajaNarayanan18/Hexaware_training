package myproject;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Databaseconfig {
	
	    public static void main(String[] args) throws IOException, java.sql.SQLException {
	    	
	        Properties properties = new Properties();
	        FileInputStream fis = new FileInputStream("application.properties");
	        properties.load(fis);
	        fis.close();

	        String url = properties.getProperty("db.url");
	        String user = properties.getProperty("db.user");
	        String password = properties.getProperty("db.password");

	        Connection connection = DriverManager.getConnection(url, user, password);
	        System.out.println("Connected to database successfully!");
	        
	        
	        Statement st=connection.createStatement();
	        connection.setAutoCommit(false);
	        int x=st.executeUpdate("update prince set bal=bal+200 where accno=100");
	        int y=st.executeUpdate("update king  set bal=bal-100 where accno=100");
	        if(x>0 && y>0) {
	        	System.out.println("operation got performed");
	        	connection.commit();
	        }else {
	        	System.out.println("error");
	        	connection.rollback();
	        }
	        /*
			 ResultSet resultSet = st.executeQuery("select * from Students");
			 
			ResultSetMetaData rs = resultSet.getMetaData();

			int cc=rs.getColumnCount();
			System.out.println("total column: "+ cc);
		//	Scanner sc=new Scanner(System.in);
			
		//	System.out.println("enter the option to proceed 1= getdata and 2=insert");
		//	int opt=sc.nextInt();
		//	if(opt==1) {
			
			for (int i = 1; i <= rs.getColumnCount(); i++) {
			    System.out.print(rs.getColumnName(i)+"\t");
			   
			}
			 System.out.println(" ");
			while(resultSet.next()) {
				   System.out.println(resultSet.getInt(1)+"\t"+resultSet.getString(2));

			}
			*/

	    }
	}



