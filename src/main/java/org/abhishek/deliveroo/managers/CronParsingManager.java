package org.abhishek.deliveroo.managers;
import org.abhishek.deliveroo.enums.TimeField;
import org.abhishek.deliveroo.factory.ParserFactory;
import org.abhishek.deliveroo.parsers.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class CronParsingManager implements IParsingManager {
    private final Set<Parser> parserSet = new HashSet<>();

    @Override
    public List<Integer> parseFieldValues(TimeField timeField, String cronExpression) {
        for(Parser parser: parserSet) {
            if(Pattern.matches(parser.checkRegexPattern(),cronExpression)) {
                return parser.regexAndRangeValidator(timeField,cronExpression);
            }
        }
        throw new RuntimeException("Invalid cron expression entered! Cannot be parsed!");
    }

    @Override
    public void registerAllParsersType() {

        parserSet.addAll(ParserFactory.createParsers());
//        parserSet.add(new StarParser());
//        parserSet.add(new IncrementParser());
//        parserSet.add(new MultipleValuesParser());
//        parserSet.add(new NumberParser());
//        parserSet.add(new RangeParser());
    }
}
