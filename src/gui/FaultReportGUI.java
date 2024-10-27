package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import rental.Rental;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for generating and displaying fault reports using Swing GUI.
 * This class provides a static method to generate a fault report GUI based on a list of rentals.
 */
public class FaultReportGUI {

    /**
     * Generates and displays a fault report GUI based on the provided list of rentals.
     * The GUI includes a table showing vehicles with reported faults.
     * @param rentals The list of rentals to generate the fault report for.
     */
    public static void generateFaultReport(List<Rental> rentals) {
        // Creating table model
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Vrsta prevoznog sredstva");
        model.addColumn("ID");
        model.addColumn("Vrijeme");
        model.addColumn("Opis kvara");

        // Filtering rentals with faults and adding rows to the table model
        List<Rental> rentalsWithFaults = rentals.stream()
                .filter(Rental::hasFault)
                .collect(Collectors.toList());

        for (Rental rental : rentalsWithFaults) {
            model.addRow(new Object[]{
                    rental.getVehicleType(),
                    rental.getVehicleId(),
                    rental.getDateTime(),
                    rental.getFaultDescription()
            });
        }

        // Creating JTable with the specified model
        JTable table = new JTable(model);

        // Setting table appearance
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(30);
        
        // Setting background and foreground color
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.BLACK);
        
        // Adding the table to a JScrollPane to enable scrolling if the table is large
        JScrollPane scrollPane = new JScrollPane(table);

        // Creating and displaying the GUI with the table
        JFrame frame = new JFrame("Fault Report");
        frame.add(scrollPane);
        frame.setSize(800, 400); // Setting window size
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
