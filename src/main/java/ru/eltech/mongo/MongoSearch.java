package ru.eltech.mongo;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Collections;

public class MongoSearch {

    public static void main(String[] args) {
        MongoCollection<Document> collection = Mongo.getTrelloCollection();

        for (Document d : collection.find()) {
            System.out.println(d.toJson());
        }

        AggregateIterable<Document> aggregate = collection.aggregate(Collections.singletonList(
                new Document("$group", new Document("_id",
                        new Document("day",
                                new Document("$dayOfWeek", "$time")))
                        .append("count",
                                new Document("$sum", 1)))));

        for (Document d : aggregate) {
            System.out.println(d.toJson());
        }


    }
}
