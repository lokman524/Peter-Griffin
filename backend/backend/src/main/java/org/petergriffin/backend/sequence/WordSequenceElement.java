package org.petergriffin.backend.sequence;

public class WordSequenceElement {

    private final String word;
    private final float start;
    private final float end;


    public WordSequenceElement(String word, float start, float end) {
        this.word = word;
        this.start = start;
        this.end = end;
    }

    public float getStart() {
        return start;
    }

    public float getEnd() {
        return end;
    }

    public String getWord() {
        return word;
    }
}
