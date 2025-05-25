package org.petergriffin.backend.reel;

import org.petergriffin.backend.dialogue.DialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/reel")
public class ReelController {

    private final ReelService reelService;

    @Autowired
    public ReelController(ReelService reelService, DialogueService dialogueService) {
        this.reelService = reelService;
    }

    @GetMapping(path = "/generate_reel")
    public Reel generateReel() {
        Reel result = reelService.createReel("Test notes"); //Notes be received from the body of the API call
        return result;
    }

}
