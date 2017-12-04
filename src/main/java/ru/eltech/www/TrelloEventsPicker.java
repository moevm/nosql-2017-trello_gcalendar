package ru.eltech.www;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Action;
import com.julienvey.trello.impl.TrelloImpl;
import java.util.List;


public class TrelloEventsPicker {

    private static final String TEST_APPLICATION_KEY = "5a81bb93f35516ba55b92ba63d7b31f8";
    private static final String TRELLO_ACCESS_TOKEN = "f1974878d145c6e6d3fd08400fdc35f6eab86f4a65d219fe33ac866719693eac";
    private static final String TRELLO_BOARD_ID = "5a21c7f35397170e85f40da6";

    private static Trello trelloAPI;

    public List<Action> getAllActions() throws NullPointerException {
        trelloAPI = new TrelloImpl(TEST_APPLICATION_KEY, TRELLO_ACCESS_TOKEN);
        return trelloAPI.getBoardActions(TRELLO_BOARD_ID);
    }

    public static void main(String[] args) {
        TrelloEventsPicker trelloEventsPicker = new TrelloEventsPicker();
        List<Action> list = trelloEventsPicker.getAllActions();
        for (Action a : list) {
            System.out.println(a.getId());
            System.out.println(a.getType());
            System.out.println(a.getDate());
        }
    }
}
