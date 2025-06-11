package org.petergriffin.backend.voice;

import org.petergriffin.backend.sequence.Sequence;
import org.petergriffin.backend.sequence.SequenceElement;
import org.petergriffin.backend.sequence.WordSeqence;
import org.petergriffin.backend.sequence.WordSequenceElement;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Voice {

    private final File audioFile;
    private final String transcription;
    private WordSeqence wordSeqence;

    public Voice( File audioFile, String transcription) throws IOException {
        this.audioFile = audioFile;
        this.transcription = transcription;
        wordSeqence = getWordTimeStamps(audioFile, transcription);
    }

    private WordSeqence getWordTimeStamps(File audioFile, String transcript) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(
                "python",
                "/app/python/whisper_aligner.py",
                audioFile.getAbsolutePath()
        );

        Process process = pb.start();
        String jsonOutput = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        // Parse JSON output to List<WordTimestamp>
        return parseAlignmentOutput(jsonOutput);
    }

    //Format: Word/start_time/end
    private WordSeqence parseAlignmentOutput(String output) {
        String[] lines = output.split("\n");
        WordSeqence result  = new WordSeqence();
        for (String i : lines){
            String[] wordData = i.split("/");
            result.addToWordSequence(new WordSequenceElement(
                    wordData[0],
                    Integer.parseInt(wordData[1]),
                    Integer.parseInt(wordData[2])
            ));
        }

        return result;
    }


    public File getAudioFile() {
        return audioFile;
    }

    public String getTranscription() {
        return transcription;
    }

    public WordSeqence getWordSeqence() {
        return wordSeqence;
    }

    public void setWordSeqence(WordSeqence wordSeqence) {
        this.wordSeqence = wordSeqence;
    }
}
