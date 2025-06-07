package org.petergriffin.backend.video;


import java.util.*;
import java.util.ArrayList;

class SequenceElement{
    private final String type;
    private final String content;

    public SequenceElement(String type, String content){
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}

class Sequence{

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

public class Video {

    private Sequence sequence = new Sequence();
    private String VideoURL;

    public void addToSequence(String type, String content){
        sequence.addToSequenceElement(new SequenceElement(type, content));
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

}
