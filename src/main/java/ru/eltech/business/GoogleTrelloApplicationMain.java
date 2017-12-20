package ru.eltech.business;

import ru.eltech.www.AddEventsToMongo;

public class GoogleTrelloApplicationMain {

    public static void main(String[] args) throws Exception {
        AddEventsToMongo.main(args);
        FindActionsByTime.main(args);
        FindActionsByTimeClosing.main(args);
        FindOverworkByDay.main(args);
        FindOverworkByDayClosing.main(args);
    }
}
