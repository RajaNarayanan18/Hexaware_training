package test;
import java.util.HashMap;
import java.util.Map;

public class string {
	
	public static void main( String [] args) {
		// reverse a string
		String s1="Hexaware";
		for(int i=s1.length()-1;i>=0;i--) {
			System.out.print(s1.charAt(i));
			
		}
		System.out.println(" ");
		
		// check the string is palindrome or not
		String s2="MadaM";
		System.out.println(s2.equals(new StringBuilder(s2).reverse().toString()));
	
	//count number of vowels and consonants
	String s3="Aaja";
	s3=s3.toLowerCase();
	int v=0,c=0;
	for(int i=0;i<=s3.length()-1;i++) {
		if(s3.charAt(i)=='a' || s3.charAt(i)=='e' || s3.charAt(i)=='i'|| s3.charAt(i)=='o'|| s3.charAt(i)=='u') {
			v+=1;
		}else {
			c+=1;
		}
		
	}
	System.out.println(v);
	System.out.println(c);
	
	//finding 1st non-repeating characters
	for(char ch:s1.toCharArray()) {
		if(s1.indexOf(ch)==s1.lastIndexOf(ch)) {
			System.out.println(ch);
			break;
		}
	}
	//finding most frequent char in string
	String str="success";
	 Map<Character, Integer> freqMap = new HashMap<>();
     char maxChar = str.charAt(0);
     int maxCount = 0;

     for (char ch : str.toCharArray()) {
         freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
         if (freqMap.get(ch) > maxCount) {
             maxCount = freqMap.get(ch);
             maxChar = ch;
         }
     }

     System.out.println(maxChar);
		
	}

}
