package ru.eltech.www;

import static org.junit.Assert.*;

import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.Event;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

public class GoogleCalendarEventsPickerTest {

    GoogleCalendarEventsPicker googleCalendarEventsPicker;

    @Before
    public void setUp() {
        googleCalendarEventsPicker = new GoogleCalendarEventsPicker();
    }

    @Test
    public void getAllCalendars() throws Exception {
        CalendarList calendarList = googleCalendarEventsPicker.getAllCalendars();

        Assert.notNull(calendarList, "Calendars list cannot be equal null!");
        Assert.notEmpty(calendarList, "There should be at least one calendar!");
    }

    @Test
    public void getAllEvents() throws Exception {
        List<Event> eventList = googleCalendarEventsPicker.getAllEvents();

        Assert.notNull(eventList, "Event list cannot be equal null!");
        Assert.notEmpty(eventList, "There should be at least one event!");
    }

}