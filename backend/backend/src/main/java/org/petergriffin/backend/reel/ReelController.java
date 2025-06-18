package org.petergriffin.backend.reel;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.petergriffin.backend.dialogue.DialogueService;
import org.petergriffin.backend.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping(path = "api/v1/reel")
public class ReelController {

    private final ReelService reelService;

    @Autowired
    public ReelController(ReelService reelService, DialogueService dialogueService) {
        this.reelService = reelService;
    }

    /**
     *
     * @param prompt
     * @return Response of the following body format
     * {
     *     dialogues : [
     *          "Dialogue 1",
     *          "Dialogue 2",
     *          ...
     *     ],
     *     videoData :{
     *         sequence : {
     *             sequenceElements : [{
     *                 content: {
     *                  `   transcription   : "The original dialogue",
     *                      wordSequence    : {
     *                          wordSequenceElements : [{
     *                              word    : "Word to show in the subtitle",
     *                              start   : (start time)
     *                              end     : (end time)
     *                          }, ...
     *                          ]
     *                      }
     *                      audioData`      : (audio data represented in bytes)
     *                 }
     *             },{
     *                 content: {
     *                      "content" : "pause",
     *                      duration : duration of pause (should be 5)
     *                 }
     *             }]
     *         }
     *     }
     * }
     */
    @PostMapping(path = "/generate_reel")
    public Reel generateReel(@RequestBody Prompt prompt){
        try{
            return reelService.createReel(prompt); //Notes be received from the body of the API call
        } catch (Exception e){
            System.out.println(e.getStackTrace().toString());
            return null;
        }
    }

}
