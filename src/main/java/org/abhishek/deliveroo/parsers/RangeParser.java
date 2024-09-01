package org.abhishek.deliveroo.parsers;

import org.abhishek.deliveroo.enums.TimeField;

import java.util.ArrayList;
import java.util.List;

public class RangeParser extends Parser {
    private boolean isValidRange(int startValue, int endValue, TimeField timeField) {
        return startValue >= timeField.getStartValue() && startValue <= endValue && endValue <= timeField.getEndValue();
    }
    @Override
    public List<Integer> regexAndRangeValidator(TimeField timeField, String cronExpression) {
        List<Integer> result = new ArrayList<>();
        String[] rangeValues = cronExpression.split("-");
        int startValue = Integer.parseInt(rangeValues[0]);
        int endValue = Integer.parseInt(rangeValues[1]);
        if(isValidRange(startValue,endValue,timeField)) {
            while(startValue <= endValue) {
                result.add(startValue);
                startValue++;
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
