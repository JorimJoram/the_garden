window.onload = function(){
    setApplyButton()
    setApplicants()
    setGroupingReview()
}

function getGroupId(){
    const url = window.location.href;
    const parts = url.split('/');
    return parts[parts.length - 1];
}

function setApplicants(){
    axios.get(`/grouping/api/group/apply-list?groupId=${getGroupId()}`)
    .then(response => {
        var data = response.data;
        var dataCell = document.getElementById('grouping_detail_applicantCell');
        dataCell.innerHTML = '';
        data.forEach(item => {
            const container = document.createElement('div');
            
            const image = document.createElement('img');
            image.src = item.account.imagePath;
            image.className = "applicant_img"
            
            const span = document.createElement('span')
            span.textContent = item.account.username;
            
            container.appendChild(image);
            container.appendChild(span);

            dataCell.appendChild(container);
        });
    }).catch(error => {
        console.error('Error fetching ', error);
    })
}

function setGroupingReview(){
    axios.get(`/grouping/api/review/list?groupId=${getGroupId()}`)
    .then(response => {
        const reviews = response.data;
        const reviewContainer = document.getElementById('review-container');

        reviews.forEach(review => {
            console.log(review);
            const reviewElement = document.createElement('div');
            reviewElement.className = 'review';

            const img = document.createElement('img');
            img.src = review.account.imagePath;
            img.alt = review.account.username;

            const content = document.createElement('div');
            content.className = 'content';

            const userName = document.createElement('h3');
            userName.textContent = review.account.username;

            const reviewText = document.createElement('p');
            reviewText.textContent = review.content;

            const date = document.createElement('p');
            date.className = 'date';
            date.textContent = new Date(review.createdDate).toLocaleDateString();

            content.appendChild(userName);
            content.appendChild(reviewText);
            content.appendChild(date);

            reviewElement.appendChild(img);
            reviewElement.appendChild(content);

            reviewContainer.appendChild(reviewElement);
            });
        }).catch(error => {
            console.error('Error fetching ', error)
        })
}

function removeCommnet(event, id){
    console.log(`clicked id : ${id}`);
}

function formattedDate(date){
    const dateStr = new Date(date.split('.')[0] + 'Z');
    const formattedDate = dateStr.toISOString().slice(0, 19).replace('T', ' ');
    return formattedDate;
}

function sendReview(){
    if( document.getElementById('grouping_review_content').value.trim()== ''){
        alert('댓글을 다시 확인해주세요')
        return ;
    }

    var json = {
        groupId: getGroupId(),
        content: document.getElementById('grouping_review_content').value
    }
    axios.post(`/grouping/api/review/create`, json)
    .then(response => {
        console.log(response.data);
        var item = response.data;
        if(item.id != -1){
            document.getElementById('grouping_review_content').value = '';
            setGroupingReview();
        }
    }).catch(error => {
        console.error('Error fetching ', error);
    })
}

function setApplyButton(){
    var applyButton = document.getElementById("grouping_detail_applyButton")
    applyButton.style.backgroundColor = "rgb(18,124,116)"
    applyButton.style.color="#ffffff";
    applyButton.textContent = '참여하기';
    axios.get(`/grouping/api/apply-cert?groupId=${getGroupId()}`)
    .then(response => {
        if(response.data == false){
            applyButton.style.backgroundColor = "#ffffff"
            applyButton.style.color = "rgb(18,124,116)"
            applyButton.textContent = '취소하기'
        }else{

        }
    }).catch(error => {
        console.error('Error fetching ', error)
    })
}

function applicantButtonEvent(){
    var applyButton = document.getElementById('grouping_detail_applyButton');
    var color = window.getComputedStyle(applyButton).backgroundColor;
    console.log(color);

    if(color == 'rgb(255, 255, 255)'){ //흰색 -> 이미 등록된 상황이라면
        applyButton.addEventListener("click", removeApplicant())
    }else{
        applyButton.addEventListener("click", createApplicant())
    }

    function createApplicant(){
        axios.post(`/grouping/api/applicant/create/${getGroupId()}`)
        .then(response => {
            if(response.data.id > 0){
                applyButton.style.backgroundColor = "rgb(255,255,255)"//흰색
                applyButton.style.color = "rgb(18,124,116)";
                applyButton.textContent = '취소하기'
                setApplicants()
            }
        }).catch(error => {
            console.error(error)
        })
    }

    function removeApplicant(){
        axios.delete(`/grouping/api/applicant/del/${getGroupId()}`)
        .then(response => {
            applyButton.style.backgroundColor = "rgb(18,124,116)"//파란색
            applyButton.style.color = "#ffffff"
            applyButton.textContent = '참여하기'
            setApplicants()
        }).catch(error => {
            console.error(error)
        })
    }
}