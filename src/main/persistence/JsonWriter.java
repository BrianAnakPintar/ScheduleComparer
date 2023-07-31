package persistence;

import model.AppState;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*

Code modified from JsonSerializationDemo project:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

 */


// Writer that writes JSON representation of AppState to file
public class JsonWriter {
    private final String outputDir;
    private PrintWriter writer;

    // EFFECTS: Constructor with a specified output directory
    public JsonWriter(String outputDir) {
        this.outputDir = outputDir;
    }

    // MODIFIES: this
    // EFFECTS: Opens writer, if file cannot be opened, throws FileNotFoundException
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(outputDir);
    }

    // MODIFIES: this
    // EFFECTS: Writes JSON representation of the AppState
    public void write(AppState appState) {
        JSONObject jsonObject = appState.toJson();
        saveToFile(jsonObject.toString(2));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
