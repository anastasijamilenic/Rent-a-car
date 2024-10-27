package ePJ2;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.io.FileInputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import gui.SummaryReportGUI;
import rental.Rental;
import vehicles.Vehicle;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Utility class for generating invoices, summary reports, and daily reports.
 * This class provides methods for generating invoices, summary reports, and daily reports based on rental data,
 * as well as methods for finding vehicles with the most loss due to faults and deserializing vehicle objects from files.
 */
public class InvoiceGenerator {

    /**
     * Path to the directory where invoices will be saved.
     */
    private static final String INVOICE_PATH = Config.getProperty("INVOICE_PATH");

    /**
     * Generates an invoice for the provided rental and saves it to a text file.
     * @param rental The rental for which the invoice is generated.
     */
    public void generateInvoice(Rental rental) {
        String invoice = calculateTotalPrice(rental);

        if (INVOICE_PATH == null) {
            throw new IllegalArgumentException("Invoice path not set in properties file.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String formattedDateTime = rental.getDateTime().format(formatter);

        // Creating the full file path
        String filePath = Paths.get(INVOICE_PATH, "invoice_" + rental.getVehicleId() + "_" + formattedDateTime + ".txt").toString();

        // Writing the invoice to a text file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(invoice);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calculates the total price for the provided rental and returns the invoice details as a string.
     * @param rental The rental for which to calculate the total price and generate the invoice.
     * @return The invoice details as a string.
     */
    private String calculateTotalPrice(Rental rental) {
        double unitPrice = Config.getDoubleProperty(rental.getVehicleType() + "_UNIT_PRICE");
        double distanceFactor = rental.isWideArea() ? Config.getDoubleProperty("DISTANCE_WIDE") : Config.getDoubleProperty("DISTANCE_NARROW");
        double duration = rental.getDuration();
        double distancePrice = unitPrice * distanceFactor * duration;

        double discountPercentage = Config.getDoubleProperty("DISCOUNT_PERCENTAGE");
        double discount = rental.getRentalNumber() % 10 == 0 ?  (distancePrice * discountPercentage) : 0;

        double promoPercentage = Config.getDoubleProperty("DISCOUNT_PROMO_PERCENTAGE");
        double promoDiscount = rental.hasPromotion() ? (distancePrice * promoPercentage) : 0;

        double totalPrice = distancePrice - discount - promoDiscount;

        if (rental.hasFault()) {
            totalPrice = 0;
        }

        StringBuilder invoiceDetails = new StringBuilder();
        invoiceDetails.append("Vozilo ID: ").append(rental.getVehicleId()).append("\n");
        invoiceDetails.append("Početna tačka: (").append(rental.getStartX()).append(", ").append(rental.getStartY()).append(")\n");
        invoiceDetails.append("Krajnja tačka: (").append(rental.getEndX()).append(", ").append(rental.getEndY()).append(")\n");
        invoiceDetails.append("Trajanje: ").append(rental.getDuration()).append(" sekundi\n");
        invoiceDetails.append("Cena po osnovu udaljenosti: ").append(distancePrice).append(" EUR\n");
        invoiceDetails.append("Rental count: ").append(rental.getRentalNumber()).append(" EUR\n");

        invoiceDetails.append("Popust: ").append(discount).append(" EUR\n");

        if (promoDiscount > 0) {
            invoiceDetails.append("Promocijski popust: ").append(promoDiscount).append(" EUR\n");
        }

        if (rental.hasFault()) {
            invoiceDetails.append("Zbog kvara ukupna cena je 0 EUR\n");
        } else {
            invoiceDetails.append("Ukupno za plaćanje: ").append(totalPrice).append(" EUR\n");
        }

        return invoiceDetails.toString();
    }

    
    /**
     * Generates a summary report based on the provided rentals and vehicle prices,
     * and displays it using a Swing GUI.
     * @param rentals The list of rentals for which to generate the summary report.
     * @param vehiclePrices A map containing vehicle IDs as keys and their corresponding prices as values.
     */
    public static void generateSummaryReport(List<Rental> rentals, Map<String, Double> vehiclePrices) {
        double totalIncome = 0;
        double totalDiscount = 0;
        double totalPromotion = 0;
        double totalUrbanAreaIncome = 0;
        double totalWideAreaIncome = 0;
        double totalRepairCosts = 0;

        for (Rental rental : rentals) {
            double unitPrice = Config.getDoubleProperty(rental.getVehicleType() + "_UNIT_PRICE");
            double distanceFactor = rental.isWideArea() ? Config.getDoubleProperty("DISTANCE_WIDE") : Config.getDoubleProperty("DISTANCE_NARROW");
            double duration = rental.getDuration();
            double distancePrice = unitPrice * distanceFactor * duration;
            
           

            double discountPercentage = Config.getDoubleProperty("DISCOUNT_PERCENTAGE");
            double discount = rental.getRentalNumber() % 10 == 0 ?  (distancePrice * discountPercentage) : 0;

            double promoPercentage = Config.getDoubleProperty("DISCOUNT_PROMO_PERCENTAGE");
            double promoDiscount = rental.hasPromotion() ?  (distancePrice * promoPercentage) : 0;

            double totalPrice = distancePrice - discount - promoDiscount;
            double repairCoefficient = getRepairCoefficient(rental.getVehicleType());
            double purchasePrice = vehiclePrices.getOrDefault(rental.getVehicleId(), 0.0);

            if (rental.hasFault()) {
                totalPrice = 0;
                
                totalRepairCosts += repairCoefficient * purchasePrice;
            }

            totalIncome += totalPrice;
            totalDiscount += discount;
            totalPromotion += promoDiscount;
            if (rental.isWideArea()) {
                totalWideAreaIncome += totalPrice;
            } else {
                totalUrbanAreaIncome += totalPrice;
            }

           
            
            
        }

        double totalMaintenance = totalIncome * 0.2;
        double companyCosts = totalIncome * 0.2;
        double totalTax = (totalIncome - totalMaintenance - totalRepairCosts - companyCosts) * 0.1;

        System.out.println("Summary Report:");
        System.out.println("1. Ukupan prihod: " + totalIncome + " EUR");
        System.out.println("2. Ukupan popust: " + totalDiscount + " EUR");
        System.out.println("3. Ukupno promocije: " + totalPromotion + " EUR");
        System.out.println("4. Ukupan iznos svih vožnji u užem dijelu grada: " + totalUrbanAreaIncome + " EUR");
        System.out.println("5. Ukupan iznos svih vožnji u širem dijelu grada: " + totalWideAreaIncome + " EUR");
        System.out.println("6. Ukupan iznos za održavanje: " + totalMaintenance + " EUR");
        System.out.println("7. Ukupan iznos za popravke kvarova: " + totalRepairCosts + " EUR");
        System.out.println("8. Ukupni troškovi kompanije: " + companyCosts + " EUR");
        System.out.println("9. Ukupan porez: " + totalTax + " EUR");
        
        
        String summaryText = "Summary Report:\n"
                + "1. Ukupan prihod: " + totalIncome + " EUR\n"
                + "2. Ukupan popust: " + totalDiscount + " EUR\n"
                + "3. Ukupno promocije: " + totalPromotion + " EUR\n"
                + "4. Ukupan iznos svih vožnji u užem dijelu grada: " + totalUrbanAreaIncome + " EUR\n"
                + "5. Ukupan iznos svih vožnji u širem dijelu grada: " + totalWideAreaIncome + " EUR\n"
                + "6. Ukupan iznos za održavanje: " + totalMaintenance + " EUR\n"
                + "7. Ukupan iznos za popravke kvarova: " + totalRepairCosts + " EUR\n"
                + "8. Ukupni troškovi kompanije: " + companyCosts + " EUR\n"
                + "9. Ukupan porez: " + totalTax + " EUR\n";

        // Prikazivanje sumarnog izvještaja na ekranu
        new SummaryReportGUI(summaryText);
       
    }

    private static double getRepairCoefficient(String vehicleType) {
        switch (vehicleType.toLowerCase()) {
            case "car":
                return 0.07;
            case "bike":
                return 0.04;
            case "scooter":
                return 0.02;
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }
    
    /**
     * Generates daily reports based on the provided rentals and vehicle prices,
     * and displays them using a Swing GUI.
     * @param rentals The list of rentals for which to generate daily reports.
     * @param vehiclePrices A map containing vehicle IDs as keys and their corresponding prices as values.
     */

    public static void generateDailyReports(List<Rental> rentals, Map<String, Double> vehiclePrices) {
        // Kreiranje modela tabele
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Datum");
        model.addColumn("Ukupan prihod (EUR)");
        model.addColumn("Ukupan popust (EUR)");
        model.addColumn("Ukupno promocije (EUR)");
        model.addColumn("Prihod uži dio grada");
        model.addColumn("Prihod širi dio grada)");
        model.addColumn("Održavanje (EUR)");
        model.addColumn("Popravke kvarova (EUR)");

        // Grupisanje iznajmljivanja po datumima
        Map<LocalDate, List<Rental>> rentalsByDate = rentals.stream().collect(Collectors.groupingBy(r -> r.getDateTime().toLocalDate()));

        // Dodavanje redova u model tabele za svaki datum
        for (Map.Entry<LocalDate, List<Rental>> entry : rentalsByDate.entrySet()) {
            LocalDate date = entry.getKey();
            List<Rental> rentalsForDate = entry.getValue();

            double totalIncome = 0;
            double totalDiscount = 0;
            double totalPromotion = 0;
            double totalUrbanAreaIncome = 0;
            double totalWideAreaIncome = 0;
            double totalRepairCosts = 0;

            // Računanje sume za svaki datum
            for (Rental rental : rentalsForDate) {
                double unitPrice = Config.getDoubleProperty(rental.getVehicleType() + "_UNIT_PRICE");
                double distanceFactor = rental.isWideArea() ? Config.getDoubleProperty("DISTANCE_WIDE") : Config.getDoubleProperty("DISTANCE_NARROW");
                double duration = rental.getDuration();
                double distancePrice = unitPrice * distanceFactor * duration;

                double discountPercentage = Config.getDoubleProperty("DISCOUNT_PERCENTAGE");
                double discount = rental.getRentalNumber() % 10 == 0 ? (distancePrice * discountPercentage) : 0;

                double promoPercentage = Config.getDoubleProperty("DISCOUNT_PROMO_PERCENTAGE");
                double promoDiscount = rental.hasPromotion() ? (distancePrice * promoPercentage) : 0;

                double totalPrice = distancePrice - discount - promoDiscount;
                
                double loss = 0;

                if (rental.hasFault()) {
                	double repairCoefficient = getRepairCoefficient(rental.getVehicleType());
                    double purchasePrice = vehiclePrices.getOrDefault(rental.getVehicleId(), 0.0);
                	
                    totalRepairCosts += repairCoefficient * purchasePrice;
                    loss = (repairCoefficient * purchasePrice) + totalPrice;
                    rental.setLoss(loss);
                    totalPrice = 0;
                }

                totalIncome += totalPrice;
                totalDiscount += discount;
                totalPromotion += promoDiscount;
                if (rental.isWideArea()) {
                    totalWideAreaIncome += totalPrice;
                } else {
                    totalUrbanAreaIncome += totalPrice;
                }

               
            }

            double totalMaintenance = totalIncome * 0.2;

            // Dodavanje reda u model tabele sa sumom podataka za trenutni datum
            model.addRow(new Object[]{
                    date,
                    totalIncome,
                    totalDiscount,
                    totalPromotion,
                    totalUrbanAreaIncome,
                    totalWideAreaIncome,
                    totalMaintenance,
                    totalRepairCosts
            });
        }

        // Kreiranje JTable sa zadatim modelom
        JTable table = new JTable(model);

        // Dodavanje tabele u JScrollPane radi omogućavanja pomeranja ako je tabela velika
        JScrollPane scrollPane = new JScrollPane(table);

        // Kreiranje i prikazivanje GUI-ja sa tabelom
        JFrame frame = new JFrame("Dnevni izvjestaji");
        frame.add(scrollPane);
        frame.setSize(800, 400); // Postavljanje veličine prozora
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Finds the rental with the most loss due to faults for each vehicle type,
     * serializes the corresponding vehicle object, and saves it to a file.
     * @param rentals The list of rentals to search for faults.
     * @param vehicles The list of vehicles corresponding to the rentals.
     */
    public static void findMostLossForEachType(List<Rental> rentals, List<Vehicle> vehicles) {
        Map<String, Rental> maxLossRentals = new HashMap<>();
        Map<String, Double> maxLossValues = new HashMap<>();

        for (Rental rental : rentals) {
            if (rental.hasFault()) {
                String type = rental.getVehicleType();
                double loss = rental.getLoss();

                if (!maxLossValues.containsKey(type) || loss > maxLossValues.get(type)) {
                    maxLossValues.put(type, loss);
                    maxLossRentals.put(type, rental);
                }
            }
        }

        for (String type : maxLossRentals.keySet()) {
            Rental rental = maxLossRentals.get(type);
            String vehicleId = rental.getVehicleId();

            // Find the corresponding vehicle
            Vehicle vehicle = vehicles.stream()
                    .filter(v -> v.getID().equals(vehicleId))
                    .findFirst()
                    .orElse(null);

            if (vehicle != null) {
                System.out.println("Vehicle type: " + type);
                System.out.println("Vehicle ID: " + vehicleId);
                System.out.println("Total Loss: " + rental.getLoss());
                System.out.println();

                // Serialize the vehicle object
                String filePath = "losses/"  + vehicleId + ".bin";
                SerializationUtil.serializeVehicle(vehicle, filePath);
                
            }
        }
    
}
    
    /**
     * Deserializes vehicle objects from files in the specified folder.
     * @param folderPath The path to the folder containing the serialized vehicle files.
     * @return A list of deserialized vehicle objects.
     */

    public static ArrayList<Vehicle> deserializeFiles(String folderPath) {
        ArrayList<Vehicle> objects = new ArrayList<>();
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    Vehicle obj = (Vehicle) ois.readObject();
                    objects.add(obj);
                    
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return objects;
    }

    /**
     * Clears the invoice directory by deleting all files inside it.
     */
    public static void clearInvoiceDirectory() {
        File directory = new File(INVOICE_PATH);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        file.delete();
                    }
                }
            }
        }
    }
}
