package vehicles;

import java.time.LocalDate;
import java.util.Random;

/**
 * Represents a car, which is a type of vehicle.
 * This class extends the Vehicle class and adds specific properties and behaviors for cars.
 */
public class Car extends Vehicle {
    
    /**
     * Number of passengers the car can accommodate.
     */
    private int numOfPassengers;
    
    /**
     * Date of purchase of the car.
     */
    private LocalDate dateOfPurchase;
    
    /**
     * Description of the car.
     */
    private String description;
    
    /**
     * Constructs a new Car object with the specified ID, manufacturer, model, price, purchase date, and description.
     * @param id The unique identifier of the car.
     * @param manufacturer The manufacturer of the car.
     * @param model The model of the car.
     * @param price The price of the car.
     * @param date The date of purchase of the car.
     * @param description The description of the car.
     */
    public Car(String id, String manufacturer, String model, double price,  LocalDate date, String description) {
        super(id, manufacturer, model, price);
        this.dateOfPurchase = date;
        this.description = description;
        this.numOfPassengers = generateNumOfPassengers();
    }

    /**
     * Generates a random number of passengers for the car.
     * @return A random number of passengers for the car.
     */
    public int generateNumOfPassengers() {
        return new Random().nextInt(5) + 1;
    }

    /**
     * Gets the date of purchase of the car.
     * @return The date of purchase of the car.
     */
    public LocalDate getDate() {
        return this.dateOfPurchase;
    }
    
    /**
     * Gets the description of the car.
     * @return The description of the car.
     */
    public String getDescription() {
        return this.description;
    }

    

    /**
     * Returns a string representation of the car.
     * @return A string representation of the car.
     */
    @Override
    public String toString() {
        return super.toString() + "Opis: " + this.description + " Datum nabavke:  " + this.dateOfPurchase + " " + "Automobil";
    }
}
