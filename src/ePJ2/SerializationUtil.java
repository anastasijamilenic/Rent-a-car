/**
 * Provides utility methods for serializing and deserializing Vehicle objects.
 */
package ePJ2;

import java.io.*;

import vehicles.Vehicle;

/**
 * A utility class for serializing and deserializing Vehicle objects.
 */
public class SerializationUtil {

    /**
     * Serializes a Vehicle object to a file.
     * @param vehicle the Vehicle object to serialize
     * @param filePath the path to the file where the Vehicle object will be serialized
     */
    public static void serializeVehicle(Vehicle vehicle, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(vehicle);
            System.out.println("Vehicle serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes a Vehicle object from a file.
     * @param filePath the path to the file from which the Vehicle object will be deserialized
     * @return the deserialized Vehicle object, or null if deserialization fails
     */
    public static Vehicle deserializeVehicle(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (Vehicle) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
