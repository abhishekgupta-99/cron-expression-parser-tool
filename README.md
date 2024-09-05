# Cron Expression Parser Tool
The Cron Expression Parser Tool is a command-line utility designed to parse cron expressions and display the exact times at which the specified command will be executed.

# Features
- Simple and Intuitive: Parse standard cron expressions and output the expanded schedule in a clear format.
- Comprehensive Support: Handles minute, hour, day of month, month, and day of week fields with various formats, including ranges, increments, wildcards, and specific values.

# Building the application
- This is a maven project.
- The project is built using `mvn clean install` command.
- A jar named `cron-expression-parser-tool-0.0.1.jar` should be created in `target/` folder.

# Running the application
- `cd target` to navigate to the jar path.
- Command used to run the application `java -jar cron-expression-parser-tool-0.0.1.jar "<input_string>"`

- Sample Input: `java -jar cron-expression-parser-tool-0.0.1.jar "*/15 0 1,15 * 1-5 /usr/bin/find"`

# Input String Format
The input string should be a standard cron expression with six fields:

Minute: 0-59
Hour: 0-23
Day of Month: 1-31
Month: 1-12
Day of Week: 0-6 (where 0 is Sunday)
Command: The command to execute

- `input_string` should be passed in this format : [minute] [hour] [day of month] [month] [day of week] [command]

     Example: `*/15 0 1,15 * 1-5 /usr/bin/find`

- Sample output of the application :

```
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```
# Handling Edge Cases
The Cron Expression Parser Tool has been designed to handle various edge cases:

- Wildcards (*): All possible values for the corresponding field.
- Ranges (x-y): All values within the specified range.
- Increments (*/n): All values at intervals of n within the field's range.
- Multiple Values (x,y,z): Expands to the specified values.