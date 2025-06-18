package org.petergriffin.backend.voice;

import org.petergriffin.backend.sequence.Sequence;
import org.petergriffin.backend.sequence.SequenceElement;
import org.petergriffin.backend.sequence.WordSeqence;
import org.petergriffin.backend.sequence.WordSequenceElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Voice {

    private final File audioFile;
    private final String transcription;
    private WordSeqence wordSeqence;
    private byte[] audioData;
    /**
     * Word Sequence will be in the following format:
     * [{
     *     content: word to be transcribed,
     *     start: local start time
     *     end: local end time
     * }]
     */

    public Voice( File audioFile, String transcription) throws IOException, InterruptedException {
        this.audioFile = audioFile;
        this.transcription = transcription;
        wordSeqence = getWordTimeStamps(audioFile);

        audioData = Files.readAllBytes(audioFile.toPath());

        //Delete file after
        Files.delete(audioFile.toPath());

    }

    private WordSeqence getWordTimeStamps(File audioFile) throws IOException, InterruptedException {

        // process builder will run the venv and the script at the same time
        ProcessBuilder pb = new ProcessBuilder(
                ".\\venv\\Scripts\\activate.bat",
                "&&" ,
                "python",
                "src/main/python/whisper_aligner.py",
                audioFile.getAbsolutePath()
        );

        pb.redirectErrorStream(true);
        Process process = pb.start();
        int exitCode = process.waitFor();
        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        System.out.println("Output from Python Aligner with exit code "+ exitCode+" :\n" + output);

        // Parse JSON output to List<WordTimestamp>
        return parseAlignmentOutput(output);
    }

    //Format: Word/start_time/end
    private WordSeqence parseAlignmentOutput(String output) {
        String[] lines = output.split("\n");
        WordSeqence result  = new WordSeqence();
        //starts from line 2
        for (int i = 2; i < lines.length ; i++){
            String[] wordData = lines[i].split("/");
            if (wordData.length < 3){
                break;
            }
            System.out.println("Processing: " + lines[i]);
            result.addToWordSequence(new WordSequenceElement(
                    wordData[0],
                    Float.parseFloat(wordData[1]),
                    Float.parseFloat(wordData[2])
            ));
        }

        return result;
    }

//    public File getAudioFile() {
//        return audioFile;
//    }

    public String getTranscription() {
        return transcription;
    }

    public WordSeqence getWordSeqence() {
        return wordSeqence;
    }

    public void setWordSeqence(WordSeqence wordSeqence) {
        this.wordSeqence = wordSeqence;
    }

    public byte[] getAudioData() {
        return audioData;
    }


}
