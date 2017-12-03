package ru.eltech.www;

import com.google.api.services.calendar.model.Event;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.eltech.data.GoogleCalendarEvent;
import ru.eltech.mongo.Mongo;

import java.util.List;


public class AddEventsToMongo {

    public static void main(String args[]) {
        MongoDatabase database = Mongo.getDatabase();

        MongoCollection<Document> collection = database.getCollection("events");

        GoogleCalendarEventsPicker googleCalendarEventsPicker = new GoogleCalendarEventsPicker();
        try {
            List<Event> allEvents = googleCalendarEventsPicker.getAllEvents();

            for (Event e : allEvents) {
                Document doc = new GoogleCalendarEvent(e).getDocument();

                System.out.println(doc.toJson());

                collection.insertOne(doc);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}