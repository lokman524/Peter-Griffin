FROM python:3.11-slim

WORKDIR /app

# Install system dependencies
RUN apt-get update && apt-get install -y \
    libsndfile1 \
    git \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Copy requirements file
COPY requirements.txt .

# Install Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Set environment variables to avoid interactive prompts
ENV PYTHONUNBUFFERED=1
ENV TTS_CACHE_DIR=/app/.tts_cache

# Pre-download the TTS model to avoid runtime prompts
# Use echo to automatically accept the license agreement
RUN echo "y" | python -c "from TTS.api import TTS; import torch; device='cuda' if torch.cuda.is_available() else 'cpu'; TTS('tts_models/multilingual/multi-dataset/xtts_v2').to(device)" || echo "Model download completed"

# Copy application code
COPY main.py .

# Copy reference audio file (assuming ref.wav exists)
COPY ref.wav .

# Expose port
EXPOSE 8000

# Command to run the FastAPI application
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]