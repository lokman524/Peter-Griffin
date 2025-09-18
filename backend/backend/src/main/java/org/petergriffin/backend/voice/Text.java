package org.petergriffin.backend.voice;

public class Text {

    private String text;

    public Pause(String text){
        this.text = text;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

}
