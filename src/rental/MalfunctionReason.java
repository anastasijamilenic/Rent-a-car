package rental;

/**
 * Enumerates the reasons for a vehicle malfunction.
 */
public enum MalfunctionReason {
    /** The engine overheated. */
    ENGINE_OVERHEATED,

    /** The vehicle has a flat tire. */
    FLAT_TIRE,

    /** The vehicle experienced a battery failure. */
    BATTERY_FAILURE,

    /** The vehicle experienced a brake failure. */
    BRAKE_FAILURE,

    /** The vehicle experienced a transmission issue. */
    TRANSMISSION_ISSUE,

    /** The vehicle experienced an electrical failure. */
    ELECTRICAL_FAILURE
}
