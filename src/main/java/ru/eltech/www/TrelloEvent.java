package ru.eltech.www;

import org.bson.Document;

public class TrelloEvent {
    String id;
    long time;

    TrelloEvent(String id, long time) {

    }

    public Document getBson() {
        return new Document("id", id)
                .append("time", time);
    }
}
