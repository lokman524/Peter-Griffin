package org.petergriffin.backend.reel;

import org.petergriffin.backend.dialogue.DialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class ReelService {

    @Autowired
    private DialogueService dialogueService = new DialogueService();

    public Reel createReel(String prompt){
        List<String> result = dialogueService.GenerateDialogues(prompt);

        //TODO: Generate Voice
        //TODO: Generate Video
        return new Reel(result);
    }

}
