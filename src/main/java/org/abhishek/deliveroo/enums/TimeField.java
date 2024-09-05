package org.abhishek.deliveroo.enums;

public enum TimeField {
    MINUTE("minute",0,59),
    HOUR("hour",0,23),
    DAY_OF_MONTH("day of month",1,31),
    MONTH("month",1,12),
    DAY_OF_WEEK("day of week",0,6);

    private String name;
    private int begin;
    private int end;

    TimeField(String name, int begin, int end) {
        this.name = name;
        this.begin = begin;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public int getStartValue() {
        return begin;
    }

    public int getEndValue() {
        return end;
    }
}
