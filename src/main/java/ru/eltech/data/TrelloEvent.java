package ru.eltech.data;

import com.julienvey.trello.domain.Action;
import org.bson.Document;

import java.util.Date;

public class TrelloEvent {
    private final String id;
    private final Date time;
    private final String type;


    public TrelloEvent(String id, Date time) {
        this.id = id;
        this.time = time;
        this.type = "";
    }

    public TrelloEvent(Action action) {
        this.id = action.getId();
        this.type = action.getType();
        this.time = action.getDate();
    }

    public Document getDocument() {
        return new Document("id", id)
                .append("type", type)
                .append("time", time);
    }
}
