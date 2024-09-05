package org.abhishek.deliveroo.parsers;

import org.abhishek.deliveroo.enums.TimeField;

import java.util.ArrayList;
import java.util.List;

public class WildStarParser extends Parser {
    @Override
    public List<Integer> regexAndRangeValidator(TimeField timeField, String cronExpression){
        List<Integer> result = new ArrayList<>();
        int begin = timeField.getStartValue();
        int end = timeField.getEndValue();

        while(begin <= end) {
            result.add(begin);
            begin++;
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
