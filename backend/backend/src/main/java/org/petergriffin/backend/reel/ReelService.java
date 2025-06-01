package org.petergriffin.backend.reel;

import org.petergriffin.backend.dialogue.DialogueService;
import org.petergriffin.backend.prompt.Prompt;
import org.petergriffin.backend.voice.Voice;
import org.petergriffin.backend.voice.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReelService {

    @Autowired
    private DialogueService dialogueService;

    @Autowired
    private VoiceService voiceService;

    public Reel createReel(Prompt prompt) throws IOException {
        List<String> DialogueResult = dialogueService.GenerateDialogues(prompt);
        //TODO: Generate Voice

        List<String> VoiceList = new ArrayList<>();

        for (String i : DialogueResult){
            if(i.contains("*WAIT FOR RESPONSE*")){
                VoiceList.add(voiceService.getVoice(i));
            }
        }

        //TODO: Generate Video
        return new Reel(DialogueResult, VoiceList );
    }

}
