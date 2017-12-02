package ru.eltech.www;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.julienvey.trello.domain.TList;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import org.bson.Document;
import ru.eltech.data.GoogleCalendarEvent;

import java.util.ArrayList;
import java.util.List;


public class AddEventsToMongo {

    public static void main(String args[]) {
        MongoClient mongo = new MongoClient("localhost", 27017);

        MongoDatabase database = mongo.getDatabase("myDb");

        MongoCollection<Document> collection = database.getCollection("events");

        GoogleCalendarEventsPicker googleCalendarEventsPicker = new GoogleCalendarEventsPicker();
        try {
            List<Event> allEvents = googleCalendarEventsPicker.getAllEvents();

            for (Event e : allEvents) {
                Document doc = new GoogleCalendarEvent(e).getBson();

                System.out.println(doc.toJson());

                collection.insertOne(doc);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}