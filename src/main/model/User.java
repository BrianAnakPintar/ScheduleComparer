package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// User class with the user, along with his weekly schedule.
public class User implements Writable {

    private final String name;
    private final ArrayList<Day> weekSchedule;

    // EFFECTS: Constructor for User
    public User(String name) {
        this.name = name;
        weekSchedule = new ArrayList<>(7);

        weekSchedule.add(new Day("Monday"));
        weekSchedule.add(new Day("Tuesday"));
        weekSchedule.add(new Day("Wednesday"));
        weekSchedule.add(new Day("Thursday"));
        weekSchedule.add(new Day("Friday"));
        weekSchedule.add(new Day("Saturday"));
        weekSchedule.add(new Day("Sunday"));

    }

    public String getName() {
        return name;
    }

    public ArrayList<Day> getWeekSchedule() {
        return weekSchedule;
    }

    // EFFECTS: Turns object into a JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("weekSchedule", scheduleToJson());
        return jsonObject;
    }

    // EFFECTS: returns the week schedule into a JSON array
    private JSONArray scheduleToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Day d : weekSchedule) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }
}
