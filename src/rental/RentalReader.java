/**
 * Reads rental data from a CSV file and creates Rental objects.
 */
package rental;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import customers.Customer;
import vehicles.VehicleReader;

/**
 * A utility class for reading rental data from a CSV file and creating Rental objects.
 */
public class RentalReader {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("d.M.yyyy H:mm");

    /**
     * Reads rental data from a CSV file and creates Rental objects.
     * @param csvFile the path to the CSV file containing rental data
     * @return a list of Rental objects created from the data in the CSV file
     */
    public static List<Rental> readRentals(String csvFile) {
        List<Rental> rentals = new ArrayList<>();
        Set<String> vehicleIds = VehicleReader.readVehicleIds("files" + File.separator +"PrevoznaSredstva.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Skip the header line
            while ((line = br.readLine()) != null) {
                String result = line.replace("\"", "");

                String[] data = result.split(",");
                if (data.length != 10) {
                    System.out.println("Error: Netacan broj podataka: " + line);
                    continue;
                }
                
                

                LocalDateTime dateTime = LocalDateTime.parse(data[0], DATE_TIME_FORMATTER);
                String user = data[1];
                String vehicleId = data[2];
                double startX = Double.parseDouble(data[3]);
                double startY = Double.parseDouble(data[4]);
                double endX = Double.parseDouble(data[5]);
                double endY = Double.parseDouble(data[6]);
                long duration = Long.parseLong(data[7]);
                boolean hasFault = data[8].equalsIgnoreCase("da");
                boolean hasPromotion = data[9].equalsIgnoreCase("da");
                
             // Provjera opsega
                if (startX < 0 || startX > 19 || startY < 0 || startY > 19 || endX < 0 || endX > 19 || endY < 0 || endY > 19) {
                    System.out.println("Error: Coordinates out of range in line: " + line);
                    continue;
                }
                

                Customer customer = new Customer(user);
                if(customer.getIdentificationDocument()!= null) {
                if (vehicleIds.contains(vehicleId)) {
                Rental rental = new Rental(dateTime, user, vehicleId, startX, startY, endX, endY, duration, hasFault, hasPromotion);
                if (rental.hasFault()) {
                    MalfunctionReason[] reasons = MalfunctionReason.values();
                    int index = new Random().nextInt(reasons.length);
                    rental.setMalfunction(new Malfunction(reasons[index], dateTime));
                }
                rentals.add(rental);
            }
                else {
                    System.out.println("Vehicle ID " + vehicleId + " does not exist in the vehicle list.");
                }
                }
                else System.out.println("Korisnik nema identifikacione dokumente");
        } }catch (IOException e) {
            e.printStackTrace();
        }
        return rentals;
    }
}
