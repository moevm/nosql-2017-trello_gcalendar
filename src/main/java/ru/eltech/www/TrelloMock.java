package ru.eltech.www;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class TrelloMock {

    static List<TrelloEvent> getTreeEvents() {
        long millis = Instant.now().toEpochMilli();

        return Arrays.asList(
                new TrelloEvent("e1", millis - 100000),
                new TrelloEvent("e2", millis - 10000),
                new TrelloEvent("e3", millis)
        );
    }



}
