package org.petergriffin.backend.voice;

import org.petergriffin.backend.prompt.Prompt;
import org.petergriffin.backend.reel.Reel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/voice")
public class VoiceController {
    private final VoiceService voiceService;

    @Autowired
    public VoiceController(VoiceService voiceService){
        this.voiceService = voiceService;
    }

    @PostMapping(path = "/test_voice")
    public String testVoice(@RequestBody Prompt prompt) throws Exception{
        return voiceService.getVoice(prompt.getPromptContent());
    }
}
