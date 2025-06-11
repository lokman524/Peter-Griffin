import whisper
import json
import sys
import os

def get_word_timestamps(audio_path):
    print(os.listdir())
    model = whisper.load_model("base")
    result = model.transcribe('audio.wav', word_timestamps=True)

    words = ""
    for segment in result["segments"]:
        for word in segment["words"]:
            words += word["word"].strip() + "/" + str(word["start"]) + "/" + str(word["end"]) + "\n"

    return words

if __name__ == "__main__":
    audio_path = sys.argv[1]
    timestamps = get_word_timestamps(audio_path)
    print(timestamps)