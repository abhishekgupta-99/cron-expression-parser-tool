package org.abhishek.deliveroo;

import org.abhishek.deliveroo.managers.IParsingManager;
import org.abhishek.deliveroo.managers.CronParsingManager;
import org.abhishek.deliveroo.model.CronExpressionResultBuilder;

public class Main {
    public static void main(String[] args) {
//        args = new String[]{"*/58 */2 */5 1,2,3 0-6 /usr/bin/find"};
        if(args.length != 1 && args[0].split(" ").length != 6) {
            throw new RuntimeException("Invalid number of arguments passed!");
        }
        //index out of bound if no args passed

        String cronExpression = args[0];

        IParsingManager parsingManager = new CronParsingManager();
        parsingManager.registerAllParsersType();

        CronExpressionParser cronExpressionParser = new CronExpressionParser(parsingManager);
        try{
            CronExpressionResultBuilder cronExpressionResultBuilder = cronExpressionParser.parseExpression(cronExpression);
            cronExpressionResultBuilder.displayParsedCronResponse();
        } catch(RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
}