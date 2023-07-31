package ui;

import model.AppState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The GUI for the main screen (After logging in / Signing up)
public class MainUI extends JFrame implements ActionListener {

    private final AppState appState;

    private final JPanel panel;

    private JButton schedule;
    private JButton compare;
    private JButton logout;

    // MODIFIES: this
    // EFFECTS: Initializes the left side of the menu
    private void initializeLeftSide() {
        JPanel leftPanel = new JPanel(new FlowLayout());
        JLabel bigText = new JLabel("WELCOME, " + appState.getUser().getName().toUpperCase());
        Font bigFont = new Font("Arial", Font.BOLD, 20);
        bigText.setFont(bigFont);
        bigText.setHorizontalAlignment(SwingConstants.CENTER);
        bigText.setVerticalAlignment(SwingConstants.CENTER);
        leftPanel.add(bigText);

        JLabel picLabel = new JLabel(new ImageIcon("./data/images/seahorse.jpeg"));
        leftPanel.add(picLabel);

        logout = new JButton("Logout");
        logout.setActionCommand("logout");
        logout.addActionListener(this);
        leftPanel.add(logout);
        panel.add(leftPanel);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the right side of the main menu
    private void initializeRightSide() {
        GridLayout rightLayout = new GridLayout(3, 1, 10, 10);
        JPanel rightPanel = new JPanel(rightLayout);
        schedule = new JButton("Schedule");
        schedule.setActionCommand("viewSchedule");
        schedule.addActionListener(this);

//        compare = new JButton("Compare");
//        compare.setActionCommand("compare");
//        compare.addActionListener(this);


        rightPanel.add(schedule);
//        rightPanel.add(compare);

        panel.add(rightPanel);
    }

    // MODIFIES: this
    // EFFECTS: Constructor for the main class
    public MainUI(AppState appState) {
        super("Meetup Scheduler!");
        this.appState = appState;

        GridLayout panelLayout = new GridLayout(1, 2,10,10);
        panel = new JPanel(panelLayout);
        add(panel);

        initializeLeftSide();
        initializeRightSide();

        setSize(750, 450);
        setLocationRelativeTo(null);
        setVisible(true);

        UIDesigner.quitSave(this, this.appState);
    }

    // MODIFIES: this
    // EFFECTS: Adds functionality to the button with their respective functions
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("logout")) {
            appState.logout();
            new LoginUI(appState);
            dispose();
        } else if (e.getActionCommand().equals("viewSchedule")) {
            new Timetable(appState);
        }
    }
}
