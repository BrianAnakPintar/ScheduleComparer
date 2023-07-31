package persistence;

import exceptions.InvalidCourseTimeException;
import model.AppState;
import model.Course;
import model.Day;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*

Code modified from JsonSerializationDemo project:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

 */

// Represents a reader that reads AppState from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AppState read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAppState(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses AppState from JSON object and returns it
    private AppState parseAppState(JSONObject jsonObject) {
        AppState appState = new AppState();
        addCourses(appState, jsonObject);
        addAccounts(appState, jsonObject);
        return appState;
    }

    // MODIFIES: appState
    // EFFECTS: Parses all courses from JSON object and adds them to appState
    private void addCourses(AppState appState, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject course = (JSONObject) json;
            addCourse(appState, course);
        }
    }

    // MODIFIES: appState
    // EFFECTS: Parses all users from JSON object and adds them to appState
    private void addAccounts(AppState appState, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("accounts");
        for (Object json : jsonArray) {
            JSONObject user = (JSONObject) json;
            addUser(appState, user);
        }
    }

    // MODIFIES: appState
    // EFFECTS: Creates a user from JSON object
    private void addUser(AppState appState, JSONObject jsonObject) {
        String username = jsonObject.getString("name");
        User user = new User(username);
        JSONArray weekSchedule = jsonObject.getJSONArray("weekSchedule");
        int i = 0;
        for (Object json : weekSchedule) {
            JSONObject day = (JSONObject) json;
            Day parsedDay = readDay(appState, day);
            user.getWeekSchedule().set(i, parsedDay);
            i++;
        }
        appState.getAccounts().put(username, user);

    }

    // EFFECTS Reads JSON object and returns a Day based on the parsed JSON object.
    private Day readDay(AppState appState, JSONObject jsonObject) {
        String dayName = jsonObject.getString("dayName");
        JSONArray schedule = jsonObject.getJSONArray("schedule");
        Day day = new Day(dayName);

        for (Object json : schedule) {
            JSONObject timeSlot = (JSONObject) json;
            int time = timeSlot.getInt("time");

            if (!timeSlot.isNull("course")) {
                JSONObject course = timeSlot.getJSONObject("course");
                String name = course.getString("name");
                try {
                    day.addCourse(time, appState.getCourses().get(name));
                } catch (InvalidCourseTimeException e) {
                    System.err.println("Error adding schedule, Invalid Course Time");
                }
            }
        }
        return day;
    }



    // MODIFIES: appState
    // EFFECTS: parses course from JSON object and adds it to appState
    private void addCourse(AppState appState, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String location = jsonObject.getString("location");
        Course course = new Course(name, location);
        appState.getCourses().put(name, course);
    }
}
