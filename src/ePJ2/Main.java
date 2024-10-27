package ePJ2;

import java.awt.GridLayout;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import gui.FaultReportGUI;
import gui.MapaGUI;
import gui.VehicleGUI;
import rental.Rental;
import rental.RentalReader;
import rental.RentalSimulation;
import vehicles.Vehicle;
import vehicles.VehicleReader;

public class Main {
	/**
     * The entry point for running the rental simulation.
     * @param args the command line arguments
     */


   

    public static void main(String[] args) {
        MapaGUI mapaGUI = new MapaGUI();
        
        int counter = 1;
        
        InvoiceGenerator.clearInvoiceDirectory();


      
        List<Rental> rentals = RentalReader.readRentals("files" + File.separator +"Iznajmljivanja.csv");

        // Grupisanje iznajmljivanja po datumu i vremenu
        Map<LocalDateTime, List<Rental>> groupedRentals = rentals.stream()
                .collect(Collectors.groupingBy(Rental::getDateTime));
        
        Map<LocalDateTime, List<Rental>> sortedGroupedRentals = new TreeMap<>(groupedRentals);
        
        for (Map.Entry<LocalDateTime, List<Rental>> entry : sortedGroupedRentals.entrySet()) {
            LocalDateTime dateTime = entry.getKey();
            List<Rental> rentalsForDateTime = entry.getValue();

            System.out.println("Datum i vrijeme: " + dateTime);
            for (Rental rental : rentalsForDateTime) {
                System.out.println(" - Vozilo ID: " + rental.getVehicleId() + ", Start: (" + rental.getStartX() + ", " + rental.getStartY() + "), End: (" + rental.getEndX() + ", " + rental.getEndY() + "), Trajanje: " + rental.getDuration());
            }
        }

        // Prikazivanje GUI-ja
        JFrame frame = new JFrame("Mapa GUI");
        frame.add(mapaGUI);
        frame.setSize(650, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

      
        
        Map<LocalDateTime, Set<String>> processedVehicles = new HashMap<>();

     // Pokretanje simulacija za svaki datum i vrijeme
     for (Map.Entry<LocalDateTime, List<Rental>> entry : sortedGroupedRentals.entrySet()) {
         LocalDateTime dateTime = entry.getKey();
         List<Rental> rentalsForDateTime = entry.getValue();
         
         // Provera i dodavanje seta za trenutni datum i vreme ako već ne postoji
         processedVehicles.putIfAbsent(dateTime, new HashSet<>());
         
         // Pokretanje simulacija za iznajmljivanja za određeni datum i vrijeme
         List<RentalSimulation> simulations = new ArrayList<>();
         for (Rental rental : rentalsForDateTime) {
             String vehicleId = rental.getVehicleId();
             rental.setRentalNumber(counter++);
             
             
             
             // Provera da li je vozilo već obrađeno za trenutni datum i vreme
             if (!processedVehicles.get(dateTime).contains(vehicleId)) {
            	 
            	// rental.setRentalNumber(counter++);
                 // Ako vozilo nije obrađeno, pokreni simulaciju
                 RentalSimulation simulation = new RentalSimulation(rental, mapaGUI);
                 simulation.start();
                 simulations.add(simulation);
                 
                 
                 // Dodavanje vozila u set obrađenih vozila
                 processedVehicles.get(dateTime).add(vehicleId);
                
             }
         }

         // Čekanje da se sve simulacije za određeno vrijeme završe prije pauze
         for (RentalSimulation simulation : simulations) {
             try {
                 simulation.join(); // Čekanje da se završi svaka simulacija
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }

         // Očišćenje seta obrađenih vozila za trenutni datum i vreme
         processedVehicles.get(dateTime).clear();
     

        

            // Pauza od 5 sekundi nakon završetka simulacija za određeni datum i vrijeme
            try {
            	
            	System.out.println("Pauza");
                Thread.sleep(5000);
                mapaGUI.removeOldVehicles(); // Uklanjanje starih vozila sa mape
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
  

     
     FaultReportGUI.generateFaultReport(rentals);
     
     String vehicleFilePath = "files" + File.separator + "PrevoznaSredstva.csv";
     String rentalFilePath = "files" + File.separator +"Iznajmljivanja.csv";

     Map<String, Double> vehiclePrices = VehicleReader.readVehiclePrices(vehicleFilePath);
     InvoiceGenerator.generateSummaryReport(rentals, vehiclePrices);
     InvoiceGenerator.generateDailyReports(rentals, vehiclePrices);
     
     List<Vehicle> vehicles = VehicleReader.readVehicles(vehicleFilePath);
     InvoiceGenerator.findMostLossForEachType(rentals, vehicles);
     
     SwingUtilities.invokeLater(() -> new VehicleGUI(vehicles));
     JFrame frame2 = new JFrame("Prikazivanje serijalizovanih podataka");
     
     SwingUtilities.invokeLater(() -> {
         
         frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         JPanel panel = new JPanel(new GridLayout(0, 1));

         
         ArrayList<Vehicle> objects = InvoiceGenerator.deserializeFiles("losses" + File.separator);

         // Prikazivanje podataka na ekranu
         for (Vehicle obj : objects) {
             JLabel label = new JLabel(obj.toString());
             panel.add(label);
         }

         frame2.add(new JScrollPane(panel));
         frame2.pack();
         frame2.setVisible(true);
     });
    }
}
