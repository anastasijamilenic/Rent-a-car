package gui;

import javax.swing.*;

import rental.Rental;

import java.util.Random;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import vehicles.VehicleInfo;

/**
 * Represents a graphical user interface for displaying a map with vehicle positions.
 */
public class MapaGUI extends JPanel {

    /** The size of the map. */
    private static final int MAP_SIZE = 20;

    /** Represents the grid of the map where each cell can be either 0 (white) or 1 (blue). */
    private int[][] polja;

    /** Stores the positions of vehicles on the map. */
    //private Map<String, Point> vehiclePositions;
    private Map<String, VehicleInfo> vehiclePositions;

    /**
     * Constructs a new MapaGUI with an initialized map grid and vehicle positions.
     */
    public MapaGUI() {
        this.polja = new int[MAP_SIZE][MAP_SIZE];
        this.vehiclePositions = new ConcurrentHashMap<>();

        // Initialize all cells of the map to white
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                polja[i][j] = 0;
            }
        }

        // Set blue cells in the center of the map
        int startI = 5;
        int startJ = 5;
        int endI = 14;
        int endJ = 14;
        for (int i = startI; i <= endI; i++) {
            for (int j = startJ; j <= endJ; j++) {
                polja[i][j] = 1;
            }
        }
    }

            /**
		     * Overrides the paintComponent method to paint the map and vehicle positions.
		     * @param g The Graphics context used for painting.
		     */
		   /* @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        int cellSize = 20; // Size of each cell on the map
		
		        // Draw the map grid
		        for (int i = 0; i < 20; i++) {
		            for (int j = 0; j < 20; j++) {
		                if (polja[i][j] == 0) {
		                    g.setColor(Color.WHITE);
		                } else {
		                    g.setColor(Color.BLUE);
		                }
		                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
		                g.setColor(Color.BLACK);
		                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
		            }
		        }
		
		        // Draw vehicles on the map
		       // synchronized (vehiclePositions) {
		            for (Map.Entry<String, Point> entry : vehiclePositions.entrySet()) {
		                String vehicleId = entry.getKey();
		                Point position = entry.getValue();
		
		                // Set the color of the vehicle
		                g.setColor(Color.RED); 
		                g.fillRect(position.x * cellSize, position.y * cellSize, cellSize, cellSize);
		                g.setColor(Color.BLACK);
		                g.drawString(vehicleId, position.x * cellSize + 5, position.y * cellSize + 15);
		            }
		        }
		   // }*/
		
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        int cellSize = 30; // Size of each cell on the map
		
		        // Draw the map grid
		        for (int i = 0; i < 20; i++) {
		            for (int j = 0; j < 20; j++) {
		                if (polja[i][j] == 0) {
		                    g.setColor(Color.WHITE);
		                } else {
		                    g.setColor(Color.BLUE);
		                }
		                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
		                g.setColor(Color.BLACK);
		                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
		            }
		        }
		
		        // Draw vehicles on the map
		        Map<Point, List<String>> vehiclesByPosition = new HashMap<>();
		        for (Map.Entry<String, VehicleInfo> entry : vehiclePositions.entrySet()) {
		            String vehicleId = entry.getKey();
		            VehicleInfo position = entry.getValue();
		
		            // Add the vehicle to the list of vehicles at this position
		            VehicleInfo vehicleInfo = vehiclePositions.get(vehicleId);
		            if (vehicleInfo != null) {
		                vehiclesByPosition.computeIfAbsent(vehicleInfo.getPosition(), k -> new ArrayList<>()).add(vehicleId);
		            }
		        }
		
		        // Draw vehicles at each position
		       /* for (Map.Entry<Point, List<String>> entry : vehiclesByPosition.entrySet()) {
		            Point position = entry.getKey();
		            List<String> vehicleIds = entry.getValue();
		
		            // Set the color of the vehicle
		            
		            g.setColor(Color.RED);
		            g.fillRect(position.x * cellSize, position.y * cellSize, cellSize, cellSize);
		            g.setColor(Color.BLACK);
		            
		            // Draw each vehicle ID
		            int yOffset = 15;
		            for (String vehicleId : vehicleIds) {
		                g.drawString(vehicleId, position.x * cellSize + 5, position.y * cellSize + yOffset);
		                yOffset += 15; // Increase yOffset for next vehicle
		            }
		        }
		    }*/for (Map.Entry<Point, List<String>> entry : vehiclesByPosition.entrySet()) {
		        Point position = entry.getKey();
		        List<String> vehicleIds = entry.getValue();
		        int yOffset = 15;
		        for (String vehicleId : vehicleIds) {
		            // Set the color based on the first letter of the vehicle ID
		            char firstLetter = vehicleId.charAt(0);
		            switch (firstLetter) {
		                case 'A':
		                    g.setColor(Color.RED);
		                    break;
		                case 'T':
		                    g.setColor(Color.GREEN);
		                    break;
		                case 'B':
		                    g.setColor(Color.YELLOW);
		                    break;
		                default:
		                    g.setColor(Color.GRAY); // Default color if no match
		                    break;
		            }
		            g.fillRect(position.x * cellSize, position.y * cellSize, cellSize, cellSize);
		            g.setColor(Color.BLACK);
		        }
		        for (String vehicleId : vehicleIds) {
		            g.drawString(vehicleId, position.x * cellSize + 5, position.y * cellSize + yOffset);
		            yOffset += 15; // Increase yOffset for next vehicle
		            VehicleInfo vehicleInfo = vehiclePositions.get(vehicleId);
		            if (vehicleInfo != null) {
		                // Get battery level
		                int batteryLevel = vehicleInfo.getBatteryLevel();

		                // Draw battery level
		                g.drawString("Battery: " + batteryLevel, position.x * cellSize + 5, position.y * cellSize + yOffset + 15);

		               
		                // Update battery level in vehiclePositions map
		                vehiclePositions.get(vehicleId).setBatteryLevel(batteryLevel);
		            }
		           
		            }
		        
		    }
		}


    /**
     * Updates the position of a vehicle on the map.
     * @param vehicleId The ID of the vehicle.
     * @param x The x-coordinate of the new position.
     * @param y The y-coordinate of the new position.
     * @param batteryLevel The battery level of the vehicle.
     */
    public  void updatePosition(String vehicleId, int x, int y, int batteryLevel) {
    	vehiclePositions.put(vehicleId, new VehicleInfo(new Point(x, y), batteryLevel));
        repaint(); // Refresh the display to show the updated vehicle position
    }
		    
		   

    
		    
     
    /**
     * Checks if a given position on the map is within the wide area.
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     * @return true if the position is within the wide area, false otherwise.
     */
    public boolean isWideArea(int x, int y) {
        // Check if the coordinates (x, y) are within the map boundaries
        if (x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE) {
            // Check if the cell at the given coordinates is in the wide area (white color)
            return polja[y][x] == 0; // We assume that 0 represents the color white (wide area)
        } else {
            // If the coordinates are outside the map boundaries, assume it's not a wide area
            return false;
        }
    }

    /**
     * Removes old vehicles from the map.
     * This method clears the map of all vehicle positions.
     */
    public void removeOldVehicles() {
        vehiclePositions.clear(); // Remove all current vehicle positions
        repaint(); // Redraw the map without the old vehicles
    }

}
