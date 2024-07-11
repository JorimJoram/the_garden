// window.onload() = function(){

// }

function checkUsername(){
    var usernameInput = document.getElementById("account_username");
    var resultSpan = document.getElementById("account_username_result");
    var usernameResult = false;
    var username = usernameInput.value;

    
    if(checkUsernameRegex(username)){
        axios.get(`/account/api/username-dup?username=${username}`)
        .then(response => {
            usernameResult = response.data
            if(!response.data){
                resultSpan.textContent = "이미 등록된 계정입니다";
            }else{
                resultSpan.textContent = "사용 가능한 계정입니다";
            }
        }).catch(error => {
            console.error('Error fetching ', error);
            throw error;
        })
    }else{
        resultSpan.textContent = "아이디를 다시 확인해주세요";
    }  
}

function checkUsernameRegex(username){
    var englishRegex = /^[A-Za-z]+$/;
    if(username.length <= 4)
        return false;

    if(englishRegex.test(username)){
        return false;
    }else{
        return true
    }
}

function checkPasswordRegex(password){
    var passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{5,}$/;
    return passwordRegex.test(password);
}

function checkPassword(){
    var password = document.getElementById("account_password").value;
    var confirm = document.getElementById("account_confirm").value;
    var resultTag = document.getElementById("account_password_result")
    
    var checkRegex = false;
    var checkConfirm = false;

    if(checkPasswordRegex(password)){
        checkRegex=true;
    }
        
    
    if(password == confirm){
        checkConfirm = true;
    }

    if(checkConfirm && checkRegex){
        resultTag.textContent = "비밀번호가 확인되었습니다";
    }else{
        resultTag.textContent = "비밀번호를 다시 확인해주세요";
    }   
}

/**
 * 실패할 때 발생하는 메시지 저장해놓은 공간입니다. 나중에 수정하시고 표시해주세요
 * @param {*} result 
 */
function failureMsg(result){
    switch(result){
        case 1:
            return ;
    }
}