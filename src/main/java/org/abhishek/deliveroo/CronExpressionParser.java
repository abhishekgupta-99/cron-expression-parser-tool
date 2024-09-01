package org.abhishek.deliveroo;

import org.abhishek.deliveroo.managers.IParsingManager;
import org.abhishek.deliveroo.model.CronExpressionResultBuilder;
import org.abhishek.deliveroo.enums.TimeField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CronExpressionParser {
    private IParsingManager parsingManager;

    public CronExpressionParser(IParsingManager parsingManager) {
        this.parsingManager = parsingManager;
    }

    public CronExpressionResultBuilder parseExpression(String cronExpression) {
        String[] subCronExpressions = cronExpression.split(" ");

        Map<TimeField, List<Integer>> finalOutputMap = new HashMap<>();
        finalOutputMap.put(TimeField.MINUTE, parsingManager.parseFieldValues(TimeField.MINUTE,subCronExpressions[0] ));
        finalOutputMap.put(TimeField.HOUR, parsingManager.parseFieldValues(TimeField.HOUR,subCronExpressions[1] ));
        finalOutputMap.put(TimeField.DAY_OF_MONTH, parsingManager.parseFieldValues(TimeField.DAY_OF_MONTH,subCronExpressions[2]) );
        finalOutputMap.put(TimeField.MONTH, parsingManager.parseFieldValues(TimeField.MONTH,subCronExpressions[3] ));
        finalOutputMap.put(TimeField.DAY_OF_WEEK, parsingManager.parseFieldValues(TimeField.DAY_OF_WEEK,subCronExpressions[4]));

//        List<Integer> MinuteResult = parsingManager.parseFieldValues(TimeField.MINUTE,subCronExpressions[0]);
//        List<Integer> HourResult = parsingManager.parseFieldValues(TimeField.HOUR,subCronExpressions[1]);
//        List<Integer> DayOfMonthResult = parsingManager.parseFieldValues(TimeField.DAY_OF_MONTH,subCronExpressions[2]);
//        List<Integer> MonthResult = parsingManager.parseFieldValues(TimeField.MONTH,subCronExpressions[3]);
//        List<Integer> DayOfWeekResult = parsingManager.parseFieldValues(TimeField.DAY_OF_WEEK,subCronExpressions[4]);
        String cronCommand = subCronExpressions[5];

        CronExpressionResultBuilder cronExpressionResultBuilder = new CronExpressionResultBuilder(finalOutputMap, cronCommand);
        return cronExpressionResultBuilder;
    }
}
