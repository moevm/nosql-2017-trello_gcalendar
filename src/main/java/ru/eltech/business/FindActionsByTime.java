package ru.eltech.business;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import ru.eltech.mongo.Mongo;

import java.util.Collections;

public class FindActionsByTime {
    public static void main(String[] args) {
        MongoCollection<Document> collection = Mongo.getTrelloCollection();

        AggregateIterable<Document> aggregate = collection.aggregate(Collections.singletonList(
                new Document("$group", new Document("_id",
                        new Document("hour",
                                new Document("$hour", "$time")))
                        .append("count",
                                new Document("$sum", 1)))));


        int first = 0;
        int second = 0;

        for (Document d : aggregate) {
            Integer hour = ((Document) d.get("_id")).getInteger("hour");
            Integer count = d.getInteger("count");

            if (hour < 12) {
                first += count;
            } else {
                second += count;
            }

        }

        System.out.println();
        System.out.println("Действий в первой половине дня: " + first);
        System.out.println("Действий во второй половине дня: " + second);
    }
}
