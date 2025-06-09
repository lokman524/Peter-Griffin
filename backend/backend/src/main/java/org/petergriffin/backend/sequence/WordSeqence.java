package org.petergriffin.backend.sequence;

import java.util.ArrayList;
import java.util.List;

public class WordSeqence {

    private List<WordSequenceElement> wordSequenceElements;

    public WordSeqence() {
        wordSequenceElements = new ArrayList<WordSequenceElement>();
    }

    public void addToWordSequence(WordSequenceElement wordSequenceElement) {
        wordSequenceElements.add(wordSequenceElement);
    }

}
