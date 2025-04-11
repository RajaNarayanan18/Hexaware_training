package util;

import java.io.*;
import java.util.*;

public class DBPropertyUtil {
    public static Properties loadProperties(String fileName) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(fileName)) {
            props.load(fis);
        }
        return props;
    }
}