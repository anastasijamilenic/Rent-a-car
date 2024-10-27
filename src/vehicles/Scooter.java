package vehicles;

import java.time.LocalDate;

/**
 * Represents a scooter, which is a type of vehicle.
 * This class extends the Vehicle class and adds specific properties and behaviors for scooters.
 */
public class Scooter extends Vehicle {
    
    /**
     * Maximum speed of the scooter.
     */
    private double maxSpeed;

    /**
     * Constructs a new Scooter object with the specified ID, manufacturer, model, price, and maximum speed.
     * @param id The unique identifier of the scooter.
     * @param manufacturer The manufacturer of the scooter.
     * @param model The model of the scooter.
     * @param price The price of the scooter.
     * @param maxSpeed The maximum speed of the scooter.
     */
    public Scooter(String id, String manufacturer, String model, double price,  double maxSpeed) {
        super(id, manufacturer, model, price);
        this.maxSpeed = maxSpeed;
    }

    /**
     * Gets the maximum speed of the scooter.
     * @return The maximum speed of the scooter.
     */
    public double getMaxSpeed() {
        return this.maxSpeed;
    }
    
    /**
     * Returns a string representation of the scooter.
     * @return A string representation of the scooter.
     */
    @Override
    public String toString() {
        return super.toString() +  " Maksimalna brzina " + this.maxSpeed + " " + " Trotinet";
    }

    /**
     * Gets the description of the scooter.
     * Since this method is abstract in the superclass, it must be implemented here.
     * In this case, it returns a default description since scooters do not have a specific description.
     * @return The description of the scooter.
     */
    @Override
    protected String getDescription() {
        return "Skuter nema opis";
    }

    /**
     * Gets the date of the scooter.
     * Since this method is abstract in the superclass, it must be implemented here.
     * In this case, it returns null since scooters do not have a purchase date.
     * @return The date of the scooter.
     */
    @Override
	public LocalDate getDate() {
        return null;
    }
}
