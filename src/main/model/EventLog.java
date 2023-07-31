package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a log of scheduler events.
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 */
public class EventLog implements Iterable<Event> {
    /**
     * the only EventLog in the system (Singleton Design Pattern)
     */
    private static EventLog theLog;
    private final Collection<Event> events;

    /**
     * Prevent external construction.
     * (Singleton Design Pattern).
     */
    // MODIFIES: this
    // EFFECTS: Private constructor which prevents it from getting instantiated outside of this class.
    private EventLog() {
        events = new ArrayList<Event>();
    }

    /**
     * Gets instance of EventLog - creates it
     * if it doesn't already exist.
     * (Singleton Design Pattern)
     *
     * @return instance of EventLog
     */
    // MODIFIES: this
    // EFFECTS: Returns theLog instance, creates a new one if an instance was not created.
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    /**
     * Adds an event to the event log.
     *
     * @param e the event to be added
     */
    // MODIFIES: this
    // EFFECTS: Adds event to the event log
    public void logEvent(Event e) {
        events.add(e);
    }

    /**
     * Clears the event log and logs the event.
     */
    // MODIFIES: this
    // EFFECTS: Clears the event log and logs the event
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    // EFFECTS: Returns the iterator for events
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
