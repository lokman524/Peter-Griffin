package org.petergriffin.backend.dialogue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.azure.ai.inference.ChatCompletionsClient;
import com.azure.ai.inference.ChatCompletionsClientBuilder;
import com.azure.ai.inference.models.ChatCompletions;
import com.azure.ai.inference.models.ChatCompletionsOptions;
import com.azure.ai.inference.models.ChatRequestMessage;
import com.azure.ai.inference.models.ChatRequestSystemMessage;
import com.azure.ai.inference.models.ChatRequestUserMessage;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.Configuration;


import org.petergriffin.backend.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DialogueService {

    @Value("${app.api.github-token}")
    private String key;

    public List<String> GenerateDialogues(Prompt prompt){

        String endpoint = "https://models.github.ai/inference";
        String model = "openai/gpt-4o-mini";

        ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .buildClient();

        List<ChatRequestMessage> chatMessages = Arrays.asList(
                new ChatRequestSystemMessage(prompt.getPromptSettings()),
                new ChatRequestUserMessage(prompt.getPromptContent())
        );

        ChatCompletionsOptions chatCompletionsOptions = new ChatCompletionsOptions(chatMessages);
        chatCompletionsOptions.setModel(model);

        //Faced a problem where it was showing 401 unauthorized. FIXED: fuckhead didnt say we should use beta 5 of azure
        ChatCompletions completions = client.complete(chatCompletionsOptions);

        String resultingText = completions.getChoices().get(0).getMessage().getContent();

        System.out.printf("%s.%n", resultingText);

        List<String> dialogues = new ArrayList<>();

        dialogues.addAll(Arrays.asList(resultingText.split("/")));

        return dialogues;

    }

}
