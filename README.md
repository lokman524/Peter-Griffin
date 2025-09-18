# Peter Griffin - Text-to-Speech Video Generator

A full-stack application that converts text content into spoken dialogue with synchronized video output, featuring the voice of Peter Griffin from Family Guy.

## Features

- **PDF Text Extraction**: Upload PDF documents and extract text content
- **Text-to-Speech**: Convert text to speech using Coqui TTS with Peter Griffin's voice
- **Video Generation**: Create synchronized video with the generated speech
- **Web Interface**: User-friendly React-based frontend
- **RESTful API**: Spring Boot backend for processing requests

## Tech Stack

- **Frontend**: React, Vite
- **Backend**: Spring Boot (Java)
- **Text-to-Speech**: Coqui TTS
- **Containerization**: Docker, Docker Compose
- **Build Tools**: Gradle (Backend), npm (Frontend)

## Getting Started

### Prerequisites

- Docker and Docker Compose
- Java 17 or higher
- Node.js 16+ and npm

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/Peter-Griffin.git
   cd Peter-Griffin
   ```

2. Start the application with Docker Compose:
   ```bash
   docker-compose up -d
   ```

3. The application will be available at `http://localhost:3000`

### Development

#### Backend

The backend is a Spring Boot application located in the `backend` directory.

```bash
cd backend/backend
./gradlew bootRun
```

#### Frontend

The frontend is a React application in the `frontend` directory.

```bash
cd frontend
npm install
npm run dev
```

## API Endpoints

- `GET /` - Health check endpoint
- `POST /api/process` - Process text and generate video
- `GET /api/status/{id}` - Check status of video generation

## Project Structure

```
.
├── backend/                 # Spring Boot backend
│   ├── src/
│   │   ├── main/java/org/petergriffin/backend/
│   │   │   ├── config/     # Configuration classes
│   │   │   ├── dialogue/   # Dialogue management
│   │   │   ├── reel/       # Video reel generation
│   │   │   ├── sequence/   # Text sequence processing
│   │   │   ├── video/      # Video processing
│   │   │   └── voice/      # Voice processing
│   │   └── resources/      # Configuration files
├── frontend/               # React frontend
│   ├── public/             # Static files
│   └── src/
│       ├── components/     # React components
│       ├── App.jsx         # Main App component
│       └── main.jsx        # Entry point
└── compose.yaml            # Docker Compose configuration
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.

## Acknowledgments

- Coqui TTS for the text-to-speech engine
- Mozilla's PDF.js for PDF text extraction

### Default Enviornment Variables in Backend

VOICE_URL = http://127.0.0.1:8000/get_voice
STORAGE_PATH = "./"