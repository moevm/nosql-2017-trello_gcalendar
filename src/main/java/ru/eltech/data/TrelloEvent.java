package ru.eltech.data;

import com.julienvey.trello.domain.Action;
import org.bson.Document;

import java.util.Date;

public class TrelloEvent {
    private final String id;
    private final Date time;
    private final String type;
    private final boolean isClosing;


    public TrelloEvent(String id, Date time) {
        this.id = id;
        this.time = time;
        this.type = "";
        this.isClosing = false;
    }

    public TrelloEvent(Action action) {
        this.id = action.getId();
        this.type = action.getType();
        this.time = action.getDate();
        if ("updateCard".equals(this.type)) {
            Action.Data data = action.getData();
            this.isClosing = data.getCard().isClosed() && !data.getOld().isClosed();
        } else {
            this.isClosing = false;
        }
    }

    public TrelloEvent(Document d) {
        this.id = d.getString("id");
        this.type = d.getString("type");
        this.time = d.getDate("time");
        this.isClosing = d.getBoolean("isClosing");
    }

    public Document getDocument() {
        return new Document("id", id)
                .append("type", type)
                .append("time", time)
                .append("isClosing", isClosing);
    }

    public Date getDate() {
        return time;
    }
}
