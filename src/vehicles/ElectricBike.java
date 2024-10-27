package vehicles;

import java.time.LocalDate;

/**
 * Represents an electric bike, which is a type of vehicle.
 * This class extends the Vehicle class and adds specific properties and behaviors for electric bikes.
 */
public class ElectricBike extends Vehicle {
    
    /**
     * Range of the electric bike.
     */
    private int range;

    /**
     * Constructs a new ElectricBike object with the specified ID, manufacturer, model, price, and range.
     * @param id The unique identifier of the electric bike.
     * @param manufacturer The manufacturer of the electric bike.
     * @param model The model of the electric bike.
     * @param price The price of the electric bike.
     * @param range The range of the electric bike.
     */
    public ElectricBike(String id, String manufacturer, String model, double price, int range) {
        super(id, manufacturer, model, price);
        this.range = range;
    }

    /**
     * Gets the range of the electric bike.
     * @return The range of the electric bike.
     */
    public int getRange() {
        return this.range;
    }
    
    /**
     * Returns a string representation of the electric bike.
     * @return A string representation of the electric bike.
     */
    @Override
    public String toString() {
        return super.toString() + " Domet: " + this.range + " " + " Bicikl";
    }

    /**
     * Gets the description of the electric bike.
     * Since this method is abstract in the superclass, it must be implemented here.
     * In this case, it returns a default description since electric bikes do not have a specific description.
     * @return The description of the electric bike.
     */
    @Override
    protected String getDescription() {
        return "Bicikl nema opis";
    }

    /**
     * Gets the date of the electric bike.
     * Since this method is abstract in the superclass, it must be implemented here.
     * In this case, it returns null since electric bikes do not have a purchase date.
     * @return The date of the electric bike.
     */
    @Override
	public LocalDate getDate() {
        return null;
    }
}
