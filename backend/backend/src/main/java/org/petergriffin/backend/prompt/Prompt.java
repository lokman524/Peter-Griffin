package org.petergriffin.backend.prompt;

public class Prompt {

    private String promptContent;
    private String promptSettings;

    public Prompt (String promptContent){
        this.promptContent = promptContent + "The Above is the content of a notebook from a coursework provided by a fellow student. Please act as Peter Griffin from Family Guy and give me a series of dialogues to test the knowledge of the student of the note. The dialogue structure will be as follow: 'Welcome to the [Quiz title (e.g AP Economics)] Quiz.' / 'This quiz is to [target of the quiz]' / 'Question one, [Insert Question Here] / *TIME TO THINK*  / 'The answer is [Answer to the question], [short elaboration of the answer] ... The dialogues should be outputed in a text seperated by /. Above there should be sections of [*TIME TO THINK*] to indicate when should the student start thinking the question There should be 5 questions in total.";

        this.promptSettings = "You are a assistance in generating short form videos";
    }

    //GETTERS and SETTERS

    public String getPromptContent() {
        return promptContent;
    }

    public void setPromptContent(String promptContent) {
        this.promptContent = promptContent;
    }

    public String getPromptSettings() {
        return promptSettings;
    }

    public void setPromptSettings(String promptSettings) {
        this.promptSettings = promptSettings;
    }

}
