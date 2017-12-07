package ru.eltech.business;

import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.eltech.data.TrelloEvent;

public class StatisticsProcessingExecutorTest {

    StatisticsProcessingExecutor executor;

    List trelloActions = new ArrayList();
    List googleEvents = new ArrayList();

    TrelloEvent trelloAction = mock(TrelloEvent.class);

    @Before
    public void setUp() {
        executor = new StatisticsProcessingExecutor();

        when(trelloAction.getDate()).thenReturn(new Date(0));
        trelloActions.add(trelloAction);
    }

    @Test
    public void prepareOvertimeStatistics() throws Exception {
        executor.prepareOvertimeStatistics(trelloActions, googleEvents);

        Assert.assertEquals(1, executor.overtimeStatisticsForEachDayMap.size());
        Assert.assertEquals(1, executor.statisticsForEachTimeOfDayMap.size());
    }

}