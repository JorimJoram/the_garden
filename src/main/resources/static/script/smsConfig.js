var emailCode = "";
var authState = false

function sendEvent(){
    var emailInput = document.getElementById('account_email');
    var resultSpan = document.getElementById('account_email_result');
    var email = emailInput.value
    if(checkMailRegex(email)){
        resultSpan.textContent = "인증번호가 발송되었습니다.";
        sendMail(email);
    }else{
        resultSpan.textContent = "이메일 형식을 다시 확인해주세요";
    }
}

function checkEmailCode(){
    console.log(`code: ${emailCode}`)
    var authCodeInput = document.getElementById('account_email_code');
    var authCodeResultSpan = document.getElementById('account_emailAuth_result')
    var authCode = authCodeInput.value;
    if(authCode == emailCode){
        authState = true;
        authCodeResultSpan.textContent = ''
        alert('인증이 완료되었습니다.');
    }else{
        authCodeResultSpan.textContent = '인증번호를 다시 입력해주세요'
    }
}

function sendMail(email){
    axios.get(`http://localhost:12571/sms/api/send?mail=${email}`)
    .then(response => {
        emailCode = response.data
    })
    .catch(error => {
        console.log('Error fetching email Data:', error);
        throw error
    });
}

function checkMailRegex(email){
    var mailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return mailRegex.test(email);
}