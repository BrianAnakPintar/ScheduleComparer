package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Class test for User
public class UserTest {
    User u1;
    User u2;

    @BeforeEach
    public void setup() {
        u1 = new User("Brian");
        u2 = new User("Batman");
    }

    @Test
    public void testConstructor() {
        assertEquals("Brian", u1.getName());
        assertEquals("Batman", u2.getName());
        assertEquals(7, u1.getWeekSchedule().size());
        assertEquals(7, u1.getWeekSchedule().size());
    }
}
