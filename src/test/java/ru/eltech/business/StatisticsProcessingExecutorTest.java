package ru.eltech.business;

import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import ru.eltech.data.TrelloEvent;

public class StatisticsProcessingExecutorTest {

    StatisticsProcessingExecutor executor;

    List trelloActions = anyList();
    List googleEvents = anyList();

    TrelloEvent trelloEvent = mock(TrelloEvent.class);

    @Before
    public void setUp() {
        executor = new StatisticsProcessingExecutor();

        when(trelloEvent.getDate()).thenReturn(new Date(0));
        when(trelloActions.get(0)).thenReturn(trelloEvent);
    }

    @Test
    public void prepareOvertimeStatistics() throws Exception {
        executor.prepareOvertimeStatistics(trelloActions, googleEvents);
    }

}