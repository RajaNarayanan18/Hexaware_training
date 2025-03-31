package test1;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
 

public class list {
	
	
	public static void main(String[] args) {
		
		List l=new LinkedList();
		
		
		l.add(Integer.valueOf(10));
		l.add(Integer.valueOf(100));
		l.add(200);
		l.add( "Raja");
		System.out.println(l.contains(10));
		System.out.println(l.contains(200));
		l.addLast(Integer.valueOf(1));
		System.out.println(l.size());
		System.out.println(l.contains("Raja"));
		
		
List<String> fruits = new ArrayList<>();
        
        // 2. Add elements
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Orange");
        fruits.add("Mango");
        System.out.println("Initial list: " + fruits);
        
        System.out.println("the size of arraylist:" +fruits.size());        
        // 3. Add element at specific index
        fruits.add(2, "Grapes");
        System.out.println("After adding Grapes at index 2: " + fruits);
        
        // 4. Get element by index
        System.out.println("Element at index 3: " + fruits.get(3));
        

	}

}
