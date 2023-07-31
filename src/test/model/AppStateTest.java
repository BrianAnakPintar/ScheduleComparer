package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for AppState
public class AppStateTest {
    AppState emptyAppState;
    AppState generalAppState;

    @BeforeEach
    void setup() {
        emptyAppState = new AppState();
    }

    @Test
    void testEmptyAppState() {
        assertEquals(0, emptyAppState.getAccounts().size());
        assertEquals(0, emptyAppState.getCourses().size());
        assertNull(emptyAppState.getUser());
    }

    @Test
    void testSignUp() {
        emptyAppState.signUp("brian");
        assertEquals(1, emptyAppState.getAccounts().size());
        assertTrue(emptyAppState.getAccounts().containsKey("brian"));
        assertEquals("brian", emptyAppState.getUser().getName());

        emptyAppState.signUp("batman");
        assertEquals(2, emptyAppState.getAccounts().size());
        assertTrue(emptyAppState.getAccounts().containsKey("batman"));
        assertEquals("batman", emptyAppState.getUser().getName());
    }

    @Test
    void testDuplicateRegister() {
        emptyAppState.signUp("brian");
        emptyAppState.isRegisteredUser("brian");

        emptyAppState.signUp("brian");
        assertEquals(1, emptyAppState.getAccounts().size());
        assertEquals("brian", emptyAppState.getUser().getName());
    }


    @Test
    void testLogIn() {
        emptyAppState.signUp("brian");
        emptyAppState.signUp("batman");
        emptyAppState.logIn("brian");
        assertEquals("brian", emptyAppState.getUser().getName());
    }

    @Test
    void testLogOut() {
        emptyAppState.signUp("brian");
        emptyAppState.logout();
        assertEquals(null, emptyAppState.getUser());
    }

    @Test
    void testCreateCourse() {
        Course c1 = new Course("CPSC 210", "ESB 1013");
        emptyAppState.createCourse(c1);
        assertEquals(1, emptyAppState.getCourses().size());
        assertTrue(emptyAppState.getCourses().containsKey(c1.getName()));
    }

    @Test
    void testRemoveCourse() {
        Course c1 = new Course("CPSC 210", "ESB 1013");
        emptyAppState.createCourse(c1);
        emptyAppState.removeCourse(c1.getName());
        assertEquals(0, emptyAppState.getCourses().size());

    }
}
