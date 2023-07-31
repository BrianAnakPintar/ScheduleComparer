package model;

import exceptions.InvalidCourseTimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Test class for Day
public class DayTest {
    Day d1 = new Day("Monday");
    Day d2 = new Day("Tuesday");
    Day d3 = new Day("Wednesday");

    Course c1 = new Course("CPSC 210", "ESB 1013");
    Course c2 = new Course("MATH 101", "CIRS 250");

    @BeforeEach
    public void setup() {
        d1 = new Day("Monday");
        d2 = new Day("Tuesday");
        d3 = new Day("Wednesday");
        c1 = new Course("CPSC 210", "ESB 1013");
        c2 = new Course("MATH 101", "CIRS 250");
    }

    @Test
    public void testConstructor() {
        assertEquals("Monday", d1.getDayName());
        assertEquals("Tuesday", d2.getDayName());
        assertEquals("Wednesday", d3.getDayName());
    }

    @Test
    public void testAddCourses() {
        try {
            d1.addCourse(800, c2);
            d1.addCourse(930, c1);
            assertEquals(c2, d1.getCourse(800));
            assertEquals(c1, d1.getCourse(930));

            d2.addCourse(1500, c1);
            d2.addCourse(2000, c2);
            assertEquals(c1, d2.getCourse(1500));
            assertEquals(c2, d2.getCourse(2000));

        } catch (InvalidCourseTimeException e) {
            fail("InvalidCourseTimeException Occured");
        }
    }

    @Test
    public void testAddEmptyCourse() {
        try {
            d1.addCourse(800, null);
        } catch (InvalidCourseTimeException e) {
            fail("InvalidCourseTimeException Occured");
        }
        assertEquals(null, d1.getCourse(800));
    }

    @Test
    public void testFailingAddCourse() {
        try {
            d1.addCourse(801, c1);
            d1.addCourse(2030, c1);
            d1.addCourse(2001, c1);
            d2.addCourse(0, c2);
            fail("Exception expected");
        } catch (InvalidCourseTimeException e) {
            // Ok
        }
        assertNull(d1.getCourse(800));
        assertNull(d1.getCourse(801));

        assertNull(d1.getCourse(2030));
        assertNull(d1.getCourse(2001));
        assertNull(d2.getCourse(0));

    }

    @Test
    public void testPrintDay() {
        String emptyCourse =
                ("Monday schedule: \n" +
                "08:00 Course: -\n" + "08:30 Course: -\n" + "09:00 Course: -\n" + "09:30 Course: -\n" +
                "10:00 Course: -\n" + "10:30 Course: -\n" + "11:00 Course: -\n" + "11:30 Course: -\n" +
                "12:00 Course: -\n" + "12:30 Course: -\n" + "13:00 Course: -\n" + "13:30 Course: -\n" +
                "14:00 Course: -\n" + "14:30 Course: -\n" + "15:00 Course: -\n" + "15:30 Course: -\n" +
                "16:00 Course: -\n" + "16:30 Course: -\n" + "17:00 Course: -\n" + "17:30 Course: -\n" +
                "18:00 Course: -\n" + "18:30 Course: -\n" + "19:00 Course: -\n" + "19:30 Course: -\n" +
                "20:00 Course: -\n");
        assertEquals(emptyCourse, d1.printDay());
    }
    @Test
    public void testPrintDayMixed() {
        String mixed =
                ("Monday schedule: \n" +
                        "08:00 Course: MATH 101\n" + "08:30 Course: MATH 101\n" + "09:00 Course: -\n" +
                        "09:30 Course: -\n" + "10:00 Course: -\n" + "10:30 Course: -\n" + "11:00 Course: -\n" +
                        "11:30 Course: -\n" + "12:00 Course: -\n" + "12:30 Course: -\n" + "13:00 Course: -\n" +
                        "13:30 Course: -\n" + "14:00 Course: -\n" + "14:30 Course: -\n" + "15:00 Course: CPSC 210\n" +
                        "15:30 Course: -\n" + "16:00 Course: -\n" + "16:30 Course: -\n" + "17:00 Course: -\n" +
                        "17:30 Course: -\n" + "18:00 Course: -\n" + "18:30 Course: -\n" + "19:00 Course: -\n" +
                        "19:30 Course: -\n" + "20:00 Course: -\n");
        try {
            d1.addCourse(800, c2);
            d1.addCourse(830, c2);
            d1.addCourse(1500, c1);
        } catch (InvalidCourseTimeException e) {
            fail("InvalidCourseTimeException Occured");
        }
        assertEquals(mixed, d1.printDay());
    }

    @Test
    public void testPrintDayError() {
        String emptyCourse =
                ("Monday schedule: \n" +
                        "08:00 Course: -\n" + "08:30 Course: -\n" + "09:00 Course: -\n" + "09:30 Course: -\n" +
                        "10:00 Course: -\n" + "10:30 Course: -\n" + "11:00 Course: -\n" + "11:30 Course: -\n" +
                        "12:00 Course: -\n" + "12:30 Course: -\n" + "13:00 Course: -\n" + "13:30 Course: -\n" +
                        "14:00 Course: -\n" + "14:30 Course: -\n" + "15:00 Course: -\n" + "15:30 Course: -\n" +
                        "16:00 Course: -\n" + "16:30 Course: -\n" + "17:00 Course: -\n" + "17:30 Course: -\n" +
                        "18:00 Course: -\n" + "18:30 Course: -\n" + "19:00 Course: -\n" + "19:30 Course: -\n" +
                        "20:00 Course: -\n");
        try {
            d1.addCourse(799, c1);
            fail("Exception expected");
        } catch (InvalidCourseTimeException e) {
            // Ok
        }
        assertEquals(emptyCourse, d1.printDay());
    }
}
