package org.abhishek.deliveroo.parsers;


import org.abhishek.deliveroo.enums.TimeField;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MultipleValuesParser extends Parser {

    private boolean isValid(int timeValue, TimeField timeField) {
        return timeValue >= timeField.getStartValue() && timeValue <= timeField.getEndValue();
    }
    @Override
    public List<Integer> regexAndRangeValidator(TimeField timeField, String cronExpression) {
        Set<Integer> result = new TreeSet<>();
        String[] cronValues = cronExpression.split(",");
        for(String cronValue: cronValues) {
            int timeValue = Integer.valueOf(cronValue);
            if(isValid(timeValue,timeField)) {
                result.add(timeValue);
            }
            else {
                throw new RuntimeException("Incorrect "+ timeField.getName() +" time value entered!");
            }
        }
        List<Integer> mainList = new ArrayList<>();
        mainList.addAll(result);
        return mainList;
    }

//    Examples of valid strings:
//            "123"
//            "123,456"
//            "1,2,3,4,5"
    @Override
    public String checkRegexPattern(){
        return "^\\d+(,\\d+)*$"; // {^ start, \\d+ [0-9] digits, (,\d+)* zero or more occurrences of a comma followed by one or more digits,$ end of string }
    }
}
