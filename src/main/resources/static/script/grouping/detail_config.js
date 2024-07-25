window.onload = function(){
    setApplyButton()
    setApplicants()
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