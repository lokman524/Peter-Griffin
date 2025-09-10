import { useState } from "react";
import Video from "./Video"
import Results from "./results";
import "./Quiz.css";



function Quiz ({
        setIsFormSubmitted, 
        setParagraph,
        setFileContent,
        setFileUrl,
        setSubmittedData,
        setSubmitMessage}){


    function restartQuiz (){
        setIsFormSubmitted(false);
        setParagraph("");
        setFileContent(null);
        setFileUrl(null);
        setSubmitMessage("");
        setSubmittedData(null);
    }


    return (
        <div>
            <Video restartQuiz={restartQuiz}/>
        </div>

    )
}

export default Quiz;
