package ru.eltech.business;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import ru.eltech.mongo.Mongo;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

public class FindActionsByTime {
    public static void main(String[] args) {
        StatUtil.findActionsByTime(false);
    }
}
