package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for courses
class CourseTest {
    Course c1 = new Course("CPSC 210", "ESB 1013");
    Course c2 = new Course("CPSC 221", "MATH 100");

    @BeforeEach
    public void setup() {
        c1 = new Course("CPSC 210", "ESB 1013");
        c2 = new Course("CPSC 221", "MATH 100");
    }

    @Test
    public void testConstructor() {
        assertEquals("CPSC 210", c1.getName());
        assertEquals("ESB 1013", c1.getLocation());
        assertEquals("CPSC 221", c2.getName());
        assertEquals("MATH 100", c2.getLocation());
    }

    @Test
    public void testSetters() {
        c1.setName("MATH 221");
        assertEquals("MATH 221", c1.getName());
        assertEquals("ESB 1013", c1.getLocation());
        c1.setLocation("LSK 200");
        assertEquals("LSK 200", c1.getLocation());
        c2.setName("WRDS 150B");
        c2.setLocation("SWNG 408");
        assertEquals("WRDS 150B", c2.getName());
        assertEquals("SWNG 408", c2.getLocation());
    }
}