window.onload = function(){
    setApplicants()
    setApplyButton()
}

function setApplicants(){
    axios.get(`/learning/api/apply-list?learningId=${getLearningId()}`)
    .then(response => {
        var data = response.data;
        var dataCell = document.getElementById('learning_detail_applicantCell');
        dataCell.innerHTML = '';
        data.forEach(item => {
            const container = document.createElement('div');
            
            const image = document.createElement('img');
            image.src = item.account.imagePath;
            image.className = "detail_applicant_img"
            
            const span = document.createElement('span')
            span.textContent = item.account.name;
            
            container.appendChild(image);
            container.appendChild(span);

            dataCell.appendChild(container);
        })
    })
    .catch(error => {
        console.error(error);
    })
}

function getLearningId(){
    const url = window.location.href;
    const parts = url.split('/');
    return parts[parts.length - 1];
}

function applicantButtonEvent(){
    var applyButton = document.getElementById('learning_detail_applyButton');
    var color = window.getComputedStyle(applyButton).backgroundColor;
    console.log(color);

    if(color == 'rgb(0, 168, 77)'){ //초록색 -> 이미 등록된 상황이라면
        applyButton.addEventListener("click", removeApplicant())
    }else{
        applyButton.addEventListener("click", createApplicant())
    }

    function createApplicant(){
        axios.post(`/learning/api/applicant/create/${getLearningId()}`)
        .then(response => {
            if(response.data.id > 0){
                applyButton.style.backgroundColor = "rgb(0, 168, 77)"//초록색
                setApplicants()
            }
        }).catch(error => {
            console.error(error)
        })
    }

    function removeApplicant(){
        axios.delete(`/learning/api/applicant/del/${getLearningId()}`)
        .then(response => {
            applyButton.style.backgroundColor = "rgb(0, 82, 164)"//파란색
            setApplicants()
        }).catch(error => {
            console.error(error)
        })
    }
}

function setApplyButton(){
    var applyButton = document.getElementById("learning_detail_applyButton")
    applyButton.style.backgroundColor = "#0052A4"
    axios.get(`/learning/api/apply-cert?learningId=${getLearningId()}`)
    .then(response => {
        if(response.data == false)
            applyButton.style.backgroundColor = "#00A84D"
    }).catch(error => {
        console.error('Error fetching ', error)
    })
}