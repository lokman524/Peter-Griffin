import React, { useState , useEffect} from "react";
import "./InputForm.css";
import Loading from "./Loading";

function InputForm() {
  const [paragraph, setParagraph] = useState("");
  const [fileContent, setFileContent] = useState(null);
  const [submittedData, setSubmittedData] = useState(null);
  const [submitMessage, setSubmitMessage] = useState("");
  const [isFormSubmitted, setIsFormSubmitted] = useState(false);

  function handleParagraphChange(e) {
    setParagraph(e.target.value);
  }

  function handleFileChange(e) {
    const file = e.target.files;
    if (file) {
        setFileContent(file)
    }
    else
        setFileContent(null);
    
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
    }
  }, [submittedData]);

  if (isFormSubmitted){
    return (<Loading />);
  }

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
