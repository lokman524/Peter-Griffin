from fastapi import FastAPI, HTTPException
from fastapi.responses import FileResponse
from pydantic import BaseModel
import torch
from TTS.api import TTS
import os
from pathlib import Path

app = FastAPI()

# Get device
device = "cuda" if torch.cuda.is_available() else "cpu"

# Initialize TTS
tts = TTS("tts_models/multilingual/multi-dataset/xtts_v2").to(device)

# Define request model
class TextInput(BaseModel):
    text: str

@app.post("/get_voice")
async def get_voice(text_input: TextInput):
    try:
        # Define output file path
        output_file = "output.wav"
        
        # Generate speech
        tts.tts_to_file(
            text=text_input.text,
            speaker_wav="ref.wav",  # Assuming ref.wav exists in the same directory
            language="en",
            file_path=output_file
        )
        
        # Check if file was created
        if not os.path.exists(output_file):
            raise HTTPException(status_code=500, detail="Failed to generate audio file")
            
        # Return the audio file
        return FileResponse(
            path=output_file,
            media_type="audio/wav",
            filename="output.wav"
        )
        
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Error generating speech: {str(e)}")