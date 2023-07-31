package model;

import exceptions.InvalidCourseTimeException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.HashMap;
import java.util.Map;

// The class represents a day which contains a schedule for that day and the name of the day.
public class Day implements Writable {
    private final String dayName;

    public static final int START_TIME = 800;
    public static final int END_TIME = 2000;
    public static final int INTERVAL = 30;

    private final Map<Integer, Course> schedule;

    // EFFECTS: Constructor for day
    public Day(String dayName) {
        this.dayName = dayName;
        schedule = new HashMap<>();

        // Creates an empty schedule
        for (int i = START_TIME; i <= END_TIME; i += INTERVAL) {
            // Since time is base 60, this code makes it so that if it's 8:60 it becomes 8:70 and goes to the next
            // iteration meaning 870 + 30 = 9:00 which is what we want.
            if (i % 100 == 60) {
                i += 10;
                continue;
            }
            schedule.put(i, null);
        }

    }

    public String getDayName() {
        return dayName;
    }

    public Course getCourse(int time) {
        return schedule.get(time);
    }

    // MODIFIES: this
    // EFFECTS: Adds a course to the schedule
    public void addCourse(int time, Course course) throws InvalidCourseTimeException {
        if (validTime(time)) {
            schedule.put(time, course);
            if (course != null) {
                EventLog.getInstance().logEvent(new Event("Added "
                        + course.getName() + " at: " + dayName + " " + time));
            } else {
                EventLog.getInstance().logEvent(new Event("Removed course at: "
                        + dayName + " " + time));
            }
        } else {
            throw new InvalidCourseTimeException();
        }
    }

    // EFFECTS: Returns true if 800 <= time <= 2000, and time % 100 = 0 or time % 100 = 30
    private Boolean validTime(int time) {
        return  ((time >= 800 && time <= 2000) && ((time % 100 == 0) || (time % 100 == 30)));
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

    // EFFECTS: Returns the schedule for today
    public String printDay() {
        String result = "";
        result += (dayName + " schedule: \n");
        for (int i = START_TIME; i <= END_TIME; i += INTERVAL) {
            if (i % 100 == 60) {
                i += 10;
                continue;
            }
            if (getCourse(i) != null) {
                result += (formatTime(i) + " Course: " + getCourse(i).getName() + "\n");
            } else {
                result += (formatTime(i) + " Course: -\n");
            }
        }
        return result;
    }

    // EFFECTS: Turns object into a JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dayName", dayName);
        jsonObject.put("schedule", scheduleToJson());
        return jsonObject;
    }

    // EFFECTS: Turns Schedule into JSON format
    public JSONArray scheduleToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Integer time : schedule.keySet()) {
            jsonArray.put(timeSlotToJson(time, schedule.get(time)));
        }
        return jsonArray;
    }

    // EFFECTS: Turns each time slot into JSON format
    public JSONObject timeSlotToJson(int time, Course course) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("time", time);
        if (course == null) {
            jsonObject.put("course", JSONObject.NULL);
        } else {
            jsonObject.put("course", course.toJson());
        }
        return jsonObject;
    }
}
