package org.abhishek.deliveroo.parsers;


import org.abhishek.deliveroo.enums.TimeField;

import java.util.ArrayList;
import java.util.List;

public class NumberParser extends Parser {
    @Override
    public List<Integer> regexAndRangeValidator(TimeField timeField, String cronExpression) {
        List<Integer> result = new ArrayList<>();
        int timeValue = Integer.valueOf(cronExpression);
        if(timeValue >= timeField.getStartValue() && timeValue <= timeField.getEndValue()) {
            result.add(timeValue);
        }
        else{
            throw new RuntimeException("Incorrect "+ timeField.getName()+" value provided!");
        }
        return result;
    }

//    Examples of valid strings:
//
//            "123"
//            "4567"
//            "0"
    @Override
    public String checkRegexPattern(){
        return "^\\d+$";
    }
}
