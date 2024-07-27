var isCampus = false;
var isName = false;
var isPersonalStart = false;
var isPersonalEnd = false;

function selectCampus(event){
    const campus = event.target.value;
    sessionStorage.setItem('campus', `${event.target.value}캠퍼스`);
    axios.get(`/account/api/campus/list?campus=${campus}`)
    .then(response => {
        var classList = response.data;
        console.log(classList);
        var classSelect = document.getElementById('account_select_class');
        classSelect.innerHTML = '';
        
        var defaultOption = document.createElement('option');
        defaultOption.value = "선택"
        defaultOption.textContent = "선택"
        classSelect.appendChild(defaultOption)
        
        classList.forEach(item => {
            const option = document.createElement('option');
            option.value = item.name;
            option.textContent = item.name;
            classSelect.appendChild(option)
        });

    }).catch(error => {
        console.error('Fetching error, ', error);
    })
}

function selectClass(event){
    var selectedClass = event.target.value;
    if(selectedClass == "선택"){
        isCampus = false;
    }else{
        sessionStorage.setItem('education', selectedClass);
        isCampus = true;
        checkState();
    }
}

function checkAccountName(event){
    var resultSpan = document.getElementById('account_name_result');

    if(!checkRegex(event.target.value)){//실패했다면
        resultSpan.style.color = "#ff0000";
        resultSpan.textContent = "이름을 다시 입력해주세요";
    }else{
        resultSpan.textContent = '';
        sessionStorage.setItem('name', event.target.value);
        isName = true;
        checkState();
    }
}

function checkRegex(name){
    const regex = /^[가-힣]+$/;
    return regex.test(name)
}

function checkPersonalStart(event){
    const regex = /^\d{6}$/;
    var result = regex.test(event.target.value)
    var resultSpan = document.getElementById('account_personal_result')
    if(result){
        isPersonalStart = true;
        resultSpan.textContent = '';
        sessionStorage.setItem('birth', event.target.value);
        checkState();
    }else{
        isPersonalStart = false;
        resultSpan.style.color = '#ff0000';
        resultSpan.textContent = "생년월일 6자리를 입력해주세요"
    }
}

function checkPersonalEnd(event){
    const regex = /^\d{1}$/;
    var result = regex.test(event.target.value)
    var resultSpan = document.getElementById('account_personal_result')
    if(result){
        isPersonalEnd = true;
        sessionStorage.setItem('gender', event.target.value);
        resultSpan.textContent = '';
        checkState()
    }else{
        isPersonalEnd = false;
        resultSpan.style.color = '#ff0000';
        resultSpan.textContent = "뒷자리 1자리를 입력해주세요"
    }
}

function checkState(){
    if(isCampus && isName && isPersonalStart && isPersonalEnd){
        window.location.href = "/account/3";
    }
}