package com.insurance_ms.entity;

public class Client {
	
	    private int clientId;
	    private String clientName;
	    private String contactInfo;

	    public Client() {}

	    public Client(int clientId, String clientName, String contactInfo) {
	        this.clientId = clientId;
	        this.clientName = clientName;
	        this.contactInfo = contactInfo;
	    }

	    public int getClientId() { return clientId; }
	    public void setClientId(int clientId) { this.clientId = clientId; }

	    public String getClientName() { return clientName; }
	    public void setClientName(String clientName) { this.clientName = clientName; }

	    public String getContactInfo() { return contactInfo; }
	    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

	    @Override
	    public String toString() {
	        return "Client [ID=" + clientId + ", Name=" + clientName + ", Contact=" + contactInfo + "]";
	    }
	}


