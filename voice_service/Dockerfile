FROM python:3.11-slim

WORKDIR /app

# Install system dependencies
RUN apt-get update && apt-get install -y git\
    libsndfile1 \
    && rm -rf /var/lib/apt/lists/*

# Copy requirements file
COPY requirements.txt .

# Install Python dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy application code
COPY main.py .

# Copy reference audio file (assuming ref.wav exists)
COPY ref.wav .

# Expose port
EXPOSE 8000

# Command to run the FastAPI application
CMD yes | uvicorn main:app --host 0.0.0.0 --port 8000