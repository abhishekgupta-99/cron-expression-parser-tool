package org.abhishek.deliveroo.parsers;

import org.abhishek.deliveroo.enums.TimeField;

import java.util.ArrayList;
import java.util.List;

public class StarParser extends Parser {
    @Override
    public List<Integer> regexAndRangeValidator(TimeField timeField, String cronExpression){
        List<Integer> result = new ArrayList<>();
        int startValue = timeField.getStartValue();
        int endValue = timeField.getEndValue();

        while(startValue <= endValue) {
            result.add(startValue);
            startValue++;
        }
        return result;
    }


//    Examples of valid strings:
//   "*" (a single asterisk)
    @Override
    public String checkRegexPattern(){
        return "^\\*$";
    }
}
