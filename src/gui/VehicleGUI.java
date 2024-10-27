/**
 * Represents a graphical user interface (GUI) for displaying information about vehicles.
 */
package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import vehicles.Car;
import vehicles.ElectricBike;
import vehicles.Scooter;
import vehicles.Vehicle;

import java.util.List;

/**
 * A JFrame-based GUI for displaying information about vehicles.
 */
public class VehicleGUI extends JFrame {

    /**
     * Constructs a VehicleGUI to display information about the given list of vehicles.
     * @param vehicles the list of vehicles to display
     */
    public VehicleGUI(List<Vehicle> vehicles) {
        setTitle("All Vehicles Display");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Classify vehicles by type
        List<Vehicle> cars = vehicles.stream()
                .filter(vehicle -> vehicle instanceof Car)
                .toList();
        List<Vehicle> bikes = vehicles.stream()
                .filter(vehicle -> vehicle instanceof ElectricBike)
                .toList();
        List<Vehicle> scooters = vehicles.stream()
                .filter(vehicle -> vehicle instanceof Scooter)
                .toList();

        // Create tables
        JTable carTable = createVehicleTable(cars, "Car");
        JTable bikeTable = createVehicleTable(bikes, "Electric Bicycle");
        JTable scooterTable = createVehicleTable(scooters, "Electric Scooter");

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Cars", new JScrollPane(carTable));
        tabbedPane.add("Electric Bicycles", new JScrollPane(bikeTable));
        tabbedPane.add("Electric Scooters", new JScrollPane(scooterTable));

        add(tabbedPane);

        setVisible(true);
    }

    /**
     * Creates a JTable containing information about the given vehicles.
     * @param vehicles the list of vehicles to display in the table
     * @param vehicleType the type of vehicles being displayed
     * @return a JTable containing information about the vehicles
     */
    private JTable createVehicleTable(List<Vehicle> vehicles, String vehicleType) {
        String[] columnNames = {"ID", "Manufacturer", "Model",  "Price", "Battery", "Purchase Date", "Description"};
        if (vehicleType.equals("Electric Bicycle")) {
            columnNames = new String[]{"ID", "Manufacturer", "Model", "Price", "Battery", "Range"};
        } else if (vehicleType.equals("Electric Scooter")) {
            columnNames = new String[]{"ID", "Manufacturer", "Model",  "Price", "Battery", "Max Speed"};
        }
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Vehicle vehicle : vehicles) {
            Object[] rowData = new Object[7];
            rowData[0] = vehicle.getID();
            rowData[1] = vehicle.getManufacturer();
            rowData[2] = vehicle.getModel();
            //rowData[3] = vehicle.getDate();
            rowData[3] = vehicle.getPrice();
            rowData[4] = vehicle.getBatteryPercentage();
            if (vehicle instanceof ElectricBike) {
                rowData[5] = ((ElectricBike) vehicle).getRange();
            } else if (vehicle instanceof Scooter) {
                rowData[5] = ((Scooter) vehicle).getMaxSpeed();
            }
            else if (vehicle instanceof Car) {
                rowData[5] = ((Car) vehicle).getDate();
                rowData[6] = ((Car) vehicle).getDescription();
            }
            tableModel.addRow(rowData);
        }

        return new JTable(tableModel);
    }
}
