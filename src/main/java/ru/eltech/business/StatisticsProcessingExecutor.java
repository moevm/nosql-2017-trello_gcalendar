package ru.eltech.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import ru.eltech.data.GoogleCalendarEvent;
import ru.eltech.data.TrelloEvent;
import ru.eltech.mongo.Mongo;

public class StatisticsProcessingExecutor {

    Map<Integer, Integer> overtimeStatisticsForEachDayMap = new HashMap<>();
    Map<Integer, Integer> statisticsForEachTimeOfDayMap = new HashMap<>();

    public void prepareOvertimeStatistics(List<TrelloEvent> trelloActions, List<GoogleCalendarEvent> googleEvents) {
        for (TrelloEvent trelloAction : trelloActions) {
            DateTime trelloActionDate = new DateTime(trelloAction.getDate());

            if (isOverworking(trelloActionDate, googleEvents)) {
                updateOvertimeStatisticsForEachDayMap(trelloActionDate);
            }

            updateStatisticsForEachTimeOfDayMap(trelloActionDate);

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

    private void updateStatisticsForEachTimeOfDayMap(DateTime trelloActionDate) {
        int numberOfOverworkings = statisticsForEachTimeOfDayMap
                .getOrDefault(trelloActionDate.getHourOfDay(), 0);

        statisticsForEachTimeOfDayMap
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


        int first = 0;
        int second = 0;

        for (int i = 0; i < 24; i++) {
            Integer value = statisticsProcessingExecutor.statisticsForEachTimeOfDayMap.getOrDefault(i, 0);

            if (i < 12) {
                first += value;
            } else {
                second += value;
            }

        }

        System.out.println();
        System.out.println("Действий в первой половине дня: " + first);
        System.out.println("Действий во второй половине дня: " + second);

        String[] daysOfWeek = new String[] {
            "Понедельник",
            "Вторник",
            "Среда",
            "Четверг",
            "Пятница",
            "Суббота",
            "Воскресение"};

        System.out.println();
        for (int i = 0; i < 7; i++) {
            Integer value = statisticsProcessingExecutor.overtimeStatisticsForEachDayMap.getOrDefault(i + 1, 0);
            System.out.println("Переработка в " + daysOfWeek[i] + ": " + value);

        }
    }

}
