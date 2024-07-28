function toLogin(){
    window.location.href = "/login";
}

function toMyPage(){
    window.location.href="/mypage/main"
}

function toMain(){
    window.location.href="/";
}

function toGrouping(){
    window.location.href="/grouping/list";
}

function toLearning(){
    window.location.href="/learning/list";
}

function toBack(){
    const currentPath = window.location.pathname;
    if(currentPath.startsWith('/grouping/detail')){
        window.location.href = '/grouping/list';
    }else{
        window.history.back()
    }
}