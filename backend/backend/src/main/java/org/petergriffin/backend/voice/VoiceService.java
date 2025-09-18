package org.petergriffin.backend.voice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class VoiceService {

    @Value("${app.api.voice-url}")
    private String VOICE_URL;

    @Value("${app.api.voice-storage-path}")
    private String VOICE_STORAGE_PATH;

    private final RestTemplate restTemplate;

    public VoiceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Getting a TTS voice with a string and returns the filename
    public String getVoice(String text) throws IOException {

        String filename = UUID.randomUUID().toString() + ".mp3";
        Path filePath = Paths.get(VOICE_STORAGE_PATH, filename);

        byte[] audioData;

        // Call TTS API
        System.out.println("Calling TTS with the following text:" + text);

        Text body = new Text(text);

        try{
            audioData = restTemplate.postForEntity(
                    VOICE_URL,
                    body,
                    byte[].class);
            // Save to filesystem
                Files.write(filePath, audioData);
                return filename;
        }catch (Exception e){
            System.out.println(e.getMessage() + "\n" + e.getStackTrace().toString());
        }

        return null;

    }


    public <Resource> Resource getAudioResource(String filename) throws FileNotFoundException {
        Path filePath = Paths.get(VOICE_STORAGE_PATH, filename);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("Audio file not found");
        }
        return (Resource) new FileSystemResource(filePath.toFile());
    }

    private String encodeText(String text) {
        // URL encode the text to handle special characters
        return java.net.URLEncoder.encode(text, StandardCharsets.UTF_8);
    }

    public void deleteAudioFromList(List<String> list) throws IOException{
        for (String i : list){
            Files.delete(Path.of(VOICE_STORAGE_PATH + "/" + i));
        }
    }

}
