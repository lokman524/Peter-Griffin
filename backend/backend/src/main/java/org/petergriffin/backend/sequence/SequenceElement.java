package org.petergriffin.backend.sequence;

public class SequenceElement <T> {

    private final T content;

    public SequenceElement(T content){
        this.content = content;
    }

    public T getContent() {
        return content;
    }

}
