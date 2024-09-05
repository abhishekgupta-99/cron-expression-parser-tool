package org.abhishek.deliveroo.parsers;

import org.abhishek.deliveroo.enums.TimeField;

import java.util.ArrayList;
import java.util.List;

public class StepIncrementParser extends Parser {

    @Override
    public List<Integer> regexAndRangeValidator(TimeField timeField, String cronExpression) {
        List<Integer> result = new ArrayList<>();
        String expr = "*/";
        int incrementValue;
        try{
            incrementValue = Integer.parseInt(cronExpression.substring(expr.length()));
        } catch(NumberFormatException ex) {
            throw new RuntimeException("Invalid " + timeField.getName()+ " interval value entered!");
        }
        int begin = timeField.getStartValue();
        int end = timeField.getEndValue();
        if(incrementValue < begin) {
            throw new RuntimeException("Invalid " + timeField.getName()+ " interval value entered!");
        }
        else if(incrementValue>end){
            throw new RuntimeException("Invalid " + timeField.getName()+ " interval value entered!");
        }
        while (begin <= end) {
            result.add(begin);
            begin += incrementValue;
        }
        return result;
    }

//    Examples of valid strings:
//            "*/123"
//            "*/4567"

    @Override
    public String checkRegexPattern(){
        return "^\\*\\/\\d+$"; // {^ start, \\* star, \\/ forward slash ,\\d+ [0-9] one or more digits,$ end of string }
    }
}
