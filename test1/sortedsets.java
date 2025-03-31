package test1;

//import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Queue;
import java.util.LinkedList;


public class sortedsets {
	
	
	public static void main(String[] args) {
		
		Queue<Integer>q=new LinkedList<>();
		q.add(10);
		q.add(1);
		q.add(1);
		
		System.out.println(q);
		System.out.println(q.poll());

		System.out.println(q.peek());
		System.out.println(q.poll());
		System.out.println(q.poll());
		System.out.println(q.poll()); //returns null




		
		
		 TreeSet<Integer> set = new TreeSet<>();
	        set.add(10);
	        set.add(5);
	        set.add(20);
	        set.add(15);
	        System.out.println(set.reversed());
		
	        TreeSet<String> s = new TreeSet<>();
	        s.add("Prince");
	        s.add("ajay");
	        s.add("zee");
	        s.add("rama");

	        System.out.println(s);
	        System.out.println(s.descendingSet());
	        System.out.println(s.reversed());
	        
	        //object to integer conversion 


	        
				
		
	}

}
