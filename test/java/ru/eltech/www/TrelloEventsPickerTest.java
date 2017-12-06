package ru.eltech.www;

import static org.junit.Assert.*;

import com.julienvey.trello.domain.Action;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

public class TrelloEventsPickerTest {

    TrelloEventsPicker trelloEventsPicker;

    @Before
    public void setUp() {
        trelloEventsPicker = new TrelloEventsPicker();
    }

    @Test
    public void getAllActions() throws Exception {
        List<Action> actions = trelloEventsPicker.getAllActions();

        Assert.notNull(actions, "Actions list cannot be equal null!");
        Assert.notEmpty(actions, "There should be at least one action!");
    }

}