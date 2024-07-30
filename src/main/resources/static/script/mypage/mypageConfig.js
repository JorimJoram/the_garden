function readImage(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            const preview = document.getElementById('preview_profile');
            preview.src = e.target.result;
        }
        reader.readAsDataURL(input.files[0]);
    }
}

function fetchImage(){
    var imageFile = document.getElementById('mypage_image');
    var file = imageFile.files[0];

    var formData = new FormData();
    formData.append('img', file);

    axios.put(`/mypage/api/img`, formData, {headers:{'Content-type' : 'multipart/form-data'}})
    .then(response => {
        if(response.data){
            alert('프로필 이미지가 변경되었습니다');
            window.location.reload();
        }
    })
    .catch(error => {
        console.error(error);
    })    
}

function changeNickname(){
    var nickname = document.getElementById('mypage_nickname').value;
    var nicknameResult = document.getElementById('mypage_nickname_result')
    axios.get(`/mypage/api/nickname-dup?nickname=${nickname}`)
    .then(response => {
        if(response.data){
            changeAction(nickname)
            nicknameResult.textContent = '';
        }else{
            nicknameResult.style.color = '#ff0000';
            nicknameResult.textContent = "이미 사용중인 활동명입니다"
        }
    });
}

function changeAction(nickname){
    axios.put(`/mypage/api/nickname?nickname=${nickname}`)
    .then(response => {
        if(response.data){
            alert('활동명이 변경되었습니다');
            window.location.reload
        }
    })
    .catch(error => {
        console.error(error);
    })
}