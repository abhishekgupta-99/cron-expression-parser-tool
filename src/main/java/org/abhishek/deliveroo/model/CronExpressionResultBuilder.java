package org.abhishek.deliveroo.model;

import org.abhishek.deliveroo.enums.TimeField;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class CronExpressionResultBuilder {

    private final Map<TimeField, List<Integer>> finalTimingsMap;
    private final String cronCommand;

//    private final List<Integer> minute;
//    private final List<Integer> hour;
//    private final List<Integer> dayOfMonth;
//    private final List<Integer> month;
//    private final List<Integer> dayOfWeek;

//    public CronExpressionResponse(List<Integer> minute, List<Integer> hour, List<Integer> dayOfMonth, List<Integer> month, List<Integer> dayOfWeek, String cronCommand) {
//        this.minute = minute;
//        this.hour = hour;
//        this.dayOfMonth = dayOfMonth;
//        this.month = month;
//        this.dayOfWeek = dayOfWeek;
//        this.cronCommand = cronCommand;
//    }

    public CronExpressionResultBuilder(Map<TimeField, List<Integer>> finalOutputMap, String cronCommand) {
        this.finalTimingsMap = finalOutputMap;
        this.cronCommand = cronCommand;

    }

    public void displayParsedCronResponse() {
        //{% begin format specifier, - left aligns text, 14 width of field, s format in string}
//        String b = format("%-14s%s\n", TimeField.MINUTE.getName(), formatAndPrintList(minute)) +
//                format("%-14s%s\n", TimeField.HOUR.getName(), formatAndPrintList(hour)) +
//                format("%-14s%s\n", TimeField.DAY_OF_MONTH.getName(), formatAndPrintList(dayOfMonth)) +
//                format("%-14s%s\n", TimeField.MONTH.getName(), formatAndPrintList(month)) +
//                format("%-14s%s\n", TimeField.DAY_OF_WEEK.getName(), formatAndPrintList(dayOfWeek)) +
//                format("%-14s%s\n", "command", cronCommand);

        StringBuilder finalOutput = new StringBuilder();
        for (TimeField timeField : TimeField.values()) {
            finalOutput.append(String.format("%-14s%s\n", timeField.getName(), formatAndPrintList(finalTimingsMap.get(timeField))));
        }
        finalOutput.append(String.format("%-14s%s\n", "command", cronCommand));
        System.out.println(finalOutput);
    }

    private String formatAndPrintList(List<Integer> cronValues) {
        StringBuilder result = new StringBuilder();
        for(Integer cronValue: cronValues) {
            result.append(cronValue.toString()).append(" ");
        }
        return result.toString();
    }
}
