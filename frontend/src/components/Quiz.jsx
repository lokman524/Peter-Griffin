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

    //Sample questions
    const questionBank = [
    {
      question: "What is the capital of France?",
      options: ["Berlin", "London", "Paris", "Rome"],
      answer: "Paris",
    },
    {
      question: "Which language is used for web apps?",
      options: ["PHP", "Python", "Javascript", "All"],
      answer: "All",
    },
    {
      question: "Who is the current CEO of Tesla?",
      options: ["Jeff Bezos", "Elon Musk", "Bill Gates", "Tony Stark"],
      answer: "Elon Musk",
    },
    ];

    const initialAnswers = questionBank.map(() => null);

    const [userAnswers, setUserAnswers] = useState(initialAnswers);
    const [currentQuestion, setCurrentQuestion] = useState(0);

    const [isQuizFinished, setIsQuizFinished] = useState(false);

    const selectedAnswer = userAnswers[currentQuestion];

    function handleSelectedOption(option){
        const newUserAnswers = [...userAnswers];  
        newUserAnswers[currentQuestion] = option;
        setUserAnswers(newUserAnswers);
    }
  
    function goToNext (){
        if (currentQuestion === questionBank.length - 1)
            setIsQuizFinished(true);
        else 
            setCurrentQuestion(currentQuestion + 1);
    }

    function goToPrev (){
        if (currentQuestion > 0)
            setCurrentQuestion(currentQuestion - 1);
    }

    function restartQuiz (){
        setUserAnswers(initialAnswers);
        setCurrentQuestion(0);
        setIsQuizFinished(false);
        setIsFormSubmitted(false);
        setParagraph("");
        setFileContent(null);
        setFileUrl(null);
        setSubmitMessage("");
        setSubmittedData(null);
    }

    if (isQuizFinished) {
        return <Results userAnswers={userAnswers} questionBank={questionBank} restartQuiz={restartQuiz}/>;
    }

    return (
        <div>
            <Video />
            <h2>Question {currentQuestion+1}</h2>
            <p className="question">{questionBank[currentQuestion].question}</p>
            {questionBank[currentQuestion].options.map((option) => {
                return <button className={"option" + (selectedAnswer === option ? " selected" : "")} onClick={() => handleSelectedOption(option)}>{option}</button>
            })}
            <div className="nav-buttons">
                <button onClick={goToPrev} disabled={currentQuestion === 0} >Previous</button>
                <button onClick={goToNext} disabled={selectedAnswer === null}>{currentQuestion === questionBank.length - 1 ? "Finish Quiz" : "Next"}</button>
            </div>
        </div>

    )
}

export default Quiz;
