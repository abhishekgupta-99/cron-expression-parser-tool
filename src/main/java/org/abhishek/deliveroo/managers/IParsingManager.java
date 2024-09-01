package org.abhishek.deliveroo.managers;

import org.abhishek.deliveroo.enums.TimeField;

import java.util.List;

public interface IParsingManager {
    List<Integer> parseFieldValues(TimeField timeField, String cronExpression);
    void registerAllParsersType();
}
