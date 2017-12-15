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
        StatUtil.findOverworkByDay(false);
    }

}
