package ru.eltech.business;

import ru.eltech.www.MongoDAO;

public class GoogleTrelloApplicationMain {

    public static void main(String[] args) throws Exception {
        MongoDAO.addEvents();

        for (String string: args) {
            switch (string) {
                case "cd":
                    StatUtil.findOverworkByDay(true);
                    break;

                case "d":
                    StatUtil.findOverworkByDay(false);
                    break;

                case "ct":
                    StatUtil.findActionsByTime(true);
                    break;

                case "t":
                    StatUtil.findActionsByTime(false);
                    break;

                default:
                    System.out.println("Incorrect format! Try again!");
                    return;
            }
        }
    }
}
