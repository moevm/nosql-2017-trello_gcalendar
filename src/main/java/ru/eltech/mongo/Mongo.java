package ru.eltech.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.eltech.data.GoogleCalendarEvent;
import ru.eltech.data.TrelloEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Mongo {
    public static MongoDatabase getDatabase() {
        MongoClient mongo = new MongoClient("localhost", 27017);
        return mongo.getDatabase("trello-gcallendar");
    }

    public static MongoCollection<Document> getTrelloCollection() {
        return getDatabase().getCollection("trello");
    }

    public static MongoCollection<Document> getGCalendarCollection() {
        return getDatabase().getCollection("gcalendar");
    }


    public static void drop() {
        getTrelloCollection().drop();
        getGCalendarCollection().drop();
    }

    public static List<GoogleCalendarEvent> getGoogleCalendarEvents() {
        List<GoogleCalendarEvent> events = new ArrayList<>();
        for (Document d : getGCalendarCollection().find()) {
            events.add(new GoogleCalendarEvent(d));
        }
        return events;
    }

    public static List<TrelloEvent> getTrelloEvents() {
        List<TrelloEvent> events = new ArrayList<>();
        for (Document d : getTrelloCollection().find()) {
            events.add(new TrelloEvent(d));
        }
        return events;
    }

}
