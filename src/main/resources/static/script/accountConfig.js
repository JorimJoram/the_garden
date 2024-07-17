var usernameState = false;
var passwordState = false;
var passwordRegexState = false;
var confirmState = false;
var nameState = false;
var telState = false;

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
                usernameState = false;
                resultSpan.textContent = "이미 등록된 계정입니다";
                resultSpan.style.color='red';
            }else{
                usernameState = true;
                resultSpan.textContent = "사용 가능한 계정입니다";
                resultSpan.style.color='black';
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

function checkPasswordRegex(){
    var passwordResultSpan = document.getElementById('account_password_result');
    var passwordInput = document.getElementById('account_password');
    var passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{5,}$/;
    passwordRegexState = passwordRegex.test(passwordInput.value);
    
    confirmState = false
    if(passwordRegexState){
        passwordRegexState = true;
        passwordResultSpan.textContent = '';
    }else{
        passwordRegexState = false;
        passwordResultSpan.textContent = '비밀번호 형식을 다시 확인해주세요';
        passwordResultSpan.style.color = 'red';
    }
}

function checkPassword(){
    var password = document.getElementById("account_password").value;
    var confirm = document.getElementById("account_confirm").value;
    var resultTag = document.getElementById("account_password_result")

    if(password == confirm && passwordRegexState){
        confirmState = true
        resultTag.textContent = "";
    }else{
        confirmState = false
        resultTag.textContent = "비밀번호를 다시 확인해주세요";
    }   
}

/**
 * 한글 입력 잘 했는지 확인
 */
function checkKoreanRegex(){
    var nameInput = document.getElementById('account_name');
    var nameSpan = document.getElementById('account_name_check');
    var koreanPattern = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/
    var testResult = koreanPattern.test(nameInput.value);
    if(!testResult){
        nameSpan.textContent = "이름을 다시 입력해주세요"
        nameSpan.style.color = 'red'
        nameState = false;
    }else{
        nameState = true;
    }
}

function checkTelRegex(){
    var phonePattern = /^\d{11}$/;
    
}