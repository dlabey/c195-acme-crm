# ACME CRM

This is the project for Software II - Advanced Java Concepts – C195. This
project is basically a CRM. This application is titled ACME CRM and is for a
company that has multiple offices that need to manage customers and their
appointments. Time is military time. Dates and time are put into the database in
UTC and displayed locally. Notable code is commented.

## Maven Install

```mvn clean install```

## Maven Native Installer Creator

```mvn jfx:native```

## Office Locations

* Phoenix, Arizona
* New York, New York
* London, England

## Languages (on Login)

* English
* Spanish

## Business Hours

Business hours are from 0900-1700, appointments can span more than one day but
cannot go outside of these hours. If an appointment spans more than 1 day it
means the customer will be in the office for that period of time within
business hours.

## Dates & Time

A user transparently sees times in their own time zone and the dates and time
are put into the database into UTC. When they come out of the database they are
adjusted automatically at the entity layer into the user's local time zone. If
an appointment is in another time zone (because the times are outside of
business hours for the user viewing them), it is suggested that the user
communicate with the other user so the other use can make appropriate
adjustments in their time zone.

## Reminders

A reminder is created and updated when an appointment is created and updated. It is created and set for 15 minutes before the appointment. On login and appointment creation and updating it will be scheduled/rescheduled for display 15 minutes before the appointment transparently conforming to the user's time zone.

## Auditing

Logins are stored at the top-level directory of the project in logins.txt with a UTC timestamp.