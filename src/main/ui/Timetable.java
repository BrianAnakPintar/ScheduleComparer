package ui;

import exceptions.InvalidCourseTimeException;
import model.AppState;
import model.Course;
import model.Day;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.server.UID;
import java.util.ArrayList;

// Represents the Timetable UI
public class Timetable extends JFrame implements ActionListener {

    private final AppState appState;
    private final JPanel mainPanel;

    private final JButton addCourse = new JButton("Add course");
    private final JButton removeCourse = new JButton("Remove course");
    private final JButton deleteCourse = new JButton("Delete Course");
    private final JButton close = new JButton("Close");

    // MODIFIES: this
    // EFFECTS: Initializes the navbar on the left side
    private void initNavBar() {
        GridLayout navBarLayout = new GridLayout(5, 1, 0, 10);
        JPanel navbar = new JPanel(navBarLayout);
        navbar.setBorder(new EmptyBorder(20, 20, 20, 20));
        navbar.setBackground(new Color(232, 232, 232));
        navbar.setPreferredSize(new Dimension(200, 300));

        navbar.add(addCourse);
        UIDesigner.coolButton(addCourse);
        addCourse.setActionCommand("addCourse");
        addCourse.addActionListener(this);
        navbar.add(removeCourse);
        UIDesigner.coolButton(removeCourse);
        removeCourse.setActionCommand("removeCourse");
        removeCourse.addActionListener(this);
        navbar.add(close);
        close.setActionCommand("close");
        close.addActionListener(this);
        deleteCourse.setActionCommand("delete");
        deleteCourse.addActionListener(this);
        navbar.add(deleteCourse);

        mainPanel.add(navbar, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: Initializes the main timetable at the center
    private void initTimeTable() {
        GridLayout columnGrid = new GridLayout(1, 8);
        JPanel columnPanel = new JPanel(columnGrid);
        columnPanel.setBackground(new Color(0xEEACA0));
        GridLayout rowGrid = new GridLayout(26, 1, 1, 1);
        JPanel information = new JPanel(rowGrid);
        makeInformation(information);
        // ADDS INFO PANEL
        columnPanel.add(information);
        // ADDS ALL THE DAYS
        addDays(columnPanel);
        mainPanel.add(columnPanel, BorderLayout.CENTER);
    }

    // MODIFIES: information
    // EFFECTS: Creates the left-most column of the main table which contains descriptions of each row
    private void makeInformation(JPanel information) {
        JLabel header = new JLabel("Time", SwingConstants.CENTER);
        UIDesigner.coolLabel(header);
        information.add(header);
        for (int i = Day.START_TIME; i <= Day.END_TIME; i += Day.INTERVAL) {
            // Since time is base 60, this code makes it so that if it's 8:60 it becomes 8:70 and goes to the next
            // iteration meaning 870 + 30 = 9:00 which is what we want.
            if (i % 100 == 60) {
                i += 10;
                continue;
            }
            String time = formatTime(i);
            JLabel currLabel = new JLabel(time, SwingConstants.CENTER);
            UIDesigner.coolLabel(currLabel);
            information.add(currLabel);
        }
    }

    // MODIFIES: columnPanel
    // EFFECTS: Inserts courses to each day
    private void addDays(JPanel columnPanel) {
        for (int i = 0; i < 7; i++) {
            Day day = appState.getUser().getWeekSchedule().get(i);
            JPanel panel = insertCourses(day);
            columnPanel.add(panel);
        }
    }

    // MODIFIES: this
    // EFFECTS: Adds the courses from appState into the appropriate time into the timetable
    private JPanel insertCourses(Day day) {
        GridLayout rowGrid = new GridLayout(26, 1, 1, 1);
        JPanel panel = new JPanel(rowGrid);
        JLabel header = new JLabel(day.getDayName(), SwingConstants.CENTER);
        UIDesigner.coolLabel(header);
        panel.add(header);
        for (int i = Day.START_TIME; i <= Day.END_TIME; i += Day.INTERVAL) {
            if (i % 100 == 60) {
                i += 10;
                continue;
            }
            Course course = day.getCourse(i);
            if (invalid(course)) {
                course = null;
            }
            JLabel text = new JLabel("", SwingConstants.CENTER);
            UIDesigner.coolLabel(text);
            if (course != null) {
                UIDesigner.courseLabel(text);
                text.setText(course.getName());
            }
            panel.add(text);
        }
        return panel;
    }

    // MODIFIES: course
    // EFFECTS: If course is not contained in the HASHMAP, then we set it to null.
    private Boolean invalid(Course course) {
        return appState.getCourses() != null && course != null
                && !appState.getCourses().containsKey(course.getName());
    }

    // REQUIRES: 800 <= time <= 2000
    // EFFECTS: Formats time as a string (e.g. 800 becomes 08:00)
    private String formatTime(int time) {
        String formatted;
        if (time / 100 < 10) {
            formatted = "0" + (time / 100) + ":";
        } else {
            formatted = (time / 100) + ":";
        }
        if ((time % 100) == 0) {
            formatted += "00";
        } else {
            formatted += (time % 100);
        }
        return formatted;
    }

    // MODIFIES: this
    // EFFECTS: Constructs the Timetable UI
    public Timetable(AppState appState) {
        super("Timetable");
        this.appState = appState;
        mainPanel = new JPanel(new BorderLayout());

        initNavBar();
        initTimeTable();
        add(mainPanel);

        // Set the frame's size and make it visible
        setMinimumSize(new Dimension(800, 500));
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Checks if the any button is pressed and do the appropriate task.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addCourse")) {
            addCourseCommandMain();
            new Timetable(appState);
            dispose();
        } else if (e.getActionCommand().equals("removeCourse")) {
            int day = getDayDialog();
            int timeDialog = getTimeDialog();
            addCourse(timeDialog, null, day);
            new Timetable(appState);
            dispose();
        } else if (e.getActionCommand().equals("close")) {
            dispose();
        } else if (e.getActionCommand().equals("delete")) {
            String courseName = addCourseCommand(1);
            appState.removeCourse(courseName);
            new Timetable(appState);
            dispose();
        }
    }

    // MODIFIES: this
    // EFFECTS: Shows various forms to add a course
    private void addCourseCommandMain() {
        int newCourse = JOptionPane.showConfirmDialog(null,
                "Do you want to create a new course?", "New Course?",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        String courseName = addCourseCommand(newCourse);
        if (courseName == null) {
            return;
        }
        int day = getDayDialog();
        if (day == -1) {
            return;
        }
        int timeDialog = getTimeDialog();
        if (timeDialog == -1) {
            return;
        }
        addCourse(timeDialog, appState.getCourses().get(courseName), day);
    }

    // EFFECTS: Shows dialog that asks user to input time.
    private int getTimeDialog() {
        ImageIcon icon = new ImageIcon("./data/images/pandaIcon.jpg");
        Object[] timeOptions = getTimes().toArray();
        Integer time = (Integer)JOptionPane.showInputDialog(null, "Select time:",
                "Input day", JOptionPane.QUESTION_MESSAGE, icon, timeOptions, timeOptions[0]);
        if (time == null) {
            return -1;
        }
        return time;
    }

    // EFFECTS: Returns an integer of valid timeslots that can be accepted in the application
    private ArrayList<Integer> getTimes() {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = Day.START_TIME; i <= Day.END_TIME; i += Day.INTERVAL) {
            if (i % 100 == 60) {
                i += 10;
                continue;
            }
            result.add(i);
        }
        return result;
    }

    // MODIFIES: this
    // EFFECTS: Takes time and coverts it into a string, then inserts it into a course into the day at specified time
    private void addCourse(int time, Course course,int day) {
        try {
            appState.getUser().getWeekSchedule().get(day).addCourse(time, course);
            System.out.println("Success!");
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Cancelled.");
        } catch (InvalidCourseTimeException e) {
            System.err.println("Error, invalid time");
        }
    }

    // EFFECTS: Shows a dialog which asks for day
    private int getDayDialog() {
        String[] options = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String day = (String)JOptionPane.showInputDialog(null, "Select day:",
                "Input day", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (day == null) {
            return -1;
        }
        switch (day) {
            case "Sunday":
                return 6;
            case "Tuesday":
                return 1;
            case "Wednesday":
                return 2;
            case "Thursday":
                return 3;
            case "Friday":
                return 4;
            case "Saturday":
                return 5;
            default:
                return 0;
        }
    }

    // MODIFIES: this
    // EFFECTS: Shows the menu when trying to add course to schedule and returns the string of the course Name
    private String addCourseCommand(int option) {
        if (option == 0) {
            // Means create new course
            String courseName = JOptionPane.showInputDialog("Enter course name:");
            if (courseName == null) {
                JOptionPane.showMessageDialog(null, "Enter a valid course please",
                        "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            courseName.toUpperCase();
            String location = JOptionPane.showInputDialog("Enter location: ");
            appState.createCourse(new Course(courseName, location));
            return courseName;
        } else {
            if (appState.getCourses().size() <= 0) {
                JOptionPane.showMessageDialog(null, "No course has been added yet",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            Object[] courseOptions = appState.getCourses().keySet().toArray();
            String courseName = (String) JOptionPane.showInputDialog(null, "Select time:",
                    "Input day", JOptionPane.QUESTION_MESSAGE, null, courseOptions, courseOptions[0]);
            return courseName;
        }
    }
}
