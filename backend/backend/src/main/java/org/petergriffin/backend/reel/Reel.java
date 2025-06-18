package org.petergriffin.backend.reel;

import org.petergriffin.backend.video.Video;

import java.util.ArrayList;
import java.util.List;


public class Reel {

    private List<String> dialogues = new ArrayList<String>();
    private Video videoData;
//    private final video


    //Constructor
    public Reel(List<String> dialogues, Video videoData) {
        this.dialogues = dialogues;
        this.videoData = videoData;
    }

    //Getters and Setters
    public List<String> getDialogues() {
        return dialogues;
    }

    public void setDialogues(List<String> dialogues) {
        this.dialogues = dialogues;
    }

    public void setVideoData(Video videoData) {
         this.videoData = videoData;
    }

    public Video getVideoData() {
        return videoData;
    }
}
