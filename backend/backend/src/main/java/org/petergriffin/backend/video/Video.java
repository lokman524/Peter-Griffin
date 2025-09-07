package org.petergriffin.backend.video;


import org.petergriffin.backend.sequence.Sequence;
import org.petergriffin.backend.sequence.SequenceElement;
import org.petergriffin.backend.voice.Pause;
import org.petergriffin.backend.voice.Voice;

import java.io.File;
import java.util.*;
import java.util.ArrayList;

/***
 * Contains the information of the video
 * IMPORTANT: DOES NOT INCLUDE THE ACTUAL VIDEO MP4
 * Only includes the sequence of the video as a list of pauses or audio
 */
public class Video {

    private Sequence sequence = new Sequence();
    private String VideoURL;

    public void addAudioToSequence(Voice voice){
        sequence.addToSequenceElement(new SequenceElement<Voice>(
                voice));
    }

    public void addPauseToSequence(){
        sequence.addToSequenceElement(new SequenceElement<Pause>(
                new Pause()));
    }

    public Sequence getSequence() {
        return sequence;
    }

    public void setSequence(Sequence sequence) {
        this.sequence = sequence;
    }

}
