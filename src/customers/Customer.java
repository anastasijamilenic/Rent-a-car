package customers;

import java.util.Random;
import java.util.UUID;

public class Customer {
    private String userId;
    private int drivingLicenseNumber;
    private IdentificationDocument identificationDocument;

   
    
    public Customer(String id) {
    	this.userId = id;
    	this.drivingLicenseNumber = new Random().nextInt(100);
    	int random = new Random().nextInt(2);
    	if(random == 0)
    		this.identificationDocument = new Passport(UUID.randomUUID().toString());
    	else
    		this.identificationDocument = new IdCard(UUID.randomUUID().toString());
    }

   

    public String getIdentificationDocument() {
        return "Identifikacioni dokument: " + identificationDocument.toString() + " " + userId + "Vozacka dozvola: " + drivingLicenseNumber;
    }
}



