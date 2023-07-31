package ui;

import exceptions.InvalidCourseTimeException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

// Console scheduler which takes user inputs and displays them.
public class Scheduler {
    private static final String DATA_DIR = "./data/schedulerData.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    private boolean active;
    private boolean loadScreen;

    private AppState appState;
    private boolean loggedIn;

    String userInput = null;
    private Scanner input;

    // EFFECTS: runs the scheduler application and initializes fields.
    public Scheduler() {
        appState = new AppState();
        jsonWriter = new JsonWriter(DATA_DIR);
        jsonReader = new JsonReader(DATA_DIR);

        startScheduler();
    }

    // MODIFIES: this
    // EFFECTS: Starts the scheduler
    public void startScheduler() {
        active = true;
        loadScreen = true;

        input = new Scanner(System.in);
        input.useDelimiter("\n");

        while (loadScreen) {
            displayLoadData();
            userInput = input.nextLine().toLowerCase();
            processLoadData(userInput);
        }

        while (active) {
            if (!loggedIn) {
                displayLoginScreen();
                userInput = input.nextLine().toLowerCase();
                loginCommands(userInput);
            } else {
                displayMainScreen();
                userInput = input.nextLine().toLowerCase();
                mainMenuCommands(userInput);
            }

        }
        System.out.println("Exiting app... 👍");
    }

    // EFFECTS: Displays the load data menu
    private void displayLoadData() {
        System.out.println("---------------------------------");
        System.out.println("Do you want to load previous data");
        System.out.println("---------------------------------");
        System.out.println("  y -> Yes");
        System.out.println("  n -> No");
    }

    // MODIFIES: this
    // EFFECTS: Processes the Load Data command
    private void processLoadData(String key) {
        if (Objects.equals(key, "y")) {
            loadAppState();
            loadScreen = false;
        } else if (Objects.equals(key, "n")) {
            loadScreen = false;
        } else {
            displayUnknownCommand();
        }
    }

    // EFFECTS: Displays the main menu to user
    private void displayMainScreen() {
        System.out.println("---------------------------");
        System.out.println("Select one of these options");
        System.out.println("---------------------------");
        System.out.println("  a -> Add course");
        System.out.println("  r -> Remove course");
        System.out.println("  v -> View schedule");
        System.out.println("  c -> Compare schedule");
        System.out.println("  s -> show other users");
        System.out.println("  l -> Logout");
    }

    // MODIFIES: this
    // EFFECTS: Calls appropriate function from user input
    private void mainMenuCommands(String key) {
        if (Objects.equals(key, "a")) {
            displayAddCourses();
            userInput = input.nextLine().toLowerCase();
            addCourseCommands(userInput);
        } else if (Objects.equals(key, "r")) {
            removeCourse();
        } else if (Objects.equals(key, "v")) {
            viewDays();
        } else if (Objects.equals(key, "c")) {
            displayUsers();
            displayCompareMenu();
            userInput = input.nextLine().toLowerCase();
            compareMenuOptions(userInput);
        } else if (Objects.equals(key, "s")) {
            displayUsers();
        } else if (Objects.equals(key, "l")) {
            loggedIn = false;
        } else {
            displayUnknownCommand();
        }
    }

    // EFFECTS: Display options for comparing schedule
    private void displayCompareMenu() {
        System.out.println("Select one of these options:");
        System.out.println("  f -> See overlapping free time");
        System.out.println("  s -> See same courses");
    }

    // MODIFIES: this
    // EFFECTS: Processes commands for comparing schedule
    private void compareMenuOptions(String key) {
        if (Objects.equals("f", key)) {
            processCompare(1);
        } else if (Objects.equals("s", key)) {
            processCompare(2);
        } else {
            displayUnknownCommand();
        }
    }

    // MODIFIES: this
    // EFFECTS: Compare based on option
    private void processCompare(int option) {
        User otherUser;
        displayUsers();
        System.out.println("Enter username:");
        userInput = input.nextLine().toLowerCase();
        if (appState.getAccounts().get(userInput) == null) {
            System.err.println("Invalid username");
            return;
        } else {
            otherUser = appState.getAccounts().get(userInput);
        }
        displayDays();
        userInput = input.nextLine();
        int day = validateDay(userInput);

        switch (option) {
            case 1:
                compare(1, appState.getUser().getWeekSchedule().get(day - 1), otherUser.getWeekSchedule().get(day - 1));
                break;
            case 2:
                compare(2, appState.getUser().getWeekSchedule().get(day - 1), otherUser.getWeekSchedule().get(day - 1));
                break;
        }
    }

    // EFFECTS: produce an int of the string day given. Produce -1 if it's invalid.
    private int validateDay(String dayString) {
        try {
            int cmd = Integer.parseInt(dayString);
            if (cmd <= 7 && cmd >= 1) {
                return cmd;
            }
            return -1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // EFFECTS: Compares given schedule based on option and prints them out
    private void compare(int option, Day userOne, Day userTwo) {
        ScheduleComparer comparer = new ScheduleComparer(userOne, userTwo);

        switch (option) {
            case 1:
                System.out.println("Here are the list of overlapping free times:");
                System.out.println(comparer.findFree());
                break;
            case 2:
                System.out.println("Here are the list of same courses and time");
                System.out.println(comparer.findSimilar());
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes user specified course
    private void removeCourse() {
        viewDays();
        int day = Integer.parseInt(userInput);
        inputTime();
        addCourse(userInput, null, day);
        System.out.println("Successfully Removed!");
    }

    // MODIFIES: this
    // EFFECTS: Processes user command to view a specified day. If command is unknown, then produce error message.
    private void viewDays() {
        displayDays();
        userInput = input.nextLine().toLowerCase();
        viewCommands(userInput);
    }

    // EFFECTS: Displays the menu to login, create account or quit application
    private void displayLoginScreen() {
        System.out.println("---------------------------------");
        System.out.println("Welcome to the Course Scheduler:");
        System.out.println("Select one of these options");
        System.out.println("  l -> Login");
        System.out.println("  c -> Create Account");
        System.out.println("  q -> Quit Application");
    }

    // MODIFIES: this
    // EFFECTS: Checks user input and calls the appropriate function
    private void loginCommands(String key) {
        if (Objects.equals(key, "l")) {
            displayAccountScreen(false);
        } else if (Objects.equals(key, "c")) {
            displayAccountScreen(true);
        } else if (Objects.equals(key, "q")) {
            displayQuitSave();
            processQuitSave();
        } else {
            displayUnknownCommand();
        }
    }

    // EFFECTS: Displays option to save the appState before quitting the application
    private void displayQuitSave() {
        System.out.println("Do you want to save your schedule and courses?");
        System.out.println("  y -> Yes");
        System.out.println("  n -> No");
    }

    // MODIFIES: this
    // EFFECTS: Processes the menu commands for saving before you quit the application
    private void processQuitSave() {
        userInput = input.nextLine().toLowerCase();
        if (Objects.equals(userInput, "y")) {
            saveAppState();
            active = false;
        } else if (Objects.equals(userInput, "n")) {
            active = false;
        } else {
            displayUnknownCommand();
        }
    }

    // MODIFIES: this
    // EFFECTS: Gets username and either create a new account or login
    private void displayAccountScreen(boolean isNewUser) {
        System.out.println("Enter username: ");
        userInput = input.nextLine().toLowerCase();

        if (isNewUser) {
            if (appState.isRegisteredUser(userInput)) {
                System.err.println("ERROR, user already exist.");
            } else {
                System.out.println("Creating new user...");
                appState.signUp(userInput);
                loggedIn = true;
            }
        } else {
            if (appState.isRegisteredUser(userInput)) {
                System.out.println("Logging in to, " + userInput + "...");
                appState.logIn(userInput);
                loggedIn = true;
                System.out.println("Logged in!");
            } else {
                System.err.println("⚠️ Error, user cannot be found.");
            }
        }
    }

    // EFFECTS: Display the Days menu.
    private void displayDays() {
        System.out.println("Select day");
        System.out.println("  1 - Monday");
        System.out.println("  2 - Tuesday");
        System.out.println("  3 - Wednesday");
        System.out.println("  4 - Thursday");
        System.out.println("  5 - Friday");
        System.out.println("  6 - Saturday");
        System.out.println("  7 - Sunday");
    }

    // EFFECTS: Processes commands for the view schedule operation
    private void viewCommands(String key) {
        try {
            int cmd = Integer.parseInt(key);
            System.out.println(appState.getUser().getWeekSchedule().get(cmd - 1).printDay());

        } catch (NumberFormatException e) {
            displayUnknownCommand();
        } catch (IndexOutOfBoundsException e) {
            System.err.println("ERROR, Number is invalid!");
        }
    }

    // EFFECTS: Displays all users in the accounts HashMap
    private void displayUsers() {
        System.out.println("Listing users...");
        for (String username : appState.getAccounts().keySet()) {
            System.out.println("- " + username);
        }
    }

    // EFFECTS: Displays available courses, along with the add course menu
    private void displayAddCourses() {
        System.out.println("Here are the currently available courses:");
        for (String courseName : appState.getCourses().keySet()) {
            System.out.println("- " + courseName);
        }
        System.out.println("Select one of the following options");
        System.out.println("  s -> Select one of the courses listed above");
        System.out.println("  c -> Create a new course");
    }

    // MODIFIES: this
    // EFFECTS: Processes add course commands
    private void addCourseCommands(String key) {
        if (Objects.equals("s", key)) {
            processCourseScreen(false);

        } else if (Objects.equals("c", key)) {
            processCourseScreen(true);

        } else {
            displayUnknownCommand();
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays UI and processes the add course command.
    private void processCourseScreen(Boolean isNewCourse) {
        System.out.println("Insert course name:");
        userInput = input.nextLine().toUpperCase();
        Course currentCourse;
        if (isNewCourse) {
            String courseName = userInput;
            System.out.println("Insert location name");
            userInput = input.nextLine().toUpperCase();
            currentCourse = new Course(courseName, userInput);
            appState.getCourses().put(courseName, currentCourse);
        } else {
            currentCourse = appState.getCourses().get(userInput);
        }
        if (currentCourse == null) {
            System.err.println("Error, Invalid Course");
            return;
        }
        viewDays();
        if (validateDay(userInput) != -1) {
            int day = Integer.parseInt(userInput);
            inputTime();
            addCourse(userInput, currentCourse, day);
        }
    }

    // MODIFIES: this
    // EFFECTS: Displays command to input time, then changes userInput to what user specified
    private void inputTime() {
        System.out.println("Insert Time (e.g. 800, 1000, 1430, etc.):");
        userInput = input.nextLine();
    }

    // MODIFIES: this
    // EFFECTS: Takes time and coverts it into a string, then inserts it into a course into the day at specified time
    private void addCourse(String timeStr, Course course,int day) {
        try {
            int time = Integer.parseInt(timeStr);
            appState.getUser().getWeekSchedule().get(day - 1).addCourse(time, course);
            System.out.println("Success!");
        } catch (NumberFormatException e) {
            System.err.println("Error, please input a number.");
        } catch (InvalidCourseTimeException e) {
            System.err.println("Error, invalid time");
        }
    }

    // EFFECTS: Displays an error in the console
    private void displayUnknownCommand() {
        System.err.println("😢 ERROR! Unknown command");
    }

    // MODIFIES: this
    // EFFECTS: saves the workroom to file
    private void saveAppState() {
        try {
            jsonWriter.open();
            jsonWriter.write(appState);
            jsonWriter.close();
            System.out.println("Saved " + " to " + DATA_DIR);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
        }
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
