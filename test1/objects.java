package test1;
import java.util.*;


public class objects {
	int id;
    String name;

    public objects(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return "Student{id=" + id + ", name='" + name + "'}";
    }
    
    public static void main(String[] args) {
    	List<objects> l = new ArrayList<>();
    	
    	objects r=new objects(100,"prince");
    	objects s=new objects(101,"Raja");
    	l.add(s);
    	l.add(r);
    	for(objects st:l) {
    		System.out.println(st);
    	}
    	

    	
}

}
