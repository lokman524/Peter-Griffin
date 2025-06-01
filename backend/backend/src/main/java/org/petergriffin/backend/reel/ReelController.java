package org.petergriffin.backend.reel;

import org.petergriffin.backend.dialogue.DialogueService;
import org.petergriffin.backend.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(path = "api/v1/reel")
public class ReelController {

    private final ReelService reelService;

    @Autowired
    public ReelController(ReelService reelService, DialogueService dialogueService) {
        this.reelService = reelService;
    }

    @PostMapping(path = "/generate_reel")
    public Reel generateReel(@RequestBody Prompt prompt) throws IOException {
        Reel result = reelService.createReel(prompt); //Notes be received from the body of the API call
        return result;
    }

}
