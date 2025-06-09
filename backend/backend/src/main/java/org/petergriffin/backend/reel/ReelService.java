package org.petergriffin.backend.reel;

import org.petergriffin.backend.dialogue.DialogueService;
import org.petergriffin.backend.prompt.Prompt;
import org.petergriffin.backend.sequence.Sequence;
import org.petergriffin.backend.sequence.SequenceElement;
import org.petergriffin.backend.video.Video;
import org.petergriffin.backend.voice.Pause;
import org.petergriffin.backend.voice.Voice;
import org.petergriffin.backend.voice.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReelService {

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private VoiceService voiceService;

    @Value("${app.api.voice-storage-path}")
    private String VOICE_STORAGE_PATH;

    public Reel createReel(Prompt prompt) throws IOException {
        List<String> DialogueResult = dialogueService.GenerateDialogues(prompt);
        //TODO: Generate Voice

        Video video = new Video();
        List<String> VoiceList = new ArrayList<>();

        for (String i : DialogueResult){
            if(!i.contains("*WAIT FOR RESPONSE*")){
                File audioFile = new File(voiceService.getVoice(i));
                if(audioFile.exists()){
                    video.addAudioToSequence(new Voice(audioFile, i));
                    VoiceList.add(i);
                }
            }else{
                video.addPauseToSequence();
            }
        }



        //TODO: Generate Video

        return new Reel(DialogueResult, VoiceList, video);
    }

}
