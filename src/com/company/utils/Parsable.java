package com.company.utils;

public interface Parsable<T> extends Comparable<T> {

    /**
     * Suformuoja objektą iš eilutės
     *
     * @param dataString
     */
    void parse(String dataString);
}
