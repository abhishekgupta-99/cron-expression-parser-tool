package org.abhishek.deliveroo.parsers;

import org.abhishek.deliveroo.enums.TimeField;

import java.util.ArrayList;
import java.util.List;

public class HyphenRangeParser extends Parser {
    private boolean isValidRange(int begin, int end, TimeField timeField) {
        return begin >= timeField.getStartValue() && begin <= end && end <= timeField.getEndValue();
    }
    @Override
    public List<Integer> regexAndRangeValidator(TimeField timeField, String cronExpression) {
        List<Integer> result = new ArrayList<>();
        String[] rangeValues = cronExpression.split("-");
        int begin = Integer.parseInt(rangeValues[0]);
        int end = Integer.parseInt(rangeValues[1]);
        if(isValidRange(begin,end,timeField)) {
            while(begin <= end) {
                result.add(begin);
                begin++;
            }
        }
        else{
            throw new RuntimeException("Entered "+ timeField.getName()+" values are not in the range of Time Field");
        }
        return result;
    }

//    Examples of valid strings:
//            "123-456"
//            "1-1"
//            "1000-9999"
    @Override
    public String checkRegexPattern() {
        return "^\\d+\\-\\d+$";
    }
}
