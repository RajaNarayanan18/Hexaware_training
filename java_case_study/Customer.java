package java_case_study;

public class Customer {
	
	    private int customerId;
	    private String name;
	    private String email;
	    private String password;

	    public Customer(int customerId, String name, String email, String password) {
	        this.customerId = customerId;
	        this.name = name;
	        this.email = email;
	        this.password = password;
	    }

	    public int getCustomerId() { return customerId; }
	    public String getName() { return name; }
	    public String getEmail() { return email; }
	    public String getPassword() { return password; }

	    public void displayCustomer() {
	        System.out.println("Customer ID: " + customerId + ", Name: " + name + ", Email: " + email);
	    }
	}



