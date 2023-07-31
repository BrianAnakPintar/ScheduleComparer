# Meetup Scheduler

---

### *What* will the application do?
The application will look at multiple schedule listed by the user. 
Then it will proceed to list available free time slots within the schedule

### *Who* will use it?
The application is meant to be used for students to see when they are able to meet with their friends.
Moreover, this app is especially useful for classes when there are group assignments. The app would allow group members
to compare their schedule with fellow group members and see when they have time to meet outside of class to work on
their project

### *Why* is this project of interest to you?
Currently, I am taking multiple courses that are group based, and in most of these courses my group has a hard time
deciding when to meet up. In some cases, we had to meet up late at night because it was too complicated to find a
common timeslot where all group members are free.

## User Stories

---

- As a user, I want to be able to add a course to a specified day in my schedule
- As a user, I want to be able to view the schedules that has been added
- As a user, I want to be able to delete a course from my schedule
- As a user, I want to be able to see if there are free overlapping time slots with my friends
- As a user, I want to be able to save my schedule
- As a user, I want to be able to load my schedule
- As a user, I want to be able to load my friends' schedule
- As a user, I want to be able to be reminded to save my schedule when I quit.
- As a user, I want the option to load the appState when I start the application.

## Instructions for Grader

---

- You can add courses to the schedule by going to the schedule menu and click add course
- You can remove courses from the schedule by going to the schedule menu and click remove course
- You can locate my visual component when you log-in to the application.
- You can load the state of my application when you start the application.
- You can save the state of my application by trying to exit the application from the MainUI

## Phase 4: Task 2

Tue Apr 11 23:21:28 PDT 2023
Added CPSC210 at: Monday 1030

Tue Apr 11 23:21:28 PDT 2023
Added CPSC210 at: Monday 930

Tue Apr 11 23:21:28 PDT 2023
Added CPSC 121 at: Tuesday 1200

Tue Apr 11 23:21:28 PDT 2023
Added CPSC 210 at: Thursday 800

Tue Apr 11 23:21:29 PDT 2023
Logged in to: brian

Tue Apr 11 23:22:08 PDT 2023
Created new course

Tue Apr 11 23:22:12 PDT 2023
Added PHYS131 at: Wednesday 1200

Tue Apr 11 23:22:22 PDT 2023
Added PHYS131 at: Wednesday 1130

Tue Apr 11 23:22:35 PDT 2023
Created new course

Tue Apr 11 23:22:38 PDT 2023
Added CLOWN at: Monday 1530

Tue Apr 11 23:22:59 PDT 2023
Removed course at: Monday 1530

Tue Apr 11 23:23:05 PDT 2023
Removed course

Tue Apr 11 23:23:09 PDT 2023
Logged out

## PHASE 4: Task 3
Based on the UML Diagram, I can see that my AppState class is used a lot by my UI Elements. Firstly, I think the best
thing to do is to turn my AppState class into a Singleton as there is no need to have multiple AppStates. Moreover,
I think creating a parent class for my UI would be beneficial. I think creating parent classes which then are extended
upon by my UI classes would help make the overall code structure cleaner. Moreover, while it is not necessarily shown
in the UML Diagram, the UI designer and ScheduleComparer both act very similar to a singleton. Therefore, refactoring
them into a singleton would be better as only 1 instance of them are required. A huge issue with my current design is
how much coupling there are, as each class are dependent on another. For example User contains days which contains
course. Moreover, most of my classes especially the UIs depend on the UIDesigner which I made to reduce me copy
and pasting code, this increases coupling as many UI classes depend on the UIDesigner, but it does keep it organized as
if there is a broken code, I only need to fix it once. But it does mean if 1 thing breaks in the class then everything
also breaks.