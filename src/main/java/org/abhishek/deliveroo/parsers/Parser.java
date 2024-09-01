package org.abhishek.deliveroo.parsers;


import org.abhishek.deliveroo.enums.TimeField;

import java.util.List;

public abstract class Parser {
    public abstract String checkRegexPattern();
    public abstract List<Integer> regexAndRangeValidator(TimeField timeField, String cronExpression);
}
