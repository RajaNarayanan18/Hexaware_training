package test1;

public class Customer {
	
	    public String custID, fName, lName, email, phone, address;

	    public Customer() {}
	    public Customer(String custID, String fName, String lName, String email, String phone, String address) {
	        this.custID = custID;
	        this.fName = fName;
	        this.lName = lName;
	        this.email = email;
	        this.phone = phone;
	        this.address = address;
	    }
	    public void displayInfo() {
	        System.out.println("Customer: " + fName + " " + lName + ", ID: " + custID);
	    }
	}


