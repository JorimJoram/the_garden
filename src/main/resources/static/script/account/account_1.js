var isPersonalInfo = false;
var isAccountInfo = false;

function selectPersonalInfo(){
    var personalInfoCheck = document.getElementById('personal_info');
    isPersonalInfo = personalInfoCheck.checked ? true : false;

    checkState();
}

function selectAccountInfo(){
    var accountInfoCheck = document.getElementById('account_info');
    isAccountInfo = accountInfoCheck.checked ? true : false;

    checkState()
}

function checkState(){
    if(isPersonalInfo && isAccountInfo){
        window.location.href = "/account/2";
    }
}