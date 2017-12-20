package ru.eltech.mongo;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;

public class MongoStat {
    public static void main(String[] args) {
        JsonWriterSettings settings = JsonWriterSettings.builder().indent(true).build();
        System.out.println(Mongo.getDatabase().runCommand(new Document("collStats", "trello")).toJson(settings));
        System.out.println("---------------------------------------------------");
        System.out.println(Mongo.getDatabase().runCommand(new Document("collStats", "gcalendar")).toJson(settings));
    }
}
