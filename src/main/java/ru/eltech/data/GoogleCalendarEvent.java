package ru.eltech.data;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.bson.Document;

import java.util.Date;

public class GoogleCalendarEvent {
    private String id;
    private String description;

    private Date start;
    private Date end;

    public GoogleCalendarEvent(String id, Date start, Date end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public GoogleCalendarEvent(Event e) {
        EventDateTime start = e.getStart();
        EventDateTime end = e.getEnd();

        this.id = e.getId();
        this.description = e.getDescription();
        this.start = new Date(start.getDateTime().getValue());
        this.end = new Date(end.getDateTime().getValue());
    }

    public Document getDocument() {
        return new Document("id", id)
                .append("start", start)
                .append("end", end);
    }

}
