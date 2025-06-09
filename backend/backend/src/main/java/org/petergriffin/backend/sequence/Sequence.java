package org.petergriffin.backend.sequence;

import java.util.ArrayList;
import java.util.List;

public class Sequence{

    private List<SequenceElement> SequenceElements = new ArrayList<SequenceElement>();

    public List<SequenceElement> getSequenceElements() {
        return SequenceElements;
    }

    public void setSequenceElements(List<SequenceElement> sequenceElements) {
        SequenceElements = sequenceElements;
    }

    public void addToSequenceElement(SequenceElement sequenceElement){
        SequenceElements.add(sequenceElement);
    }
}