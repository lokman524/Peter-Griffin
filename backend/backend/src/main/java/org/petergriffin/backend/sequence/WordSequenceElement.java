package org.petergriffin.backend.sequence;

public class WordSequenceElement {

    private final String word;
    private final int start;
    private final int end;


    public WordSequenceElement(String word, int start, int end) {
        this.word = word;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getWord() {
        return word;
    }
}
