var isUsername = false;
var isPassword = false;
var isNickname = false;

function nicknameCheck(event){
    var nickname = event.target.value;
    axios.get(`/account/api/nickname-dup?nickname=${nickname}`)
    .then(response => {
        if(response.data){
            nicknameBlank(nickname)
        }else{
            var nicknameResult = document.getElementById('account_nickname_result')
            nicknameResult.style.color = "#ff0000";
            nicknameResult.textContent = "이미 사용중인 닉네임입니다";
        }
    })
}

function nicknameBlank(nickname){
    var nicknameResult = document.getElementById('account_nickname_result')
    if(nickname.value !== ''){
        isNickname = true
        nicknameResult.textContent = "";
        sessionStorage.setItem('nickname', nickname);
    }
    else{
        nicknameResult.style.color = "#ff0000";
        nicknameResult.textContent = "활동명을 적어주세요";
        isNickname = false;
    }
}