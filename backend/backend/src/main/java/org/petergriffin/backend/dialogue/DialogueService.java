package org.petergriffin.backend.dialogue;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DialogueService {

    public List<String> GenerateDialogues(String prompt){
        List<String> dialogues = new ArrayList<String>();

        //TODO: Result should be fetched from API call to Gemini and parsed to seperated array elements
        dialogues.add("Dialogues!!!!!");
        return dialogues;

    }

}
