package org.petergriffin.backend.voice;

public class Pause {

    private String content;
    private float duration;

    public Pause(){
        content = "pause";
        duration = 5;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}
