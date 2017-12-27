package ru.eltech.www;

import com.google.api.services.calendar.model.Event;
import com.julienvey.trello.domain.Action;
import com.mongodb.client.MongoCollection;
import java.util.List;
import org.bson.Document;
import ru.eltech.data.GoogleCalendarEvent;
import ru.eltech.data.TrelloEvent;
import ru.eltech.mongo.Mongo;


public class MongoDAO {

    public static void addEvents() {
        Mongo.drop();

        try {
            MongoCollection<Document> gCalendarCollection = Mongo.getGCalendarCollection();

            GoogleCalendarEventsPicker googleCalendarEventsPicker = new GoogleCalendarEventsPicker();

            List<Event> allEvents = googleCalendarEventsPicker.getAllEvents();

            for (Event e : allEvents) {
                Document doc = new GoogleCalendarEvent(e).getDocument();
                System.out.println(doc.toJson());
                gCalendarCollection.insertOne(doc);
            }

            MongoCollection<Document> trelloCollection = Mongo.getTrelloCollection();

            TrelloEventsPicker trelloEventsPicker = new TrelloEventsPicker();

            for (Action action : trelloEventsPicker.getAllActions()) {
                Document doc = new TrelloEvent(action).getDocument();
                System.out.println(doc.toJson());
                trelloCollection.insertOne(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}