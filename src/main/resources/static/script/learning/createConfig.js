function sendData(){
    var data = {
        applicantCnt: document.getElementById('learning_create_applicant_cnt').value,
        style: document.getElementById('learning_create_style').value,
        freq: document.getElementById('learning_create_freq').value,
        title: document.getElementById('learning_create_title').value,
        content: document.getElementById('learning_create_content').value
    }

    axios.post(`/learning/api/create`, data)
    .then(response => {
        window.location.href=`/learning/detail/${response.data.id}`;
    }).catch(error => {
        console.error(error);
    })
}