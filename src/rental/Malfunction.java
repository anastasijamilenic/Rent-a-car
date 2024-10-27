package rental;

import java.time.LocalDateTime;

/**
 * Represents a malfunction that occurred with a vehicle.
 */
public class Malfunction {

    /** The description of the malfunction. */
    private MalfunctionReason description;

    /** The date and time when the malfunction occurred. */
    private LocalDateTime dateTime;

    /**
     * Constructs a new Malfunction object with the specified description and date/time.
     * @param description The description of the malfunction.
     * @param dateTime The date and time when the malfunction occurred.
     */
    public Malfunction(MalfunctionReason description, LocalDateTime dateTime) {
        this.description = description;
        this.dateTime = dateTime;
    }

    /**
     * Gets the description of the malfunction.
     * @return The description of the malfunction.
     */
    public MalfunctionReason getDescription() {
        return description;
    }

    /**
     * Gets the date and time when the malfunction occurred.
     * @return The date and time when the malfunction occurred.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Sets the description of the malfunction.
     * @param description The new description of the malfunction.
     */
    public void setDescription(MalfunctionReason description) {
        this.description = description;
    }

    /**
     * Sets the date and time when the malfunction occurred.
     * @param dateTime The new date and time of the malfunction.
     */
    public void setDateTime(LocalDateTime dateTime) {
         this.dateTime = dateTime;
    }
}
