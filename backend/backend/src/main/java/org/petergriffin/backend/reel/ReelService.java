package org.petergriffin.backend.reel;

import org.petergriffin.backend.dialogue.DialogueService;
import org.petergriffin.backend.prompt.Prompt;
import org.petergriffin.backend.video.Video;
import org.petergriffin.backend.voice.Voice;
import org.petergriffin.backend.voice.VoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public Reel createReel(Prompt prompt) throws IOException{
        List<String> DialogueResult = dialogueService.GenerateDialogues(prompt);
        //TODO: Generate Voice

        Video video = new Video();
        List<String> VoiceList = new ArrayList<>();
        List<String> AudioFiles = new ArrayList<>();

        for (String i : DialogueResult){
            if(!i.contains("*")){
                File audioFile = new File(voiceService.getVoice(i));
                if(audioFile.exists()){
                    try{
                        video.addAudioToSequence(new Voice(audioFile, i));
                        VoiceList.add(i);
                        AudioFiles.add(audioFile.getName());
                    }catch (Exception e){
                        Files.delete(audioFile.toPath());
                        System.out.println("Error From probably voice shi: " + e.getMessage());
                        for (StackTraceElement k : e.getStackTrace()){
                            System.out.println(k.toString());
                        }
                    }
                }
            }else{
                video.addPauseToSequence();
            }
        }

        //TODO: Generate Video
        for (String file : AudioFiles){
            Files.delete(Path.of(VOICE_STORAGE_PATH + "/" + file));
        }

        return new Reel(DialogueResult, VoiceList, video);
    }

}
