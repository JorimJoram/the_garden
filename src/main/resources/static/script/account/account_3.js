var isUsername = false;
var isPassword = false;
var isNickname = false;

function usernameCheck(event){
    var username = event.target.value;
    axios.get(`/account/api/username-dup?username=${username}`)
    .then(response => {
        if(response.data){
            usernameRegex(username)
        }else{
            var resultSpan = document.getElementById('account_username_result');
            resultSpan.style.color = '#ff0000';
            resultSpan.textContent = '이미 사용중인 아이디입니다';
        }
    })
}

function usernameRegex(username){
    const regex = /^[a-zA-Z0-9]{5,}$/;
    var rexResult = regex.test(username);
    var result = document.getElementById('account_username_result');
    if(rexResult){
        result.textContent = '';
        isUsername = true;
        sessionStorage.setItem('username', username);
        checkState();
    }else{
        usernameResult(result);
    }
}

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
    if(nickname.value != ''){
        isNickname = true
        nicknameResult.textContent = "";
        sessionStorage.setItem('nickname', nickname);
        checkState();
    }
    else{
        nicknameResult.style.color = "#ff0000";
        nicknameResult.textContent = "활동명을 적어주세요";
        isNickname = false;
    }
}

function usernameResult(resultSpan){
    resultSpan.style.color = '#ff0000';
    resultSpan.textContent = '아이디를 다시 입력해주세요';
    isUsername = false;
}

function passwordRegex(event){
    //정규표현식 적어주세여
    //만족했으면 true, 그렇지 않으면 false
    const regex = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{5,}$/;
    const result = document.getElementById('account_password_result');
    var rexResult = regex.test(event.target.value);
    if(rexResult){
        isPassword = true;
        result.textContent = '';
        sessionStorage.setItem('password', event.target.value);
        checkState();
    }else{
        result.style.color = "#ff0000";
        result.textContent = "비밀번호를 다시 입력해주세요"
        isPassword = false;
    }
}

function checkState(){
    if(isUsername && isNickname && isPassword){
        window.location.href = "/account/4";
    }
}