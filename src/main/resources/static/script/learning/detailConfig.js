window.onload = function(){
    setApplicants()
    setApplyButton()
    setLearningReview()
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

function setLearningReview(){
    axios.get(`/learning/api/review/list?learningId=${getLearningId()}`)
    .then(response => {
        var itemList = response.data;
        const reviewListContainer = document.getElementById('review_data_table')

        reviewListContainer.innerHTML = '';
        itemList.forEach(item => {
            const rowElement = document.createElement('tr');
            
            const profileCell = document.createElement('td')
            const profile = document.createElement('img')
            profile.src = item.account.imagePath;
            profile.className = "detail_applicant_img"
            profileCell.appendChild(profile);
            rowElement.appendChild(profileCell)
            
            const contentCell = document.createElement('td');
            contentCell.textContent = item.content;
            rowElement.appendChild(contentCell);

            reviewListContainer.appendChild(rowElement);

            const underRowElement = document.createElement('tr')
            
            const profileNameCell = document.createElement('td')
            profileNameCell.textContent = item.account.username;
            profileNameCell.addEventListener('click', (event) => removeCommnet(event, item.id));
            underRowElement.appendChild(profileNameCell)

            const dateCell = document.createElement('td')
            dateCell.textContent = formattedDate(item.createdDate);
            underRowElement.appendChild(dateCell);

            reviewListContainer.append(underRowElement);
        });
            
        }).catch(error => {
            console.error('Error fetching ', error)
        })
}

function removeCommnet(event, id){
    console.log(`clicked id : ${id}`);
}

function sendReview(){
    if( document.getElementById('learning_review_content').value.trim()== ''){
        alert('댓글을 다시 확인해주세요')
        return ;
    }

    var json = {
        learningMatesId: getLearningId(),
        content: document.getElementById('learning_review_content').value
    }

    axios.post(`/learning/api/review/create`, json)
    .then(response => {
        console.log(response.data);
        var item = response.data;
        if(item.id != -1){
            document.getElementById('learning_review_content').value = '';
            setLearningReview();
        }
    }).catch(error => {
        console.error('Error fetching ', error);
    })
}

function formattedDate(date){
    const dateStr = new Date(date.split('.')[0] + 'Z');
    const formattedDate = dateStr.toISOString().slice(0, 19).replace('T', ' ');
    return formattedDate;
}