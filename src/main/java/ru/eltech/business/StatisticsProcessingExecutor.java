package ru.eltech.business;

import com.google.api.services.calendar.model.Event;
import com.julienvey.trello.domain.Action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.DateTime;
import ru.eltech.www.GoogleCalendarEventsPicker;
import ru.eltech.www.TrelloEventsPicker;

public class StatisticsProcessingExecutor {

    Map<Integer, Integer> overtimeStatisticsForEachDayMap = new HashMap<>();
    Map<Integer, Integer> overtimeStatisticsForEachTimeOfDayMap = new HashMap<>();

    public void prepareOvertimeStatistics(List<Action> trelloActions, List<Event> googleEvents) {
        for (Action trelloAction : trelloActions) {
            DateTime trelloActionDate = new DateTime(trelloAction.getDate());

            if (isOverworking(trelloActionDate, googleEvents)) {
                updateOvertimeStatisticsForEachDayMap(trelloActionDate);
                updateOvertimeStatisticsForEachTimeOfDayMap(trelloActionDate);
            }
        }
    }

    private boolean isOverworking(DateTime trelloActionDate, List<Event> googleEvents) {
        for (Event event : googleEvents) {
            DateTime googleEventStartTime = new DateTime(
                event.getStart().getDateTime().getValue());

            DateTime googleEventEndTime = new DateTime(
                event.getEnd().getDateTime().getValue());

            if (googleEventStartTime.isBefore(trelloActionDate)
                && googleEventEndTime.isAfter(trelloActionDate)) {
                return false;
            }
        }

        return true;
    }

    private void updateOvertimeStatisticsForEachTimeOfDayMap(DateTime trelloActionDate) {
        int numberOfOverworkings = overtimeStatisticsForEachTimeOfDayMap
            .getOrDefault(trelloActionDate.getHourOfDay(), 0);

        overtimeStatisticsForEachTimeOfDayMap
            .put(trelloActionDate.getHourOfDay(), ++numberOfOverworkings);
    }

    private void updateOvertimeStatisticsForEachDayMap(DateTime trelloActionDate) {
        int numberOfOverworkings = overtimeStatisticsForEachDayMap
            .getOrDefault(trelloActionDate.getDayOfWeek(), 0);

        overtimeStatisticsForEachDayMap
            .put(trelloActionDate.getDayOfWeek(),
                ++numberOfOverworkings);
    }

    public static void main(String[] args) throws Exception {
        StatisticsProcessingExecutor statisticsProcessingExecutor = new StatisticsProcessingExecutor();
        statisticsProcessingExecutor
            .prepareOvertimeStatistics((new TrelloEventsPicker()).getAllActions(),
                (new GoogleCalendarEventsPicker()).getAllEvents());
    }

}
