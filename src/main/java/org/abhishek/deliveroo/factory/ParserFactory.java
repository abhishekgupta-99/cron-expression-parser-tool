package org.abhishek.deliveroo.factory;

import org.abhishek.deliveroo.parsers.*;

import java.util.HashSet;
import java.util.Set;

public class ParserFactory {

    public static Set<Parser> createParsers() {
        Set<Parser> parsers = new HashSet<>();
        parsers.add(new StarParser());
        parsers.add(new IncrementParser());
        parsers.add(new MultipleValuesParser());
        parsers.add(new NumberParser());
        parsers.add(new RangeParser());
        return parsers;
    }
}