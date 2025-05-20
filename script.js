console.log("yeah");

function getParagraph(item){
    console.log(item.value);
}

function getFile(){

}

function submit(){
    getParagraph(paragraph);
}

const form = document.getElementById('form');
form.addEventListener('submit', function(event) {
    event.preventDefault(); 
    const formData = new FormData(form);
    const paragraph = formData.get('paragraph');
    const fileUpload = formData.get('fileUpload');
    console.log('Paragraph: ', paragraph);
    console.log('file: ', fileUpload);
});