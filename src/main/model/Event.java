package model;

import java.util.Calendar;
import java.util.Date;


//Represents a scheduler event.
public class Event {
    private static final int HASH_CONSTANT = 13;
    private final Date dateLogged;
    private final String description;

    /**
     * Creates an event with the given description
     * and the current date/time stamp.
     *
     * @param description a description of the event
     */
    // MODIFIES: this
    // EFFECTS: Creates an event with the given description and the current date/time stamp.
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    /**
     * Gets the date of this event (includes time).
     *
     * @return the date of the event
     */
    // EFFECTS: Gets the date of this event (includes time).
    public Date getDate() {
        return dateLogged;
    }

    /**
     * Gets the description of this event.
     *
     * @return the description of the event
     */
    // EFFECTS: Gets the description of this event.
    public String getDescription() {
        return description;
    }

    @Override
    // EFFECTS: Checks if the two things are equal, and return true if they are
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged)
                && this.description.equals(otherEvent.description));
    }

    @Override
    // EFFECTS: Returns a hashcode of the object.
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    @Override
    // EFFECTS: Turns the Event to a string.
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
