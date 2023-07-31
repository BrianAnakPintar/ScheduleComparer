package persistence;

import org.json.JSONObject;

/*

Code modified from JsonSerializationDemo project:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

 */

// Interface which represents something is writable as JSON
public interface Writable {
    // EFFECTS: returns the object as JSON object
    JSONObject toJson();
}
