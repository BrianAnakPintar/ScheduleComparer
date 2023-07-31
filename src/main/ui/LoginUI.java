package ui;

import model.AppState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The class that is shown when the user is trying to login/register
public class LoginUI extends JFrame implements ActionListener {
    private final AppState appState;

    private final JPanel panel;
    private JPanel rightPanel;
    private final JPanel leftPanel;

    private JLabel usernameLabel;
    private JTextField username;
    private JButton loginButton;
    private JButton signupButton;

    // MODIFIES: this
    // EFFECTS: Sets up the interface by instantiating appropriate components
    public void setUpInterfaces() {
        usernameLabel = new JLabel("Username");
        usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(usernameLabel);
        username = new JTextField(JTextField.CENTER);
        username.setBounds(88, 25, 200, 25);
        username.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(username);

        loginButton = new JButton("Login");
        loginButton.setActionCommand("login");
        loginButton.addActionListener(this);
        leftPanel.add(loginButton);

        signupButton = new JButton("Sign up");
        signupButton.setPreferredSize(new Dimension(50, 50));
        signupButton.setActionCommand("signup");
        signupButton.addActionListener(this);
        leftPanel.add(signupButton);

        UIDesigner.coolButton(loginButton);
        UIDesigner.coolButton(signupButton);
    }

    // MODIFIES: this
    // EFFECTS: Constructs the right side of the login screen
    private void setUpRightSide() {
        rightPanel = new JPanel(new GridLayout());
        JLabel img = new JLabel(new ImageIcon("./data/images/patrick.jpg"));
        rightPanel.add(img);
    }

    // MODIFIES: this
    // EFFECTS: Constructs the login UI
    public LoginUI(AppState appState) {
        // Basic initializations
        super("Meetup Scheduler, Login");
        GridLayout panelLayout = new GridLayout(4, 1,10,10);
        panel = new JPanel(new GridLayout(1, 2));
        leftPanel = new JPanel(panelLayout);
        setUpRightSide();
        setUpInterfaces();

        panel.add(leftPanel);
        panel.add(rightPanel);

        add(panel);
        setSize(450, 250);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        this.appState = appState;
        UIDesigner.quitSave(this, this.appState);
    }

    // MODIFIES: this
    // EFFECTS: Log-in if user clicks the login button, and registers the user if the register button is clicked.
    //          Uses the username JTextField as the string.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("login")) {
            if (appState.isRegisteredUser(username.getText().toLowerCase())) {
                appState.logIn(username.getText().toLowerCase());
                new MainUI(appState);
                this.dispose();
            } else {
                String message = "User not found!";
                JOptionPane.showMessageDialog(new JFrame(), message, "User Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().equals("signup")) {
            if (appState.isRegisteredUser(username.getText().toLowerCase())) {
                String message = "User already registered!";
                JOptionPane.showMessageDialog(new JFrame(), message, "User Error", JOptionPane.ERROR_MESSAGE);
            } else {
                appState.signUp(username.getText().toLowerCase());
                new MainUI(appState);
                this.dispose();
            }
        }
    }
}
