package customers;

public class Passport extends IdentificationDocument {
   
    public Passport(String passportNumber) {
        super();
        
    }

   

    @Override
    public String toString() {
        return "Passport number: "  + " (ID: " + getID() + ")";
    }
}
