import React, { useState, useEffect } from "react";
import "./InputForm.css";
import Loading from "./Loading";
import Quiz from "./Quiz";
import Welcome from "./Welcome";
import * as pdfjsLib from 'https://mozilla.github.io/pdf.js/build/pdf.mjs';

pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://mozilla.github.io/pdf.js/build/pdf.worker.mjs';

function InputForm() {
  const [getStarted, setGetStarted] = useState(false);

  const [paragraph, setParagraph] = useState("");
  const [fileContent, setFileContent] = useState(null);
  const [submittedData, setSubmittedData] = useState(null);
  const [submitMessage, setSubmitMessage] = useState("");
  const [isFormSubmitted, setIsFormSubmitted] = useState(false);
  const [fileUrl, setFileUrl] = useState(null);

  function handleParagraphChange(e) {
    setParagraph(e.target.value);
  }

  function handleFileChange(e) {
    const file = e.target.files[0];
    if (file) {
      setFileContent(file);
      const url = URL.createObjectURL(file);
      setFileUrl(url);
    } else {
      setFileContent(null);
    }
  }

  function extractText (pdfUrl){
    var pdf = pdfjsLib.getDocument(pdfUrl);
    return pdf.promise.then(function (pdf){
        let totalPageCount = pdf.numPages;
        let countPromises = [];
        for (let currentPage = 1; currentPage <= totalPageCount; currentPage++){
          let page = pdf.getPage(currentPage);
          countPromises.push(
            page.then(function (page){
              let textContent = page.getTextContent();
              return textContent.then(function (text){
                return text.items.map(function (s){
                  return s.str;
                })
                .join('');
              });
            }),
          );
        }
        return Promise.all(countPromises).then(function (texts){
          return texts.join('');
        });
      });
  }

  function handleSubmit(e) {
    e.preventDefault();
    if (!paragraph.trim() && !fileContent) {
      setSubmitMessage("Please enter a paragraph or a file before submitting.");
      return;
    }
    setSubmittedData({
      paragraph,
      fileContent,
    });
    setSubmitMessage("Form submitted successfully!");
    setIsFormSubmitted(true);
  }


  useEffect(() => {
    if (submittedData) {
      console.log("Submitted Data:", submittedData);
      if (fileUrl){
        console.log("file url:" + fileUrl);
        extractText(fileUrl).then(
          function (text) {
            console.log('Extracted text:\n' + text);
          },
          function (reason) {
            console.error(reason);
          },
        );
      }
    }
  }, [submittedData,fileUrl]);

  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    getDataFromBackend();
  }, [submittedData])

  async function getDataFromBackend() {
    //set the state isLoading = true before making the network call
    setIsLoading(true);
    //make the network call here

    //reset the state isLoading = false when network call has finished
    setIsLoading(false);
  }

  if (isFormSubmitted) { 
    return (
      isLoading 
      ? <Loading /> 
      : <div className="app-container">
          <Quiz 
            setIsFormSubmitted={setIsFormSubmitted} 
            setParagraph={setParagraph} 
            setFileContent={setFileContent} 
            setFileUrl={setFileUrl}
            setSubmittedData={setSubmittedData}
            setSubmitMessage={setSubmitMessage}
          />
        </div>
    ); }

  if (!getStarted) {
    return (<Welcome setGetStarted={setGetStarted} />)
  }
  
  return (
      <div className="container">
        <div className="flex flex-row">
          <h1>Upload your study materials and let Peter quiz you!</h1>
          <img src="src/assets/peter-griffin1.png" className="mb-4 w-20" alt="Peter Griffin" />
        </div>
        <form onSubmit={handleSubmit}>
          <label htmlFor="paragraphInput">Enter your paragraph:</label>
          <textarea
            id="paragraphInput"
            value={paragraph}
            onChange={handleParagraphChange}
            placeholder="Type your paragraph here..."
          />

          <label htmlFor="fileUpload" className="text-white">Upload a file:</label>
          <div className="border border-[#bdc3c7] rounded-lg p-2 bg-white">
            <input
              id="fileUpload"
              type="file"
              multiple
              accept=".jpg,.jpeg,.png,.pdf,.ppt,.pptx,.doc,.docx"
              onChange={handleFileChange}
            />
          </div>
          
          <button type="submit">Submit</button>
        </form>

        {submitMessage && (
          <p style={{ marginTop: "20px", color: submitMessage.includes("successfully") ? "green" : "red" }}>
            {submitMessage}
          </p>
        )}
      </div>
    
  );
}

export default InputForm;
