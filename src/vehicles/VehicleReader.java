/**
 * Provides utility methods for reading vehicle data from CSV files.
 */
package vehicles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A utility class for reading vehicle data from CSV files.
 */
public class VehicleReader {

    /**
     * Reads vehicle prices from a CSV file.
     * @param filePath the path to the CSV file containing vehicle prices
     * @return a map of vehicle IDs to their corresponding prices
     */
    public static Map<String, Double> readVehiclePrices(String filePath) {
        Map<String, Double> vehiclePrices = new HashMap<>();
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String id = data[0];
                double price = Double.parseDouble(data[4].isEmpty() ? "0" : data[4]);
                vehiclePrices.put(id, price);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehiclePrices;
    }

    /**
     * Reads vehicles from a CSV file.
     * @param filePath the path to the CSV file containing vehicle data
     * @return a list of Vehicle objects read from the CSV file
     */
    public static List<Vehicle> readVehicles(String filePath) {
        List<Vehicle> vehicles = new ArrayList<>();
        String line;
        String csvSplitBy = ",";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d.M.yyyy.");
        Set<String> vehicleIds = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                
                // Provjera da li linija ima dovoljno podataka
                if (data.length < 9) {
                    System.out.println("Error: Insufficient data in line: " + line);
                    continue; // Preskače ovu liniju i nastavlja sa čitanjem sljedeće linije
                }
                
                String id = data[0];
                String manufacturer = data[1];
                String model = data[2];
                LocalDate purchaseDate = data[3].isEmpty() ? null : LocalDate.parse(data[3], dateFormatter);
                double price = data[4].isEmpty() ? 0 : Double.parseDouble(data[4]);
                double range = data[5].isEmpty() ? 0 : Double.parseDouble(data[5]);
                double maxSpeed = data[6].isEmpty() ? 0 : Double.parseDouble(data[6]);
                String description = data[7];
                String type = data[8];
                
             // Provjera koordinata
                if (range < 0 || maxSpeed < 0  || price < 0) {
                    System.out.println("Error: Invalid coordinates in line: " + line);
                    continue; // Preskače ovu liniju i nastavlja sa čitanjem sljedeće linije
                }
                // Provjera duplikata identifikatora vozila
                if (!vehicleIds.contains(id)) {
                    vehicleIds.add(id); // Dodavanje identifikatora u set
                } else {
                    System.out.println("Duplicate vehicle ID found: " + id);
                    continue; // Preskakanje ovog vozila ako je identifikator duplikat
                }

                Vehicle vehicle;
                if (manufacturer.startsWith("A")) {
                    vehicle = new Car(id, manufacturer, model, price, purchaseDate, description);
                } else if (manufacturer.startsWith("B")) {
                    vehicle = new ElectricBike(id, manufacturer, model, price, (int) range);
                } else if (manufacturer.startsWith("T")) {
                    vehicle = new Scooter(id, manufacturer, model,  price, maxSpeed);
                } else {
                    throw new IllegalArgumentException("Unknown manufacturer: " + manufacturer);
                }

                vehicles.add(vehicle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return vehicles;
    }
    
    /**
     * Reads vehicle IDs from a CSV file.
     * @param filePath the path to the CSV file containing vehicle data
     * @return a set of vehicle IDs read from the CSV file
     */
    public static Set<String> readVehicleIds(String filePath) {
        Set<String> vehicleIds = new HashSet<>();
        String line;
        String csvSplitBy = ",";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String id = data[0];
                vehicleIds.add(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicleIds;
    }
}
