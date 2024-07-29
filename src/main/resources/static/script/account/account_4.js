var isTel = false;
var isEmail = false;

function telRegex(event){
    const regex = /^010\d{8}$/;
    const result = document.getElementById('account_tel_result');
    var rexResult = regex.test(event.target.value);

    if(rexResult){
        isTel = true;
        result.textContent = '';
        sessionStorage.setItem('tel', event.target.value);
        checkState();
    }else{
        result.style.color = '#ff0000';
        result.textContent = "전화번호를 다시 확인해주세요";
        isTel = false;
    }
}

function emailCheck(event){
    var email = event.target.value
    axios.get(`/account/api/email-dup?email=${email}`)
    .then(response => {
        if(response.data){
            sessionStorage.setItem('email', event.target.value);
            emailRegex(email)
        }else{
            const result = document.getElementById('account_email_result');
            result.style.color = '#ff0000';
            result.textContent = "이미 등록된 이메일입니다";
        }
    }).catch(error => {
        console.error('Fetching Error ', error);
    })
}

function emailRegex(email){
    const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const result = document.getElementById('account_email_result');
    var rexResult = regex.test(email);

    if(rexResult){
        result.textContent = '';
        sendMail(email, result);
    }else{
        result.style.color = '#ff0000';
        result.textContent = "메일을 다시 입력해주세요";
        isEmail = false;
    }
}

function checkState(){
    if(isTel && isEmail)
        sendData();
}

function sendData(){
    var data = {
        username: sessionStorage.getItem('username'),
        password: sessionStorage.getItem('password'),
        nickname: sessionStorage.getItem('nickname'),
        name:sessionStorage.getItem('name'),
        tel: sessionStorage.getItem('tel'),
        email: sessionStorage.getItem('email'),
        campus: sessionStorage.getItem('campus'),
        education: sessionStorage.getItem('education'),
        birth: sessionStorage.getItem('birth'),
        gender: sessionStorage.getItem('gender'),
    }
    axios.post(`/account/api/create`, data)
    .then(response => {
        if(response.data.id > 0){
            alert('회원가입이 완료되었습니다!');

            sessionStorage.clear();
            localStorage.clear();

            window.location.href='/login';
        }
    }).catch(error => {
        console.error(error);
    })
}