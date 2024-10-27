package vehicles;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;
import java.io.Serializable;


/**
 * Abstract class representing a vehicle.
 * This class defines common properties and behaviors for all vehicles.
 */
public abstract class Vehicle implements Serializable {
    
    /**
     * Unique identifier for the vehicle.
     */
    private String ID;
    
    /**
     * Manufacturer of the vehicle.
     */
    private String manufacturer;
    
    /**
     * Model of the vehicle.
     */
    private String model;
    
    /**
     * Price of the vehicle.
     */
    private double price;
    
    /**
     * Battery percentage of the vehicle.
     */
    private int batteryPercentage;
    
    private int x;
    
    private int y;
    
    /**
     * Constructs a new Vehicle object with the specified ID, manufacturer, model, and price.
     * @param ID The unique identifier of the vehicle.
     * @param manufacturer The manufacturer of the vehicle.
     * @param model The model of the vehicle.
     * @param price The price of the vehicle.
     */
    public Vehicle(String ID, String manufacturer, String model, double price) {
        this.ID = ID;
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
        this.batteryPercentage = new Random().nextInt(100) + 1;
    }

   
    
    /**
     * Charges the battery of the vehicle by the specified amount.
     * @param amount The amount by which to charge the battery.
     */
    public void chargeBattery(int amount) {
        this.batteryPercentage = Math.min(this.batteryPercentage + amount, 100);
    }

    /**
     * Gets the manufacturer of the vehicle.
     * @return The manufacturer of the vehicle.
     */
    public String getManufacturer() {
        return this.manufacturer;
    }
    
    /**
     * Gets the model of the vehicle.
     * @return The model of the vehicle.
     */
    public String getModel() {
        return this.model;
    }
    
    /**
     * Gets the price of the vehicle.
     * @return The price of the vehicle.
     */
    public double getPrice() {
        return this.price;
    }
    
    /**
     * Gets the battery percentage of the vehicle.
     * @return The battery percentage of the vehicle.
     */
    public double getBattery() {
        return this.batteryPercentage;
    }
    
    /**
     * Gets the battery percentage of the vehicle.
     * @return The battery percentage of the vehicle.
     */
    public int getBatteryPercentage() {
        return this.batteryPercentage;
    }

    /**
     * Gets the ID of the vehicle.
     * @return The ID of the vehicle.
     */
    public String getID() {
        return this.ID;
    }
    
    /**
     * Returns a string representation of the vehicle.
     * @return A string representation of the vehicle.
     */
    @Override
    public String toString() {
        return this.manufacturer + " " + this.model + " " + this.batteryPercentage + " " + this.price;
    }

    /**
     * Abstract method to get the description of the vehicle.
     * @return The description of the vehicle.
     */
    protected abstract String getDescription();

    /**
     * Abstract method to get the date of the vehicle.
     * @return The date of the vehicle.
     */
    public abstract LocalDate getDate();


    public int getX() {
    	return this.x;
    }
    
    public int getY() {
    	return this.y;
    }
    
    

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
}
