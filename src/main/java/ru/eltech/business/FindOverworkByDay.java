package ru.eltech.business;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.joda.time.DateTime;
import ru.eltech.data.TrelloEvent;
import ru.eltech.mongo.Mongo;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FindOverworkByDay {


    public static void main(String[] args) {
        MongoDatabase database = Mongo.getDatabase();

        MongoCollection<Document> gcalendar = database.getCollection("gcalendar");

        Map<Integer, Integer> overtimeStatisticsForEachDayMap = new HashMap<>();


        for (Document d : database.getCollection("trello").find()) {
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
}
