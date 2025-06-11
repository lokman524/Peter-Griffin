import { useState, useEffect, useRef } from "react";

function Video (){

    const sampleWords = [
        {
            "word": "The",
            "start": 0.0,
            "end": 0.18
        },
        {
            "word": "text",
            "start": 0.18,
            "end": 0.42
        },
        {
            "word": "provided",
            "start": 0.42,
            "end": 0.8999999999999999
        },
        {
            "word": "appears",
            "start": 0.8999999999999999,
            "end": 1.3199999999999998
        },
        {
            "word": "to",
            "start": 1.3199999999999998,
            "end": 1.44
        },
        {
            "word": "be",
            "start": 1.44,
            "end": 1.56
        },
        {
            "word": "a",
            "start": 1.56,
            "end": 1.62
        },
        {
            "word": "jumbled",
            "start": 1.62,
            "end": 2.04
        },
        {
            "word": "collection",
            "start": 2.04,
            "end": 2.64
        },
        {
            "word": "of",
            "start": 2.64,
            "end": 2.7600000000000002
        },
        {
            "word": "words",
            "start": 2.7600000000000002,
            "end": 3.06
        },
        {
            "word": "and",
            "start": 3.06,
            "end": 3.24
        },
        {
            "word": "phrases,",
            "start": 3.24,
            "end": 3.72
        },
        {
            "word": "likely",
            "start": 3.72,
            "end": 4.08
        },
        {
            "word": "due",
            "start": 4.08,
            "end": 4.26
        },
        {
            "word": "to",
            "start": 4.26,
            "end": 4.38
        },
        {
            "word": "a",
            "start": 4.38,
            "end": 4.4399999999999995
        },
        {
            "word": "technical",
            "start": 4.4399999999999995,
            "end": 4.9799999999999995
        },
        {
            "word": "issue",
            "start": 4.9799999999999995,
            "end": 5.279999999999999
        },
        {
            "word": "or",
            "start": 5.279999999999999,
        }]

    const videoRef = useRef(null);
    const [currentTime, setCurrentTime] = useState(0);

    useEffect(() => {
        const video = videoRef.current;
        video.play().catch(error => {
            console.warn("Video error:" + error);
        })
    })

    useEffect(() => {
        const video = videoRef.current;
        const updateTime = () => setCurrentTime(video.currentTime);
        video.addEventListener('timeupdate', updateTime);
        return () => video.removeEventListener('timeupdate', updateTime);
    })

    function getIndex (currentTime, wordData){
        for (let item of wordData){
            if(currentTime >= item.start && currentTime <= item.end)
                return wordData.indexOf(item);
        }
        return -1;
    }

    const index = getIndex(currentTime, sampleWords);

    return (
        <div>
            <video ref={videoRef} src="src/assets/Sample video.mp4"></video>
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
                    {(index === -1 ? "" : sampleWords[index].word)}
                </div>
        </div> 
    )
}

export default Video;