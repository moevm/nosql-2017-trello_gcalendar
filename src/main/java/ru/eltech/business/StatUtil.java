package ru.eltech.business;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.joda.time.DateTime;
import ru.eltech.data.TrelloEvent;
import ru.eltech.mongo.Mongo;

import java.util.*;

public class StatUtil {

    public static void findOverworkByDay(boolean isClosing) {
        MongoDatabase database = Mongo.getDatabase();

        MongoCollection<Document> gcalendar = database.getCollection("gcalendar");

        Map<Integer, Integer> overtimeStatisticsForEachDayMap = new HashMap<>();

        FindIterable<Document> documents;
        if (isClosing) {
            Document query = new Document("isClosing", true);
            documents = database.getCollection("trello").find(query);
        } else {
            documents = database.getCollection("trello").find();
        }

        for (Document d : documents) {
            Date actionDate = new TrelloEvent(d).getDate();
            if (isOverwork(gcalendar, actionDate)) {
                int dayOfWeek = new DateTime(actionDate).getDayOfWeek();
                int numberOfOverworkings = overtimeStatisticsForEachDayMap
                        .getOrDefault(dayOfWeek, 0);

                overtimeStatisticsForEachDayMap.put(dayOfWeek, numberOfOverworkings + 1);
            }
        }

        String[] daysOfWeek = new String[] {
                "Понедельник",
                "Вторник",
                "Среда",
                "Четверг",
                "Пятница",
                "Суббота",
                "Воскресение"};

        System.out.println();
        for (int i = 0; i < 7; i++) {
            Integer value = overtimeStatisticsForEachDayMap.getOrDefault(i + 1, 0);
            System.out.println("Переработка в " + daysOfWeek[i] + ": " + value);

        }
    }

    private static boolean isOverwork(MongoCollection<Document> collection, Date date) {
        FindIterable<Document> documents = collection.find(
                new Document("start", new Document("$lte", date))
                        .append("end", new Document("$gte", date))
        );
        Document first = documents.first();
        return first == null;
    }

    public static void findActionsByTime(boolean isClosing) {
        MongoCollection<Document> collection = Mongo.getTrelloCollection();

        Document groupOp = new Document("$group", new Document("_id",
                new Document("hour",
                        new Document("$hour", "$time")))
                .append("count",
                        new Document("$sum", 1)));

        AggregateIterable<Document> aggregate;

        if (isClosing) {
            aggregate = collection.aggregate(Arrays.asList(
                    new Document("$match",
                            new Document("isClosing", true)),
                    groupOp));
        } else {
            aggregate = collection.aggregate(Collections.singletonList(groupOp));
        }


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
