package ru.eltech.data;

import org.bson.Document;

import java.util.Date;

public class TrelloEvent {
    private String id;
    private Date time;

    public TrelloEvent(String id, Date time) {
        this.id = id;
        this.time = time;
    }

    public Document getDocument() {
        return new Document("id", id)
                .append("time", time);
    }
}
