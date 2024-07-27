var emailCode = '';
var timeout = false;

function sendMail(mail, resultSpan){
    axios.get(`/sms/api/send?mail=${mail}`)
    .then(response => {
        alert('메일이 전송되었습니다.')
        emailCode = response.data;
        showValidTime(resultSpan);
    }).catch(error => {
        console.error('fetching Error ', error);
    })
}

function showValidTime(resultSpan){
    var mailResult = document.getElementById('account_mailCert_result')
    var time = 180;
    timeout = false;

    var startTime = Date.now();
    var endTime = startTime + time *1000;

    function update(){
        var currentTime = Date.now();
        var remainTime = Math.max(0, endTime - currentTime);

        var seconds = Math.floor(remainTime / 1000);
        var min = Math.floor(seconds/60);
        seconds %= 60;

        min = min.toString().padStart(2, '0');
        seconds = seconds.toString().padStart(2, '0');

        resultSpan.innerHTML = `${min} : ${seconds}`;

        if(currentTime >= endTime){
            timeout = true;
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

function checkEmailCode(event){
    var authCode = event.target.value;
    var mailResult = document.getElementById('account_mailCert_result');

    if(authCode == emailCode){
        isEmail = true;
        stopTimer();
        mailResult.style.color = '#000000'
        mailResult.textContent = '확인되었습니다.'
        checkState();
    }else{
        isEmail = false;
        mailResult.style.color = '#ff0000';
        mailResult.textContent = '인증번호를 다시 확인해주세요';
    }
}