package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e;
	private Date d;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
	@BeforeEach
	public void runBefore() {
		e = new Event("Sensor open at door");   // (1)
		d = Calendar.getInstance().getTime();   // (2)
	}
	
	@Test
	public void testEvent() {
		assertEquals("Sensor open at door", e.getDescription());
		assertEquals(d.toString(), e.getDate().toString());
	}

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Sensor open at door", e.toString());
	}

    @Test
    public void testEquals() {
        assertEquals(e, e);
        assertEquals(new Event("E1"), new Event("E1"));
        assertNotEquals(e, new Event("Other event"));
        assertFalse(e.equals(null));
        assertNotEquals(e, "hi");
    }

    @Test
    public void testHashcode() {
        int h1 = new Event("E1").hashCode();
        int h2 = new Event("E1").hashCode();
        int h3 = e.hashCode();
        assertNotEquals(h1, h3);
        assertEquals(h1, h2);

    }

}
