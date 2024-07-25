window.onload = function(){
    setApplyButton()
    setApplicants()
    setGroupingReview()
}

function setApplyButton(){
    var applyButton = document.getElementById("grouping_detail_applyButton")
    applyButton.style.backgroundColor = "#0052A4"
    axios.get(`/grouping/api/apply-cert?groupId=${getGroupId()}`)
    .then(response => {
        if(response.data == false)
            applyButton.style.backgroundColor = "#00A84D"
    }).catch(error => {
        console.error('Error fetching ', error)
    })
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
        var itemList = response.data;
        const reviewListContainer = document.getElementById('review_data_table')

        reviewListContainer.innerHTML = '';
        itemList.forEach(item => {
            const rowElement = document.createElement('tr');
            //별점 추가
            const profileCell = document.createElement('td')
            const profile = document.createElement('img')
            profile.src = item.account.imagePath;
            profile.className = "applicant_img"
            profileCell.appendChild(profile);
            rowElement.appendChild(profileCell)
            //내용 추가
            const contentCell = document.createElement('td');
            contentCell.textContent = item.content;
            rowElement.appendChild(contentCell);

            reviewListContainer.appendChild(rowElement);

            const underRowElement = document.createElement('tr')
            
            const profileNameCell = document.createElement('td')
            profileNameCell.textContent = item.account.username;
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