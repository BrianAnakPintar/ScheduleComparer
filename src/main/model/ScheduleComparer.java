package model;

import java.util.ArrayList;

// Class that represents a comparison between two users
public class ScheduleComparer {
    private final Day dayOne;
    private final Day dayTwo;

    // EFFECTS: Constructor for ScheduleComparer
    public ScheduleComparer(Day dayOne, Day dayTwo) {
        this.dayOne = dayOne;
        this.dayTwo = dayTwo;
    }

    // EFFECTS: Compares dayOne and dayTwo to find where both slots are empty, then returns a list
    //          of all the overlapping free time slots.
    public ArrayList<Integer> findFree() {
        ArrayList<Integer> freeTime = new ArrayList<>();
        for (int i = 800; i <= 2000; i += 30) {
            if (i % 100 == 60) {
                i += 10;
                continue;
            }
            if ((dayOne.getCourse(i) == null) && (dayTwo.getCourse(i) == null)) {
                freeTime.add(i);
            }
        }
        return freeTime;
    }

    // EFFECTS: Compares dayOne and dayTwo to find where both slots are the same course and at the same time,
    //          then returns a list of the name of courses where the criteria were met.
    public ArrayList<String> findSimilar() {
        ArrayList<String> sameCourse = new ArrayList<>();
        for (int i = 800; i <= 2000; i += 30) {
            if (i % 100 == 60) {
                i += 10;
                continue;
            }
            if ((dayOne.getCourse(i) == dayTwo.getCourse(i)) && (dayOne.getCourse(i) != null)) {
                sameCourse.add(dayOne.getCourse(i).getName() + " at " + formatTime(i));
            }
        }
        return sameCourse;
    }

    // REQUIRES: 800 <= time <= 2000
    // EFFECTS: Formats time as a string (e.g. 800 becomes 08:00)
    private String formatTime(int time) {
        String formatted;
        if (time / 100 < 10) {
            formatted = "0" + (time / 100) + ":";
        } else {
            formatted = (time / 100) + ":";
        }
        if ((time % 100) == 0) {
            formatted += "00";
        } else {
            formatted += (time % 100);
        }

        return formatted;
    }

    public Day getDayOne() {
        return dayOne;
    }

    public Day getDayTwo() {
        return dayTwo;
    }
}
