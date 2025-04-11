package test;

public class test {
	
	public void sum(int x, int y) {
		System.out.println("the sum is "+ (x+y));
	}
	private void add(int x, int y) {
		System.out.println("the sum is "+ (x+y));
	}
	protected  void sub(int x, int y) {
		System.out.println("the sum is "+ (x+y));
	}
	
	
	public static void main( String[] args)
	{
		
		test obj=new test();
		obj.add(7, 6);
		obj.sub(9, 3);
		//int arr[]= {1,2,3,4,5};
		//for(int i=0;i<5;i++) {
			//System.out.println(arr[i]);
	
		/* enhanced for loop
	 	
		int arr[]=new int[10];
		arr[0]=1;
		arr[2]=2;
		for(int z : arr) {
			System.out.println(z);
			*/
		
		}
		
		
	}

