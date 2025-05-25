package org.petergriffin.backend.reel;

import java.util.ArrayList;
import java.util.List;


public class Reel {

    private List<String> dialogues = new ArrayList<String>();
//    private final voice
//    private final video


    //Constructor
    public Reel(List<String> dialogues) {
        this.dialogues = dialogues;
    }

    //Getters and Setters
    public List<String> getDialogues() {
        return dialogues;
    }

    public void setDialogues(List<String> dialogues) {
        this.dialogues = dialogues;
    }

}
