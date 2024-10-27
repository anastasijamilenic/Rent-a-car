package customers;

public class IdCard extends IdentificationDocument {
    

    public IdCard(String idCardNumber) {
        super();
       
    }


    @Override
    public String toString() {
        return "Id Card Number: " + getID();
    }
}
