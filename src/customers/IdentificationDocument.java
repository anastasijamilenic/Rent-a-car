package customers;

import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;


public abstract class IdentificationDocument implements Serializable {
	
    
	private String ID;
	
	public IdentificationDocument() {
		
		this.ID = UUID.randomUUID().toString();
		
	}
	
	public String getID( ) {
		return this.ID;
	}
}