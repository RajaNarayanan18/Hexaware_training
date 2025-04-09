package com.insurance_ms.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class DBPropertyUtil {
	
	    public static Properties loadProperties() {
	        Properties props = new Properties();
	        try (FileInputStream fis = new FileInputStream("db.properties")) {
	            props.load(fis);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return props;
	    }
	}


