package org.petergriffin.backend.voice;

import org.petergriffin.backend.sequence.Sequence;
import org.petergriffin.backend.sequence.SequenceElement;
import org.petergriffin.backend.sequence.WordSeqence;
import org.petergriffin.backend.sequence.WordSequenceElement;

import java.io.File;
import java.io.IOException;

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
        // Execute Python script with Vosk/Whisper/Gentle
        ProcessBuilder pb = new ProcessBuilder(
                "python",
                "align_audio.py",
                audioFile.getAbsolutePath(),
                transcript
        );

        Process process = pb.start();
        String output = new String(process.getInputStream().readAllBytes());

        // Parse JSON output
        return parseAlignmentOutput(output);
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
