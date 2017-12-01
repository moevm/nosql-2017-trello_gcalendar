package ru.eltech.www;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.julienvey.trello.domain.TList;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class AddEventsToMongo {


    public static Document eventToDocument(Event e) {
        EventDateTime start = e.getStart();
        EventDateTime end = e.getEnd();

        return new Document("id", e.getId())
                .append("description", e.getDescription())
                .append("start", start.getDateTime().getValue())
                .append("end", end.getDateTime().getValue());
    }

    public static void main(String args[]) {

        // Creating a Mongo client
        MongoClient mongo = new MongoClient("localhost", 27017);

        MongoDatabase database = mongo.getDatabase("myDb");

        MongoCollection<Document> collection = database.getCollection("events");


        GoogleCalendarEventsPicker googleCalendarEventsPicker = new GoogleCalendarEventsPicker();
        try {
            List<Event> allEvents = googleCalendarEventsPicker.getAllEvents();

            for (Event e : allEvents) {

                Document doc = eventToDocument(e);

                System.out.println(doc.toJson());

                collection.insertOne(doc);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}