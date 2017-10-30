package ru.eltech.www;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.TList;
import com.julienvey.trello.impl.TrelloImpl;
import java.util.List;


public class TrelloEventsPicker {

    private static final String TEST_APPLICATION_KEY = "5a81bb93f35516ba55b92ba63d7b31f8";
    private static final String TRELLO_ACCESS_TOKEN = "f1974878d145c6e6d3fd08400fdc35f6eab86f4a65d219fe33ac866719693eac";
    private static final String TRELLO_BOARD_ID = "55742808ce861027d52c3359";

    private static Trello trelloAPI;

    public List<TList> getAllEvents() throws NullPointerException {
        trelloAPI = new TrelloImpl(TEST_APPLICATION_KEY, TRELLO_ACCESS_TOKEN);
        Board board = trelloAPI.getBoard(TRELLO_BOARD_ID);
        return trelloAPI.getBoard(TRELLO_BOARD_ID).fetchLists();
    }
}
