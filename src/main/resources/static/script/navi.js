var index = 0;
let showing = false;
const innerURL = ["/", "/", "/grouping/list", "/"];

function toLogin(){
    window.location.href = "/login";
}

function setMenuList(){
    var items = document.querySelectorAll('#navi_set li');

    function showNextItem(){
        if(index < items.length){
            items[index].classList.remove('remove_list');
            items[index].classList.remove('hidden_list');
            items[index].href = `${innerURL[index]}`;
            index++;
            setTimeout(showNextItem, 100); //시간값 잘 건드리면 더 자연스럽게 만들어질 수도 있음
        }
        showing = true;
    }

    function hideNextItem(){
        if(index >= 0){
            items[index].classList.add('remove_list');
            items[index].classList.add('hidden_list');
            items[index].href = "javascript:void(0)";
            index--;
            setTimeout(hideNextItem, 100);
        }
        showing = false;
    }

    if(!showing){
        index = 0;
        showNextItem();
    }else{
        index = items.length-1;
        hideNextItem();
    }
}