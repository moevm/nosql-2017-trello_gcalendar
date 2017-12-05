package ru.eltech.business;

import com.google.api.services.calendar.model.Event;
import com.julienvey.trello.domain.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import ru.eltech.data.GoogleCalendarEvent;
import ru.eltech.data.TrelloEvent;
import ru.eltech.mongo.Mongo;
import ru.eltech.www.GoogleCalendarEventsPicker;
import ru.eltech.www.TrelloEventsPicker;

public class StatisticsProcessingExecutor {

    Map<Integer, Integer> overtimeStatisticsForEachDayMap = new HashMap<>();
    Map<Integer, Integer> overtimeStatisticsForEachTimeOfDayMap = new HashMap<>();

    public void prepareOvertimeStatistics(List<TrelloEvent> trelloActions, List<GoogleCalendarEvent> googleEvents) {
        for (TrelloEvent trelloAction : trelloActions) {
            DateTime trelloActionDate = new DateTime(trelloAction.getDate());

            if (isOverworking(trelloActionDate, googleEvents)) {
                updateOvertimeStatisticsForEachDayMap(trelloActionDate);
                updateOvertimeStatisticsForEachTimeOfDayMap(trelloActionDate);
            }
        }
    }

    private boolean isOverworking(DateTime trelloActionDate, List<GoogleCalendarEvent> googleEvents) {
        for (GoogleCalendarEvent event : googleEvents) {
            DateTime googleEventStartTime = new DateTime(event.getStart());

            DateTime googleEventEndTime = new DateTime(event.getEnd());

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
        statisticsProcessingExecutor.prepareOvertimeStatistics(
                Mongo.getTrelloEvents(),
                Mongo.getGoogleCalendarEvents());
    }

}
