package ru.eltech.mock;

import ru.eltech.data.TrelloEvent;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TrelloMock {

    static List<TrelloEvent> getTrelloEvents() {

        return Arrays.asList(
                new TrelloEvent("e1", new Date()),
                new TrelloEvent("e2", new Date()),
                new TrelloEvent("e3", new Date())
        );
    }



}
