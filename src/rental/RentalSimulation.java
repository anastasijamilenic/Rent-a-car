/**
 * Simulates rental operations including movement tracking on a map and invoice generation.
 */
package rental;

import javax.swing.*;

import ePJ2.InvoiceGenerator;
import gui.MapaGUI;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.*;

/**
 * Represents a thread for simulating a rental operation.
 */
public class RentalSimulation extends Thread {
    private Rental rental;
    private MapaGUI mapaGUI;
    private static final Object lock = new Object(); // Global lock object
    private static InvoiceGenerator invoiceGenerator;
    private static int counter = 1; // Static counter for each instance of RentalSimulation
    private int rentalNumber; // Counter for each instance
    

    /**
     * Constructs a RentalSimulation instance with the specified rental and MapaGUI.
     * @param rental the rental to simulate
     * @param mapaGUI the MapaGUI instance for visualization
     */
    public RentalSimulation(Rental rental, MapaGUI mapaGUI) {
        this.rental = rental;
        this.mapaGUI = mapaGUI;
        this.invoiceGenerator = new InvoiceGenerator();
        this.rentalNumber = counter++;
    }

    /**
     * Runs the rental simulation.
     */
    @Override
    public void run() {
        try {
            // Simulation of movement on the map
            System.out.println("Rental simulation for vehicle " + rental.getVehicleId() + " started.");

            // Updating the map
            updateMap(rental);

            // Creating an invoice
            createInvoice(rental);

            System.out.println("Rental simulation for vehicle " + rental.getVehicleId() + " ended.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an invoice for the rental.
     * @param rental the rental for which to create an invoice
     */
    private void createInvoice(Rental rental) {
        // Generating an invoice using the InvoiceGenerator object
        invoiceGenerator.generateInvoice(rental);
    }

    /**
     * Updates the map according to the rental's movement.
     * @param rental the rental representing the vehicle's movement
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    
    public void updateBatteryLevels() {
        Thread batteryUpdater = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               
                    if (rental.getBatteryLevel() == 20) {
                        rental.setBatteryLevel(rental.getBatteryLevel() + 10);
                    } else {
                        rental.setBatteryLevel(rental.getBatteryLevel() - 5);
                    }
                }
            
        });
        batteryUpdater.start();
    }
    private void updateMap(Rental rental) throws InterruptedException {
        // Logic for updating the map

        int startX = (int) rental.getStartX();
        int startY = (int) rental.getStartY();
        int endX = (int) rental.getEndX();
        int endY = (int) rental.getEndY();

        int steps = Math.abs(endX - startX) + Math.abs(endY - startY); // Number of steps in a straight path
        long stepDuration = (long) (rental.getDuration() * 1000.0 / steps); // Duration of one step in milliseconds
        boolean isWideArea = false;
        

        updateBatteryLevels();
        // Horizontal movement
        for (int x = startX; x != endX; x += Integer.compare(endX, startX)) {
            mapaGUI.updatePosition(rental.getVehicleId(), x, startY, rental.getBatteryLevel());
        	
            if (mapaGUI.isWideArea(x, startY)) {
                isWideArea = true;
            }
            Thread.sleep(stepDuration);
        }

        // Vertical movement
        for (int y = startY; y != endY; y += Integer.compare(endY, startY)) {
            mapaGUI.updatePosition(rental.getVehicleId(), endX, y, rental.getBatteryLevel());
        	
            if (mapaGUI.isWideArea(endX, y)) {
                isWideArea = true;
            }
            Thread.sleep(stepDuration);
        }

        // Final position
        mapaGUI.updatePosition(rental.getVehicleId(), endX, endY, rental.getBatteryLevel());
       
        if (mapaGUI.isWideArea(endX, endY)) {
            isWideArea = true;
        }

        rental.setWideArea(isWideArea);

        System.out.println("Map update for vehicle: " + rental.getVehicleId());
        System.out.println("Battery level: " + rental.getBatteryLevel() + "%");
    }

    
}




