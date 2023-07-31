package ui;

import model.AppState;
import model.Event;
import model.EventLog;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

// Helper class that is used to design UI elements and contains other useful utilities.
public class UIDesigner {
    private static final String DATA_DIR = "./data/schedulerData.json";
    private static final JsonWriter jsonWriter = new JsonWriter(DATA_DIR);

    private UIDesigner() {}

    // MODIFIES: label
    // EFFECTS: Adds font to specified label, which makes it look AMAZING!
    public static void coolLabel(JLabel label) {
        label.setFont(new Font("Helvetica", Font.BOLD, 12));
        label.setBorder(BorderFactory.createMatteBorder(-1, 1, 1, 1, Color.darkGray));
    }

    // MODIFIES: button
    // EFFECTS: Turns button which is passed as a parameter into a cooler looking button
    public static void coolButton(JButton button) {
        button.setFont(new Font("Helvetica", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(109, 123, 210));
        button.setOpaque(true);
        button.setBorderPainted(false);
    }

    // MODIFIES: frame
    // EFFECTS: Shows a save your changes dialog
    public static void quitSave(JFrame frame, AppState appState) {
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Do you want to save your changes?", "Save app?",
                        JOptionPane.YES_NO_OPTION);
                if (confirmed == JOptionPane.YES_OPTION) {
                    saveAppState(appState);
                }
                for (Event event:EventLog.getInstance()) {
                    System.out.println(event.getDate() + "\n" + event.getDescription() + "\n");
                }
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: saves the workroom to file
    private static void saveAppState(AppState appState) {
        try {
            jsonWriter.open();
            jsonWriter.write(appState);
            jsonWriter.close();
            System.out.println("Saved " + " to " + DATA_DIR);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
        }
    }

    public static void courseLabel(JLabel text) {
        text.setFont(new Font("Helvetica", Font.BOLD, 12));
        text.setBorder(BorderFactory.createMatteBorder(-1, 1, 1, 1, Color.darkGray));
        text.setOpaque(true);
        text.setBackground(new Color(0xBBDEF0));
    }
}
