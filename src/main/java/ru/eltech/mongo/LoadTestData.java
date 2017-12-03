package ru.eltech.mongo;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.joda.time.DateTime;
import ru.eltech.data.GoogleCalendarEvent;
import ru.eltech.data.TrelloEvent;

import java.util.ArrayList;
import java.util.List;

public class LoadTestData {

    private static DateTime getFirstDay() {
        return new DateTime(2017, 11, 27, 0, 0);
    }

    public static List<GoogleCalendarEvent> getGoogleCallendarEvents() {
        List<GoogleCalendarEvent> result = new ArrayList<>();
        DateTime date = getFirstDay();
        for (int i = 0; i < 5; i++) {
            result.add(new GoogleCalendarEvent(
                    "d" + i,
                    date.withHourOfDay(9).toDate(),
                    date.withHourOfDay(5).toDate()));
            date = date.plusDays(1);
        }
        return result;
    }



    static List<TrelloEvent> getTrelloEvents() {
        List<TrelloEvent> result = new ArrayList<>();
        DateTime date = getFirstDay();
        for (int i = 0; i < 5; i++) {
            result.add(new TrelloEvent(
                    "d" + i,
                    date.withHourOfDay(12).toDate()));
            date = date.plusDays(1);
        }
        return result;
    }

    public static void main(String[] args) {
        MongoCollection<Document> gCalendarCollection = Mongo.getGCalendarCollection();
        gCalendarCollection.drop();
        for (GoogleCalendarEvent e: getGoogleCallendarEvents()) {
            gCalendarCollection.insertOne(e.getDocument());
        }
        MongoCollection<Document> trelloCollection = Mongo.getTrelloCollection();
        trelloCollection.drop();
        for (TrelloEvent e: getTrelloEvents()) {
            gCalendarCollection.insertOne(e.getDocument());
        }
    }
}
