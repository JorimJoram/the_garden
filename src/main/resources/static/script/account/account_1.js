var isPersonalInfo = false;
var isAccountInfo = false;

function selectPersonalInfo(){
    var personalInfoCheck = document.getElementById('personal_info');
    isPersonalInfo = !!personalInfoCheck.checked;

    checkState();
}

function selectAccountInfo(){
    var accountInfoCheck = document.getElementById('account_info');
    isAccountInfo = !!accountInfoCheck.checked;

    checkState()
}

function checkState(){
    if(isPersonalInfo && isAccountInfo){
        window.location.href = "/account/main";
    }
}