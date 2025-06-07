import React, { useState, useEffect } from "react";
import "./InputForm.css";
import Loading from "./Loading";
import Quiz from "./Quiz";
import * as pdfjsLib from 'https://mozilla.github.io/pdf.js/build/pdf.mjs';

pdfjsLib.GlobalWorkerOptions.workerSrc = 'https://mozilla.github.io/pdf.js/build/pdf.worker.mjs';

function InputForm() {
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

  // Uncomment this if you want to show a loading component after submission
  //if (isFormSubmitted) { return (<Loading />); }
  if (isFormSubmitted) { 
    return (
      <div className="app-container">
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

  return (
    <div className="container">
      <h1>User Input Form</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="paragraphInput">Enter your paragraph:</label>
        <textarea
          id="paragraphInput"
          value={paragraph}
          onChange={handleParagraphChange}
          placeholder="Type your paragraph here..."
        />

        <label htmlFor="fileUpload">Upload a file:</label>
        <input
          id="fileUpload"
          type="file"
          multiple
          accept=".jpg,.jpeg,.png,.pdf,.ppt,.pptx,.doc,.docx"
          onChange={handleFileChange}
        />

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
