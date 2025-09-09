import { useState, useEffect, useRef } from "react";
import sample from "../assets/sample.json";
import WavToMp3Component from "./WavToMp3Component";

function Video (){
    // Used to traverse through sequenceElements in json file
    const [sequenceElementsCounter, setSequenceElementsCounter]  = useState(0);
    // Timer for each sequenceElement
    const [timer, setTimer] = useState(0);
    
    // Increment timer every 0.01s
    useEffect (() => {
        const interval = setInterval(() => {
            setTimer(prev => prev + 0.01);
        },10)
        return () => clearInterval(interval);
    }, [])
    
    // Log the json file structure
    useEffect(() => {
        try {
            console.log(sample.videoData);
        } catch (error) {
            console.error(error);
        }
    }, [])

    let sequenceElements;
    let audioDataBase64;
    try {
        sequenceElements = sample.videoData.sequence.sequenceElements;
        audioDataBase64 = sample.videoData.sequence.sequenceElements[0].content.audioData;
    } catch (error) {
        console.error(error);
    }

    // Display words onto video using timer and sequenceElementsCounter
    const [index, setIndex] = useState(-1);
    useEffect (() => {
        try {
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
        } catch (error) {
            console.error(error);
        }
    }, [timer])

    const videoRef = useRef(null);

    //Play the video
    useEffect(() => {
        const video = videoRef.current;
        video.play().catch(error => {
            console.warn("Video error:" + error);
        })
    }, [])

    // Get index of word to be displayed
    function getIndex (currentTime, wordData){
        for (let item of wordData){
            if(currentTime >= item.start && currentTime <= item.end)
                return wordData.indexOf(item);
        }
        return -1;
    }

    //const index = getIndex(currentTime, sampleWords);

    return (
        <div>
            <div style={{ fontSize: '20px', fontWeight: 'bold', marginBottom: '10px' }}>
                Timer: {timer.toFixed(2)}s
            </div>
            <video ref={videoRef} src="src/assets/Sample video.mp4"></video>
            <WavToMp3Component audioDataBase64={audioDataBase64} />
                <div
                    style={{
                        position: 'absolute',
                        bottom: '60px',
                        left: '50%',
                        transform: 'translateX(-50%)',
                        backgroundColor: 'rgba(0,0,0,0.7)',
                        color: 'white',
                        padding: '5px 10px',
                        borderRadius: '5px',
                    }}
                >
                    {(sequenceElements && (index === -1 ? "" : sequenceElements[sequenceElementsCounter].content.wordSeqence.wordSequenceElements[index].word))}
                </div>
        </div> 
    )
}

export default Video;