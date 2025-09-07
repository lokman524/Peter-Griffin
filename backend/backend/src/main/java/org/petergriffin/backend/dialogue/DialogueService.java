package org.petergriffin.backend.dialogue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.json.JSONArray;


import org.petergriffin.backend.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DialogueService {

    @Value("${app.api.openrouter-token}")
    private String key;

    public List<String> GenerateDialogues(Prompt prompt) throws Exception {

        try{
            /* Deprecated code using GITHUB MODELS
            String endpoint = "https://models.github.ai/inference";
            String model = "openai/gpt-4o-mini";

            System.out.println("Authorizing");
            ChatCompletionsClient client = new ChatCompletionsClientBuilder()
                    .credential(new AzureKeyCredential(key))
                    .endpoint(endpoint)
                    .buildClient();

            System.out.println("Authorized!!!!");;

            System.out.println("Initializing Chat Prompt");
            List<ChatRequestMessage> chatMessages = Arrays.asList(
                    new ChatRequestSystemMessage(prompt.getPromptSettings()),
                    new ChatRequestUserMessage(prompt.getPromptContent())
            );

            System.out.println("Selecting Model");
            ChatCompletionsOptions chatCompletionsOptions = new ChatCompletionsOptions(chatMessages);
            chatCompletionsOptions.setModel(model);

            //Faced a problem where it was showing 401 unauthorized. FIXED: fuckhead didnt say we should use beta 5 of azure
            System.out.println("Generating dialogues");
            ChatCompletions completions = client.complete(chatCompletionsOptions);

            System.out.println("Completed");
            String resultingText = completions.getChoices().get(0).getMessage().getContent();

            System.out.printf("%s.%n", resultingText);
             */


            URL url = new URL("https://openrouter.ai/api/v1/chat/completions");

            System.out.println("Using API Token: " + key);

            // Create the connection
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + key);
            conn.setDoOutput(true);

            // Create the JSON payload
            JSONObject payload = new JSONObject();
             payload.put("model", "deepseek/deepseek-chat-v3.1:free");
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "system");
            message.put("content", prompt.getPromptSettings());
            message.put("role", "user");
            message.put("content", prompt.getPromptContent());
            messages.put(message);
            payload.put("messages", messages);

            // Send the request
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = payload.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the response
            int responseCode = conn.getResponseCode();
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            responseCode >= 200 && responseCode < 300
                                    ? conn.getInputStream()
                                    : conn.getErrorStream()))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine);
                }
            }

            // Output the response
            System.out.println("Response Code: " + responseCode);
            System.out.println("Response: " + response.toString());

            if (responseCode != 200) {
                throw new Exception("Response code: " + responseCode + ", response: " + response);
            }

            String prasedResposne = "";
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> jsonResponse = mapper.readValue(response.toString(), Map.class);
            List<Map<String, Object>> choices = (List<Map<String, Object>>) jsonResponse.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> messageObj = (Map<String, Object>) choices.get(0).get("message");
                String content = (String) messageObj.get("content");
                System.out.println("Assistant Response: " + content);
                prasedResposne = content;
            } else {
                System.out.println("No choices found in response");
            }

            List<String> dialogues = new ArrayList<>(Arrays.asList(response.toString().split("/")));

            // Close the connection
            conn.disconnect();

            return dialogues;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
