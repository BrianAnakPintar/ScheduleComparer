package model;

import exceptions.InvalidCourseTimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Test class for ScheduleComparer
public class ScheduleComparerTest {

    ScheduleComparer emptyComparer;
    ScheduleComparer normalComparer;
    ScheduleComparer fullComparer;

    Course c1;
    Course c2;
    Day normalDay1;
    Day normalDay2;
    Day fullDay1;
    Day fullDay2;

    @BeforeEach
    public void setup() {
        c1 = new Course("CPSC 210", "ESB 1013");
        c2 = new Course("CPSC 221", "MATH 100");

        normalDay1 = new Day("Monday");
        normalDay2 = new Day("Tuesday");
        fullDay1 = new Day("Schedule day 1");
        fullDay2 = new Day("Schedule day 2");

        emptyComparer = new ScheduleComparer(new Day("Monday"), new Day("Tuesday"));
    }

    @Test
    public void testConstructor() {
        try {
            normalDay1.addCourse(800, c1);
            normalDay1.addCourse(900, c2);
            normalDay2.addCourse(800, c1);
            normalDay2.addCourse(830, c2);
        } catch (InvalidCourseTimeException e) {
            fail("InvalidCourseTimeException Occured");
        }
        normalComparer = new ScheduleComparer(normalDay1, normalDay2);
        assertEquals(normalDay1, normalComparer.getDayOne());
        assertEquals(normalDay2, normalComparer.getDayTwo());
    }

    @Test
    public void testCompareFreeTimeEmpty() {
        ArrayList<Integer> free = new ArrayList<>();
        for (int i = 800; i <= 2000; i += 30) {
            if (i % 100 == 60) {
                i += 10;
                continue;
            }
            free.add(i);
        }
        assertEquals(free, emptyComparer.findFree());
    }

    @Test
    public void testCompareFreeTimeNormal() {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 930; i <= 2000; i += 30) {
            if (i % 100 == 60) {
                i += 10;
                continue;
            }
            ans.add(i);
        }
        try {
            normalDay1.addCourse(800, c1);
            normalDay1.addCourse(900, c2);
            normalDay2.addCourse(800, c1);
            normalDay2.addCourse(830, c2);
        } catch (InvalidCourseTimeException e) {
            fail("InvalidCourseTimeException Occured");
        }
        normalComparer = new ScheduleComparer(normalDay1, normalDay2);
        assertEquals(ans, normalComparer.findFree());
    }

    @Test
    public void testCompareFreeTimeNormalFail() {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 900; i <= 2000; i += 30) {
            if (i % 100 == 60) {
                i += 10;
                continue;
            }
            ans.add(i);
        }
        try {
            normalDay1.addCourse(800, c1);
            normalDay2.addCourse(800, c1);
            normalDay2.addCourse(830, c2);
            normalDay1.addCourse(901, c2);
            fail("Error, exception did not occur");
        } catch (InvalidCourseTimeException e) {
            // Ok
        }
        normalComparer = new ScheduleComparer(normalDay1, normalDay2);
        assertEquals(ans, normalComparer.findFree());
    }

    @Test
    public void testCompareFreeTimeFull() {
        try {
            for (int i = 800; i <= 2000; i += 30) {
                // Since time is base 60, this code makes it so that if it's 8:60 it becomes 8:70 and goes to the next
                // iteration meaning 870 + 30 = 9:00 which is what we want.
                if (i % 100 == 60) {
                    i += 10;
                    continue;
                }
                fullDay1.addCourse(i, c1);
                fullDay2.addCourse(i, c1);
            }
        } catch (InvalidCourseTimeException e) {
            fail("InvalidCourseTimeException Occured");
        }
        fullComparer = new ScheduleComparer(fullDay1, fullDay2);
        assertEquals(new ArrayList<>(), fullComparer.findFree());
    }

    @Test
    public void testCompareFreeTimeFullError() {
        try {
            for (int i = 800; i <= 2030; i += 30) {
                if (i % 100 == 60) {
                    i += 10;
                    continue;
                }
                fullDay1.addCourse(i, c1);
                fullDay2.addCourse(i, c1);
            }
            fail("Exception should occur.");
        } catch (InvalidCourseTimeException e) {
            fullComparer = new ScheduleComparer(fullDay1, fullDay2);
        }
        assertEquals(new ArrayList<>(), fullComparer.findFree());
    }

    @Test
    public void testFindSimilarEmpty() {
        assertEquals(new ArrayList<>(), emptyComparer.findSimilar());
    }

    @Test
    public void testFindSimilarNormal() {
        ArrayList<String> normal = new ArrayList<>();
        normal.add(c1.getName() + " at 08:00");

        try {
            normalDay1.addCourse(800, c1);
            normalDay1.addCourse(900, c2);
            normalDay2.addCourse(800, c1);
            normalDay2.addCourse(830, c2);
        } catch (InvalidCourseTimeException e) {
            fail("InvalidCourseTimeException Occured");
        }
        normalComparer = new ScheduleComparer(normalDay1, normalDay2);
        assertEquals(normal, normalComparer.findSimilar());
    }

    @Test
    public void testFindSimilarNormalFail() {
        ArrayList<String> normal = new ArrayList<>();
        normal.add(c1.getName() + " at 08:00");

        try {
            normalDay1.addCourse(800, c1);
            normalDay2.addCourse(800, c1);
            normalDay1.addCourse(845, c2);
            fail("Error, Exception did not occur");
        } catch (InvalidCourseTimeException e) {
            // Ok
        }
        normalComparer = new ScheduleComparer(normalDay1, normalDay2);
        assertEquals(normal, normalComparer.findSimilar());
    }

    @Test
    public void testFindSimilarFull() {
        ArrayList<String> full = new ArrayList<>();
        for (int i = 8; i <= 20; i++) {
            if (i < 10) {
                full.add(c1.getName() + " at 0" + i + ":00");
                full.add(c1.getName() + " at 0" + i + ":30");
            } else if (i == 20) {
                full.add(c1.getName() + " at " + i + ":00");
            } else {
                full.add(c1.getName() + " at " + i + ":00");
                full.add(c1.getName() + " at " + i + ":30");
            }
        }
        try {
            for (int i = 800; i <= 2000; i += 30) {
                if (i % 100 == 60) {
                    i += 10;
                    continue;
                }
                fullDay1.addCourse(i, c1);
                fullDay2.addCourse(i, c1);
            }
        } catch (InvalidCourseTimeException e) {
            fail("InvalidCourseTimeException Occured");
        }
        fullComparer = new ScheduleComparer(fullDay1, fullDay2);
        assertEquals(full, fullComparer.findSimilar());
    }

    @Test
    public void testFindSimilarFullFail() {
        ArrayList<String> full = new ArrayList<>();
        for (int i = 8; i <= 20; i++) {
            if (i < 10) {
                full.add(c1.getName() + " at 0" + i + ":00");
                full.add(c1.getName() + " at 0" + i + ":30");
            } else if (i == 20) {
                full.add(c1.getName() + " at " + i + ":00");
            } else {
                full.add(c1.getName() + " at " + i + ":00");
                full.add(c1.getName() + " at " + i + ":30");
            }
        }
        try {
            for (int i = 800; i <= 2030; i += 30) {
                if (i % 100 == 60) {
                    i += 10;
                    continue;
                }
                fullDay1.addCourse(i, c1);
                fullDay2.addCourse(i, c1);
            }
            fail("Exception expected.");
        } catch (InvalidCourseTimeException e) {
            // Ok
        }
        fullComparer = new ScheduleComparer(fullDay1, fullDay2);
        assertEquals(new ArrayList<>(), fullComparer.findFree());
    }
}

