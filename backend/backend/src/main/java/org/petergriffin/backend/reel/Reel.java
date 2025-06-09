package org.petergriffin.backend.reel;

import org.petergriffin.backend.video.Video;

import java.util.ArrayList;
import java.util.List;


public class Reel {

    private List<String> dialogues = new ArrayList<String>();
    private List<String> Voices = new ArrayList<String>();
    private Video videoData;
//    private final video


    //Constructor
    public Reel(List<String> dialogues, List<String> voices, Video videoData) {
        this.dialogues = dialogues;
        this.Voices = voices;
        this.videoData = videoData;
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

    public void setVideoData(Video videoData) {
         this.videoData = videoData;
    }

    public Video getVideoData() {
        return videoData;
    }
}
