package com.Ecom.util;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

	public class PropertyUtil {

	    public static String getPropertyString(String filename) {
	        Properties props = new Properties();

	        try (FileInputStream fis = new FileInputStream(filename)) {
	            props.load(fis);
	            String url = props.getProperty("db.url");
	            String user = props.getProperty("db.user");
	            String pass = props.getProperty("db.password");

	            return url + "?user=" + user + "&password=" + pass;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	}



