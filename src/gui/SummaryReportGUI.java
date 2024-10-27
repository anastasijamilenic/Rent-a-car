/**
 * Represents a graphical user interface (GUI) for displaying a summary report.
 */
package gui;

import javax.swing.*;

/**
 * A JFrame-based GUI for displaying a summary report.
 */
public class SummaryReportGUI extends JFrame {

    private JTextArea summaryTextArea;

    /**
     * Constructs a SummaryReportGUI with the specified summary text.
     * @param summary the summary text to be displayed
     */
    public SummaryReportGUI(String summary) {
        setTitle("Summary Report");
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Close only this window

        // Create JTextArea for displaying the summary report
        summaryTextArea = new JTextArea(summary);
        summaryTextArea.setEditable(false); // Text cannot be edited
        JScrollPane scrollPane = new JScrollPane(summaryTextArea);
        add(scrollPane);

        // Show the GUI
        setVisible(true);
    }
}
