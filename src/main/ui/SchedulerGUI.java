package ui;

import model.AppState;
import persistence.JsonReader;

import javax.swing.*;
import java.io.IOException;

// The initial start of the application, prompting the user if he wants to load previous data.
public class SchedulerGUI {
    private static final String DATA_DIR = "./data/schedulerData.json";
    private final JsonReader jsonReader;

    private AppState appState;

    // MODIFIES: this
    // EFFECTS: Constructor for when program starts, prompting the user if they want to load previous data or not
    public SchedulerGUI() {
        jsonReader = new JsonReader(DATA_DIR);

        ImageIcon icon = new ImageIcon("./data/images/pandaIcon.jpg");
        int input = JOptionPane.showConfirmDialog(null,
                "Do you want to load previously stored data?", "Load data?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
        if (input == 0) {
            // User answered yes
            loadAppState();
        } else {
            appState = new AppState();
        }
        new LoginUI(appState);
    }

    // MODIFIES: this
    // EFFECTS: Loads the AppState from the JSON file
    private void loadAppState() {
        try {
            appState = jsonReader.read();
            System.out.println("Loaded previous Data");
        } catch (IOException e) {
            System.out.println("Unable to read data");
        }
    }
}
