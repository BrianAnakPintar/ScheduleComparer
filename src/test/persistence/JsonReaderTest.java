package persistence;

import model.AppState;
import model.Course;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


/*

Code modified from JsonSerializationDemo project:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

 */

// Test class for JsonReaderTest
public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AppState appState = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAppState() {
        String DATA_DIR = "./data/testReaderEmptyAppState.json";
        JsonReader reader = new JsonReader(DATA_DIR);
        try {
            AppState appState = reader.read();
            assertEquals(0, appState.getCourses().size());
            assertEquals(0, appState.getAccounts().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAppState() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAppState.json");
        try {
            AppState appState = reader.read();

            Map<String, User> accounts = appState.getAccounts();
            assertEquals(2, accounts.size());
            assertTrue(accounts.containsKey("batman"));
            assertTrue(accounts.containsKey("brian"));

            Map<String, Course> courses = appState.getCourses();
            assertEquals(3, appState.getCourses().size());
            assertTrue(courses.containsKey("CPSC 210"));
            assertTrue(courses.containsKey("CPSC 221"));
            assertTrue(courses.containsKey("MATH 101"));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAppStateCheckDetails() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAppState.json");
        try {
            AppState appState = reader.read();

            Map<String, User> accounts = appState.getAccounts();
            Course c1 = accounts.get("brian").getWeekSchedule().get(0).getCourse(1500);
            assertEquals("CPSC 210", c1.getName());
            assertEquals("ESB 1013", c1.getLocation());

            Map<String, Course> courses = appState.getCourses();
            assertEquals("CPSC 210", courses.get("CPSC 210").getName());
            assertEquals("ESB 1013", courses.get("CPSC 210").getLocation());

            assertEquals("CPSC 221", courses.get("CPSC 221").getName());
            assertEquals("MATHX 100", courses.get("CPSC 221").getLocation());


            assertEquals("MATH 101", courses.get("MATH 101").getName());
            assertEquals("CIRS 1250", courses.get("MATH 101").getLocation());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralErrorAppState() {
        // This test is made for when the json file has an invalid number in it
        JsonReader reader = new JsonReader("./data/testReaderGeneralErrorAppState.json");
        try {
            AppState appState = reader.read();

            Map<String, User> accounts = appState.getAccounts();
            assertEquals(1, accounts.size());
            assertTrue(accounts.containsKey("brian"));
            assertNull(accounts.get("brian").getWeekSchedule().get(0).getCourse(1500));
            assertNull(accounts.get("brian").getWeekSchedule().get(1).getCourse(800));


            Map<String, Course> courses = appState.getCourses();
            assertEquals(1, appState.getCourses().size());
            assertTrue(courses.containsKey("CPSC 210"));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
