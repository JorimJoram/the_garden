var emailCode = "";
var authState = false
/**
 * 버튼을 막 누르게 되면 제가 가진 자원을 남용하게 됩니다
 * 따라서 버튼을 클릭한 후에 3분 정도 시간을 두게 만드는 법을 추천합니다
 */
function sendEvent(){
    var emailInput = document.getElementById('account_email');
    var resultSpan = document.getElementById('account_email_result');
    var sendButton = document.getElementById('account_email_sendButton');
    var checkButton = document.getElementById('account_email_codeCheck');
    var email = emailInput.value
    if(checkMailRegex(email)){
        alert('인증번호가 발송되었습니다.')
        sendButton.disabled = true;
        checkButton.disabled = false;
        showValidTime(resultSpan, sendButton, checkButton);
        sendMail(email);
    }else{
        resultSpan.textContent = "이메일 형식을 다시 확인해주세요";
        resultSpan.style.color = 'red';
        sendButton.disabled = false;
        checkButton.disabled = true;
    }
}

function showValidTime(resultSpan, sendButton, checkButton){
    var timeSpan = document.getElementById('account_email_time');
    var time = 180; //seconds
    timeout = false;

    var startTime = Date.now();
    var endTime = startTime + time * 1000;

    function update(){
        var currentTime = Date.now();
        var remainTime = Math.max(0, endTime - currentTime);

        var seconds = Math.floor(remainTime / 1000);
        var min = Math.floor(seconds/60);
        seconds %= 60;

        min = min.toString().padStart(2, '0');
        seconds = seconds.toString().padStart(2, '0');

        timeSpan.innerHTML = `${min} : ${seconds}`;

        if(currentTime >= endTime){
            timeout = true;
            sendButton.disabled = true;
            checkButton.disabled = false;
            resultSpan.textContent = '시간이 초과되었습니다.';
            resultSpan.style.color = 'red';
        }

        requestId = requestAnimationFrame(update);

    }
    update();
}

function stopTimer(){
    cancelAnimationFrame(requestId);
}

/**
 * 인증번호 확인
 */
function checkEmailCode(){
    console.log(`code: ${emailCode}`)
    var authCodeInput = document.getElementById('account_email_code');
    var authCodeResultSpan = document.getElementById('account_emailAuth_result')
    var authCode = authCodeInput.value;
    if(authCode == emailCode){
        stopTimer();
        authState = true;
        authCodeResultSpan.textContent = '확인되었습니다'
        authCodeResultSpan.style.color = 'black'
    }else{
        authCodeResultSpan.textContent = '인증번호를 다시 입력해주세요'
        authCodeResultSpan.style.color = 'red'
    }
}

/**
 * 이메일 전송
 * @param {사용자 이메일} email 
 */
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

/**
 * 이메일 형식 확인
 * @param {사용자가 작성하 이메일} email 
 * @returns 이메일 정규표현식 확인 후 문제있다면 false, 정상이면 true
 */
function checkMailRegex(email){
    var mailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return mailRegex.test(email);
}