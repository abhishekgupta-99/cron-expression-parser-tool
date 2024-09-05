package org.abhishek.deliveroo.factory;

import org.abhishek.deliveroo.parsers.*;

import java.util.HashSet;
import java.util.Set;

public class ParserFactory {

    public static Set<Parser> createParsers() {
        Set<Parser> parsers = new HashSet<>();
        parsers.add(new WildStarParser());
        parsers.add(new StepIncrementParser());
        parsers.add(new CommaValuesParser());
        parsers.add(new NumberParser());
        parsers.add(new HyphenRangeParser());
        return parsers;
    }
}