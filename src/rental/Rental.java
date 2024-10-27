/**
 * Represents a rental transaction of a vehicle.
 * Each rental has information about the date and time of the rental, user details,
 * vehicle ID, starting and ending coordinates, duration, fault status, promotion status,
 * battery level, wide area status, loss, and any associated malfunction.
 */
package rental;

import java.time.LocalDateTime;
import java.util.Random;

public class Rental implements Comparable<Rental> {
    private int rentalCount = 1;

    private LocalDateTime dateTime;
    private String user;
    private String vehicleId;
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private long duration;
    private boolean fault;
    private boolean promotion;
    private int batteryLevel;
    private boolean wideArea;
    private double loss;
    private Malfunction malfunction = null;

    /**
     * Constructs a Rental object with the given parameters.
     * @param dateTime the date and time of the rental
     * @param user the user who rented the vehicle
     * @param vehicleId the ID of the rented vehicle
     * @param startX the starting X-coordinate of the rental
     * @param startY the starting Y-coordinate of the rental
     * @param endX the ending X-coordinate of the rental
     * @param endY the ending Y-coordinate of the rental
     * @param duration the duration of the rental
     * @param hasFault indicates if the rental had a fault
     * @param hasPromotion indicates if the rental had a promotion
     */
    public Rental(LocalDateTime dateTime, String user, String vehicleId, double startX, double startY,
                  double endX, double endY, long duration, boolean hasFault, boolean hasPromotion) {
        this.dateTime = dateTime;
        this.user = user;
        this.vehicleId = vehicleId;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.duration = duration;
        this.fault = hasFault;
        this.promotion = hasPromotion;
        this.batteryLevel = new Random().nextInt(51) + 50;
    }

    // Getters and setters

    /**
     * Retrieves the date and time of the rental.
     * @return the date and time of the rental
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Retrieves the user who rented the vehicle.
     * @return the user who rented the vehicle
     */
    public String getUser() {
        return user;
    }

    /**
     * Retrieves the ID of the rented vehicle.
     * @return the ID of the rented vehicle
     */
    public String getVehicleId() {
        return vehicleId;
    }

    /**
     * Retrieves the starting X-coordinate of the rental.
     * @return the starting X-coordinate of the rental
     */
    public double getStartX() {
        return startX;
    }

    /**
     * Retrieves the starting Y-coordinate of the rental.
     * @return the starting Y-coordinate of the rental
     */
    public double getStartY() {
        return startY;
    }

    /**
     * Retrieves the ending X-coordinate of the rental.
     * @return the ending X-coordinate of the rental
     */
    public double getEndX() {
        return endX;
    }

    /**
     * Retrieves the ending Y-coordinate of the rental.
     * @return the ending Y-coordinate of the rental
     */
    public double getEndY() {
        return endY;
    }

    /**
     * Retrieves the duration of the rental.
     * @return the duration of the rental
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Checks if the rental had a fault.
     * @return true if the rental had a fault, false otherwise
     */
    public boolean hasFault() {
        return fault;
    }

    /**
     * Checks if the rental had a promotion.
     * @return true if the rental had a promotion, false otherwise
     */
    public boolean hasPromotion() {
        return promotion;
    }
    
    /**
     * Retrieves the battery level of the vehicle at the start of the rental.
     * @return the battery level of the vehicle
     */
    public int getBatteryLevel() {
        return batteryLevel;
    }

    // Other getters and setters omitted for brevity

    /**
     * Retrieves the type of vehicle rented based on the vehicle ID.
     * @return the type of vehicle rented (SCOOTER, BIKE, CAR, or UNKNOWN)
     */
    public String getVehicleType() {
        String id = this.getVehicleId();
        char firstChar = id.charAt(0);
        switch (firstChar) {
            case 'T':
                return "SCOOTER";
            case 'B':
                return "BIKE";
            case 'A':
                return "CAR";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * Compares this rental with another based on their date and time.
     * @param other the other rental to compare to
     * @return a negative integer, zero, or a positive integer as this rental is before, equal to, or after the other rental
     */
    @Override
    public int compareTo(Rental other) {
        return this.dateTime.compareTo(other.dateTime);
    }

    /**
     * Retrieves the rental number.
     * @return the rental number
     */
    public int getRentalNumber() {
        return rentalCount;
    }

    /**
     * Sets the malfunction associated with this rental.
     * @param malfunction the malfunction associated with this rental
     */
    public void setMalfunction(Malfunction malfunction) {
        this.malfunction = malfunction;
    }
    
    /**
     * Retrieves the malfunction associated with this rental.
     * @return the malfunction associated with this rental
     */
    public Malfunction getMalfunction() {
        return this.malfunction;
    }

    /**
     * Sets the rental number.
     * @param number the rental number to set
     */
    public void setRentalNumber(int number) {
        this.rentalCount = number;
    }

    /**
     * Sets the wide area status of this rental.
     * @param value the wide area status to set
     */
    public void setWideArea(boolean value) {
        this.wideArea = value;
    }

    /**
     * Checks if this rental is in a wide area.
     * @return true if this rental is in a wide area, false otherwise
     */
    public boolean isWideArea() {
        return wideArea;
    }

    /**
     * Sets the loss associated with this rental.
     * @param loss the loss associated with this rental
     */
    public void setLoss(double loss) {
        this.loss = loss;
    }

    /**
     * Retrieves the loss associated with this rental.
     * @return the loss associated with this rental
     */
    public double getLoss() {
        return this.loss;
    }

    /**
     * Retrieves the description of the fault associated with this rental.
     * @return the description of the fault associated with this rental
     */
    public MalfunctionReason getFaultDescription() {
        return this.malfunction.getDescription();
    }

	public void setBatteryLevel(int level) {
		this.batteryLevel = level;
		
	}
}
