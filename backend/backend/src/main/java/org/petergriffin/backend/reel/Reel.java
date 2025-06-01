package org.petergriffin.backend.reel;

import java.util.ArrayList;
import java.util.List;


public class Reel {

    private List<String> dialogues = new ArrayList<String>();
    private List<String> Voices = new ArrayList<String>();
//    private final video


    //Constructor
    public Reel(List<String> dialogues, List<String> voices) {
        this.dialogues = dialogues;
        this.Voices = voices;
    }

    //Getters and Setters
    public List<String> getDialogues() {
        return dialogues;
    }

    public void setDialogues(List<String> dialogues) {
        this.dialogues = dialogues;
    }

    public List<String> getVoices() {
        return Voices;
    }

    public void setVoices(List<String> voices) {
        Voices = voices;
    }
}
