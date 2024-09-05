package org.abhishek.deliveroo;

import org.abhishek.deliveroo.managers.IParsingManager;
import org.abhishek.deliveroo.managers.CronParsingManager;
import org.abhishek.deliveroo.model.CronExpressionResultBuilder;
import org.abhishek.deliveroo.enums.TimeField;
import org.abhishek.deliveroo.parsers.*;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CronExpressionParserTest {

    @ParameterizedTest
    @MethodSource("provideValidCronExpressions")
    public void testCronExpressionParsingPositive(String cronExpression) throws RuntimeException {
        IParsingManager parsingManager = new CronParsingManager();
        parsingManager.registerAllParsersType();

        CronExpressionParser cronExpressionParser = new CronExpressionParser(parsingManager);

        CronExpressionResultBuilder cronExpressionResultBuilder = cronExpressionParser.parseExpression(cronExpression);
        cronExpressionResultBuilder.displayParsedCronResponse();
        assertNotNull(cronExpressionResultBuilder);
    }

    private static Stream<String> provideValidCronExpressions() {
        return Stream.of(
                "*/15 0 1,15 * 1-5 /usr/bin/find",     // Case with increments and multiple values
                "0 0 1 1 * /usr/bin/find",            // Simple case
                "*/10 0 1,15 * 1-5 /usr/bin/find",    // Another case with increments
                "0 0 1-5 * 1 /usr/bin/find",          // Case with ranges
                "*/5 * * * * /usr/bin/find",          // Case with increments for every field
                "0 12 * * 0-6 /usr/bin/find",         // Every day at noon
                "0 0 1 1 * /usr/bin/find",            // Every January 1st
                "*/2 8-17 * * 1-5 /usr/bin/find",    // Every 2 minutes between 8 AM to 5 PM, Monday to Friday
                "* * * * * /usr/bin/find",
                "0 0 1-1 1 1 /usr/bin/find"


//                "*/61 0 31 1,2,3 0-6 /usr/bin/find"  //TEST
        );
    }

    @Test
    public void cron_expression_parsing_negative() throws RuntimeException {
        IParsingManager parsingManager = new CronParsingManager();
        parsingManager.registerAllParsersType();

        CronExpressionParser cronExpressionParser = new CronExpressionParser(parsingManager);
        try {
            cronExpressionParser.parseExpression("*/15 0 1,15 # 1-59 /usr/bin/find");
        }
        catch(RuntimeException re) {
            assertEquals("Invalid cron expression entered! Cannot be parsed!", re.getMessage());
        }
    }

    @Test
    public void star_parsing_positive() throws RuntimeException {
        Parser p = new WildStarParser();
        List<Integer> result;

        result = p.regexAndRangeValidator(TimeField.DAY_OF_WEEK,"*");
        assertEquals("[0, 1, 2, 3, 4, 5, 6]", result.toString());
    }

    @Test
    public void star_parsing_negative()  {
        Parser parser = new WildStarParser();
        expressionParserUtil("#", TimeField.DAY_OF_MONTH, "Invalid value entered!", parser);
    }

    @Test
    public void range_parsing_positive() throws RuntimeException {
        Parser p = new HyphenRangeParser();
        List<Integer> result;

        result = p.regexAndRangeValidator(TimeField.HOUR,"1-3");
        assertEquals("[1, 2, 3]", result.toString());

        result = p.regexAndRangeValidator(TimeField.DAY_OF_WEEK,"4-6");
        assertEquals("[4, 5, 6]", result.toString());

        result = p.regexAndRangeValidator(TimeField.MINUTE,"2-2");
        assertEquals("[2]", result.toString());
    }

    @Test
    public void range_parsing_negative()  {
        Parser parser = new HyphenRangeParser();
        expressionParserUtil("2-57", TimeField.DAY_OF_MONTH, "Entered day of month values are not in the range of Time Field", parser);
        expressionParserUtil("70-80", TimeField.MINUTE, "Entered minute values are not in the range of Time Field", parser);
        expressionParserUtil("2-10", TimeField.DAY_OF_WEEK, "Entered day of week values are not in the range of Time Field", parser);
    }

    @Test
    public void number_parsing_positive() throws RuntimeException {
        Parser p = new NumberParser();
        List<Integer> result;

        result = p.regexAndRangeValidator(TimeField.HOUR,"1");
        assertEquals("[1]", result.toString());

        result = p.regexAndRangeValidator(TimeField.DAY_OF_WEEK,"4");
        assertEquals("[4]", result.toString());

        result = p.regexAndRangeValidator(TimeField.MINUTE,"25");
        assertEquals("[25]", result.toString());
    }

    @Test
    public void number_parsing_negative()  {
        Parser parser = new NumberParser();
        expressionParserUtil("-1", TimeField.DAY_OF_MONTH, "Incorrect day of month value provided!", parser);
        expressionParserUtil("60", TimeField.MINUTE, "Incorrect minute value provided!", parser);
        expressionParserUtil("8", TimeField.DAY_OF_WEEK, "Incorrect day of week value provided!", parser);
    }

    @Test
    public void multiple_values_parsing_positive() throws RuntimeException {
        Parser p = new CommaValuesParser();
        List<Integer> result;

        result = p.regexAndRangeValidator(TimeField.HOUR,"1,2,3");
        assertEquals("[1, 2, 3]", result.toString());

        result = p.regexAndRangeValidator(TimeField.HOUR,"1,1,1");
        assertEquals("[1]", result.toString());

        result = p.regexAndRangeValidator(TimeField.HOUR,"3,1,2");
        assertEquals("[1, 2, 3]", result.toString());
    }

    @Test
    public void multiple_values_parsing_negative()  {
        Parser parser = new CommaValuesParser();
        expressionParserUtil("-1,35", TimeField.DAY_OF_MONTH, "Incorrect day of month time value entered!", parser);
        expressionParserUtil("2,34", TimeField.DAY_OF_MONTH, "Incorrect day of month time value entered!", parser);
        expressionParserUtil("0,5", TimeField.DAY_OF_WEEK, "Incorrect time value entered!", parser);
    }
    
    @Test
    public void increment_parsing_positive() throws RuntimeException {
        Parser p = new StepIncrementParser();
        List<Integer> result;

        result = p.regexAndRangeValidator(TimeField.HOUR,"*/10");
        assertEquals("[0, 10, 20]", result.toString());

//        result = p.regexAndRangeValidator(TimeField.HOUR,"*/24");
//        assertEquals("[0]", result.toString());

        result = p.regexAndRangeValidator(TimeField.DAY_OF_MONTH,"*/20");
        assertEquals("[1, 21]", result.toString());

        result = p.regexAndRangeValidator(TimeField.DAY_OF_WEEK,"*/2");
        assertEquals("[0, 2, 4, 6]", result.toString());
    }
    @Test
    public void increment_parsing_negative()  {
        Parser parser = new StepIncrementParser();
        expressionParserUtil("*/0", TimeField.DAY_OF_MONTH, "Invalid day of month interval value entered!", parser);
        expressionParserUtil("*/10/10", TimeField.DAY_OF_MONTH, "Invalid day of month interval value entered!", parser);
        expressionParserUtil("*/A", TimeField.DAY_OF_MONTH, "Invalid day of month interval value entered!", parser);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidIncrementExpressions")
    public void incrementParsingNegative(String cronExpression, TimeField timeField, String expectedErrorMessage) {
        Parser parser = new StepIncrementParser();

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            expressionParserUtil(cronExpression, timeField, parser);
        });

        assertEquals(expectedErrorMessage, thrown.getMessage());
    }

    private static Stream<Arguments> provideInvalidIncrementExpressions() {
        return Stream.of(
                Arguments.of("*/0", TimeField.DAY_OF_MONTH, "Invalid day of month interval value entered!"),
                Arguments.of("*/10/10", TimeField.DAY_OF_MONTH, "Invalid day of month interval value entered!"),
                Arguments.of("*/A", TimeField.DAY_OF_MONTH, "Invalid day of month interval value entered!")
        );
    }

//    @Test
//    public void increment_parsing_negative()  {
//        Parser parser = new IncrementParser();
//        expressionParserUtil("*/0", TimeField.DAY_OF_MONTH, "Invalid day of month interval value entered!", parser);
//        expressionParserUtil("*/10/10", TimeField.DAY_OF_MONTH, "Invalid day of month interval value entered!", parser);
//        expressionParserUtil("*/A", TimeField.DAY_OF_MONTH, "Invalid day of month interval value entered!", parser);
//    }

    private void expressionParserUtil(String cronExpression, TimeField timeField, Parser parser) {
        parser.regexAndRangeValidator(timeField, cronExpression);
    }

    private void expressionParserUtil(String incomingText, TimeField timeField, String msg, Parser parser) {
        try {
            parser.regexAndRangeValidator(timeField, incomingText);
        }
        catch(RuntimeException re) {
            assertEquals(msg, re.getMessage());
        }
    }
}