package persistence;

import exceptions.InvalidCourseTimeException;
import model.AppState;
import model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*

Code modified from JsonSerializationDemo project:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

 */


// Test class for JsonWriter
public class JsonWriterTest {

    private Course cs210;

    @BeforeEach
    void setup() {
        cs210 = new Course("CPSC 210", "ESB 1013");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            AppState appState = new AppState();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            AppState appState = new AppState();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAppState.json");
            writer.open();
            writer.write(appState);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAppState.json");
            appState = reader.read();
            assertEquals(0, appState.getCourses().size());
            assertEquals(0, appState.getAccounts().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            AppState appState = new AppState();
            appState.signUp("brian");
            appState.getCourses().put("CPSC 210", cs210);
            appState.getAccounts().get("brian").getWeekSchedule().get(0).addCourse(800, cs210);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAppState.json");
            writer.open();
            writer.write(appState);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAppState.json");
            appState = reader.read();
            assertEquals(1, appState.getCourses().size());
            assertEquals(1, appState.getAccounts().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (InvalidCourseTimeException e) {
            fail("Error in adding course test");
        }
    }
}
