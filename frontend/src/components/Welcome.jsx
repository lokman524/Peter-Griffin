import React from "react";

function Welcome({ setGetStarted }) {
  return (
    <div className="min-h-screen bg-[#E6E6E6] flex flex-col">
      {/* Top horizontal line */}
      <div className="border-t-2 border-gray-700 w-full mb-8"></div>
      
      {/* Main content row */}
      <div className="flex max-md:flex-col flex-1 items-center px-8">
        <div className="border-l-2 border-black h-96 mx-8"></div>
        {/* Left column: Large heading */}
        <div className="flex-1 flex items-center justify-center">
          <p className="text-[150px] leading-none text-left font-jacques-francois-shadow">
            Learn<br />With<br />Peter
          </p>
        </div>
        {/* Center column: Image */}
        <div className="flex flex-col items-center justify-center flex-1">
          <img src="src/assets/peter-griffin1.png" className="mb-4" alt="Peter Griffin" />
        </div>
        {/* Right column: Description and button */}
        <div className="flex-1 flex flex-col gap-y-10 items-center justify-center">
          <p className="text-sm text-center max-w-xs mb-4 font-irish-grover">
            Hey, it's Peter Griffin! Instead of your boring ahh old notes, I'll quiz ya on your own study stuff. You drop in the text, I bring the laughs and the pop quiz. Learning's way less lame when I'm the teacher. Heh-heh, freakin' sweet!
          </p>
          <p className="text-sm text-center font-serif max-w-xs mb-8">
            Learn With Peter takes your lecture notes, textbooks, or any study material you provide and transforms them into an interactive video quiz—hosted by none other than Peter Griffin himself. Instead of staring at plain text, you’ll be laughing, learning, and testing your knowledge with a familiar voice guiding you through the content.
          </p>
          <button
            className="bg-[#414141] w-56 h-14 rounded-xl text-3xl font-extrabold text-white"
            onClick={() => setGetStarted(true)}
          >
            get started!
          </button>
        </div>
        <div className="border-l-2 border-black h-96 mx-8 my-8"></div>
      </div>
      
      {/* Bottom horizontal line */}
      <div className="border-b-2 border-gray-700 w-full mt-8 "></div>
    </div>
  );
}

export default Welcome;