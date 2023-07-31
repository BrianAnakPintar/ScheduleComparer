package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.HashMap;
import java.util.Map;

// Represents the highest-level state, where all users and courses are stored
public class AppState implements Writable {
    private final Map<String, User> accounts;
    private final Map<String, Course> courses;
    private User user;

    // EFFECTS: Constructor for AppState, initializing accounts and courses
    public AppState() {
        accounts = new HashMap<>();
        courses = new HashMap<>();
    }

    public Map<String, User> getAccounts() {
        return accounts;
    }

    public Map<String, Course> getCourses() {
        return courses;
    }

    public User getUser() {
        return user;
    }

    // EFFECTS: Returns true if there is user with specified username in accounts, false otherwise
    public Boolean isRegisteredUser(String username) {
        return accounts.containsKey(username);
    }

    // MODIFIES: this
    // EFFECTS: Inserts course into courses
    public void createCourse(Course course) {
        courses.put(course.getName(), course);
        EventLog.getInstance().logEvent(new Event("Created new course"));
    }

    // MODIFIES: this
    // EFFECTS: Removes course from courses
    public void removeCourse(String courseName) {
        courses.remove(courseName);
        EventLog.getInstance().logEvent(new Event("Removed course"));
    }

    // MODIFIES: this
    // EFFECTS: Adds a new user to the accounts list and automatically logs in as the new user
    public void signUp(String username) {
        user = new User(username);
        accounts.put(username, user);
        EventLog.getInstance().logEvent(new Event("Created a new user"));
    }

    // MODIFIES: this
    // REQUIRES: User must be registered in accounts
    // EFFECTS: Sets user as the user with specified username
    public void logIn(String username) {
        this.user = accounts.get(username);
        EventLog.getInstance().logEvent(new Event("Logged in to: " + username));
    }

    // MODIFIES: this
    // EFFECTS: Sets the current user to be null
    public void logout() {
        this.user = null;
        EventLog.getInstance().logEvent(new Event("Logged out"));
    }

    // EFFECTS: Turns object into a JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("accounts", accountsToJson());
        jsonObject.put("courses", coursesToJson());

        return jsonObject;
    }

    // EFFECTS: Returns accounts as a JSON array
    private JSONArray accountsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String username : accounts.keySet()) {
            jsonArray.put(accounts.get(username).toJson());
        }

        return jsonArray;
    }

    // EFFECTS: Returns courses as a JSON array
    private JSONArray coursesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String courseName : courses.keySet()) {
            jsonArray.put(courses.get(courseName).toJson());
        }

        return jsonArray;
    }



}
