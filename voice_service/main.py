from fastapi import FastAPI, HTTPException, Query
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

@app.get("/")
async def get_voice_or_root(
    text: str = Query(None, description="Text to convert to speech"),
    speaker_id: str = Query(default="", description="Speaker ID (optional)"),
    style_wav: str = Query(default="", description="Style wav (optional)"),
    language_id: str = Query(default="en", description="Language ID")
):
    """Root endpoint that handles both status and TTS requests"""
    
    # If no text provided, return status
    if text is None:
        return {"message": "TTS API is running", "status": "healthy"}
    
    # If text provided, generate TTS
    try:
        # Define output file path
        output_file = "output.wav"
        
        # Generate speech
        tts.tts_to_file(
            text=text,
            speaker_wav="ref.wav",  # Using the reference wav file
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

@app.get("/health")
async def health_check():
    return {"status": "healthy", "device": device}

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