import { useState, useEffect, useRef } from "react";
import sample from "../assets/sample.json";

function Video ({restartQuiz}) {
    // Used to traverse through sequenceElements in json file
    const [sequenceElementsCounter, setSequenceElementsCounter]  = useState(0);
    // Timer for each sequenceElement
    const [timer, setTimer] = useState(0);
    // Play state for quiz video and audio
    const [play, setPlay] = useState(false);

    // Increment timer every 0.01s if play is true
    useEffect (() => {
        if (play) {
            const interval = setInterval(() => {
                setTimer(prev => prev + 0.01);
            },10)
            return () => clearInterval(interval);
        } else {
            const interval = setInterval(() => {
                setSequenceElementsCounter(0);
                setTimer(0);
            },10)
            return () => clearInterval(interval);
        }
    }, [play])

    // Log the json file structure
    useEffect(() => {
        try {
            console.log(sample.videoData);
        } catch (error) {
            console.error(error);
        }
    }, [])

    useEffect(() => {setSequenceElementsCounter(0)}, [])

    // Extract sequenceElements from json file
    let sequenceElements;
    try {
        sequenceElements = sample.videoData.sequence.sequenceElements;
    } catch (error) {
        console.error(error);
    }

    // Get index of word to be displayed
    function getIndex (currentTime, wordData){
        for (let item of wordData){
            if(currentTime >= item.start && currentTime <= item.end)
                return wordData.indexOf(item);
        }
        return -1;
    }

    // Display words onto video using timer and sequenceElementsCounter
    const [index, setIndex] = useState(-1);
    useEffect (() => {
        try {
            if (sequenceElementsCounter < sequenceElements.length) {
                if (sequenceElements[sequenceElementsCounter].content.content) {
                    setIndex(-1);
                    if (timer >= sequenceElements[sequenceElementsCounter].content.duration) {
                        setSequenceElementsCounter(prev => prev + 1);
                        setTimer(0);
                    }
                }
                else {
                    setIndex(getIndex(timer, sequenceElements[sequenceElementsCounter].content.wordSeqence.wordSequenceElements))
                    const wordSequenceLength = sequenceElements[sequenceElementsCounter].content.wordSeqence.wordSequenceElements.length;
                    if (timer >= sequenceElements[sequenceElementsCounter].content.wordSeqence.wordSequenceElements[wordSequenceLength - 1].end) {
                        setSequenceElementsCounter(prev => prev + 1);
                        setTimer(0);
                    }
                }
            } else {
                setPlay(false);
            }
        } catch (error) {
            console.error(error);
        }
    }, [timer])

    const videoRef = useRef(null);

    //Play the video
    function playVideo () {
        const video = videoRef.current;
        video.play().catch(error => {
            console.warn("Video error:" + error);
        })
    }

    const [audioData, setAudioData] = useState(null);

    // Set audio data when timer changes
    useEffect(() => {
      try {
        if (sequenceElements && 
            sequenceElementsCounter < sequenceElements.length && 
            sequenceElements[sequenceElementsCounter] &&
            sequenceElements[sequenceElementsCounter].content) {
          const currentElement = sequenceElements[sequenceElementsCounter];
          if (!currentElement.content.content && currentElement.content.audioData) {
            setAudioData(`data:audio/wav;base64,${currentElement.content.audioData}`);
            //console.log("Setting audio data, length:", currentElement.content.audioData.length);
          } else {
            setAudioData(null);
          }
        } else {
          setAudioData(null);
        }
      } catch (error) {
        console.error("Error setting audio data:", error);
        setAudioData(null);
      }
    }, [timer]); // Add sequenceElementsCounter as dependency
    
    // Play audio when sequenceElementsCounter changes
    useEffect(() => {
        try {
            if (audioData && play) {
                const audio = new Audio(audioData);
                audio.play().catch(error => {
                    console.error("Audio playback error:", error);
                });
            }
        } catch (error) {
            console.error("Audio creation error:", error);
        }
    }, [audioData]);

    // Because of how im dealing with audio playing and words displaying, the first audio clip will not play when play is pressed
    // This useEffect is a workaround to play the first audio clip when play is pressed
    // This is stupid but it works
    useEffect(() => {
        try {
            if (play && sequenceElements &&
            sequenceElementsCounter < sequenceElements.length &&
            sequenceElements[sequenceElementsCounter] &&
            sequenceElements[sequenceElementsCounter].content
            && sequenceElements[sequenceElementsCounter].content.audioData) {
                const audio = new Audio(`data:audio/wav;base64,${sequenceElements[0].content.audioData}`);
                audio.play().catch(error => {
                    console.error("Audio playback error:", error);
                });
            }
        } catch (error) {
            console.error("Audio creation error:", error);
        }
    }, [play]);

    // Track if the video has been played to control the play button text
    const [played, setPlayed] = useState(false);

    // Show/hide transcript
    // Let the user see the transcript of the video because im too lazy to make a pause button
    const [showTranscript, setShowTranscript] = useState(false);
    const [transcript, setTranscript] = useState("");

    // Extract transcript from json file
    useEffect (() => {
        let transcript = "";
        try {
            sequenceElements.map(element => {
                if (element.content.content) {
                    transcript += "\n*pause*\n";
                } else if (element.content.transcription) {
                    transcript += (element.content.transcription + "\n");
                }
            });
        } catch (error) {
            console.error(error);
        }
        setTranscript(transcript);
    }, [])

    return (
        <div>
            {/*  // Timer Display for debugging
            <div style={{ fontSize: '20px', fontWeight: 'bold', marginBottom: '10px' }}>
                Timer: {timer.toFixed(2)}s
            </div> */}
            <video className="w-full h-auto rounded-lg" ref={videoRef} src="src/assets/Sample video.mp4" 
                onEnded={() => {
                    if (play) {
                        const video = videoRef.current;
                        video.play();
                    }
                }}
            ></video>
            <div
                style={{
                    position: 'absolute',
                    top: '70%',
                    left: '50%',
                    transform: 'translateX(-50%)',
                    color: 'white',
                    padding: '5px 10px',
                    borderRadius: '5px',
                }}
                className="flex flex-col items-center justify-center text-center min-h-[100px] max-h-[200px]"
            >
                {(sequenceElements && (index === -1 
                    ? <p className="text-5xl">{""}</p> 
                    : <p 
                        className="text-5xl font-bebas-neue font-bold" 
                        style={{ textShadow: '-2px -2px 0 #000, 2px -2px 0 #000, -2px 2px 0 #000, 2px 2px 0 #000' }}
                      >
                        {sequenceElements[sequenceElementsCounter].content.wordSeqence.wordSequenceElements[index].word}
                      </p>
                    ))}
                <img src="src/assets/peter-griffin1.png" className=" w-70 " alt="Peter Griffin" />
            </div>
            
            <div className="flex flex-col items-center">
                <div className="flex justify-center">
                    <button  className="mx-2 my-2" disabled={play} onClick={() => { setPlay(true); setPlayed(true); playVideo()}}>{played ? "Play Again" : "Play"}</button>
                    <button className="mx-2 my-2" onClick={() => {setShowTranscript(!showTranscript); console.log(transcript)}}>Show Transcript</button>
                </div>
                {showTranscript && <p className="whitespace-pre-wrap bg-gray-200 rounded-3xl p-5 mx-2 my-2">{"Transcript:\n\n" + transcript}</p>}
                <button className="my-2 mx-2" onClick={restartQuiz}>Generate Another Quiz</button>
            </div>
            
        </div> 
    )
}

export default Video;