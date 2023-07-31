package model;

import org.json.JSONObject;
import persistence.Writable;

// Course which contains basic information contained within a course.
public class Course implements Writable {
    private String name;
    private String location;

    // EFFECTS: Constructor for course
    public Course(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // EFFECTS: Turns object into a JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("location", location);
        return jsonObject;
    }
}
