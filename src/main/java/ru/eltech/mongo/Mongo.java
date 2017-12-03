package ru.eltech.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

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
}
