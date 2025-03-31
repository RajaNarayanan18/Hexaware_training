package test1;
import  test.test;

public class test3 {
	
	public static void main(String[] args) {
		try {
            // Code that may cause an exception
            int result = 10 / 0; // This will cause ArithmeticException
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            // Handling the exception
            System.out.println("Error: Division by zero is not allowed.");
        } finally {
            // This block always executes
            System.out.println("Execution completed.");
        }
		test obj=new test();
		obj.sum(9, 6);
	}

}
