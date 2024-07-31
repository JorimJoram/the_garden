function selectStyle(event){
    const selectedValue = event.target.value;
    getStoreListByStyle(selectedValue)
}

function getStoreListByStyle(style){
    axios.get(`/store/api/list?style=${style}`)
    .then(response => {
        var storeList = response.data
        var storeSelect = document.getElementById('grouping_create_store');
        storeSelect.innerHTML = '';

        storeList.forEach(store => {
            const option = document.createElement('option');
            option.value = store.id; //이름으로는 못 찾는다!
            option.textContent = store.name;
            storeSelect.appendChild(option);
        });
    }).catch(error => {
        console.error('Fetching Error ', error);
    })
}

function sendData(){
    if(document.getElementById('grouping_create_time').value === ''||document.getElementById('grouping_create_title').value.trim() === '' || document.getElementById('grouping_create_content').value.trim() === ''){
        alert('내용을 채워주세요');
        return ;
    }
    var data = {
        title: document.getElementById('grouping_create_title').value,
        content: document.getElementById('grouping_create_content').value,
        meetingDate: document.getElementById('grouping_create_time').value,
        storeId: document.getElementById('grouping_create_store').value
    }
    console.log(data)

    axios.post(`/grouping/api/create`, data)
    .then(response => {
        window.location.href=`/grouping/detail/${response.data.id}`;
    }).catch(error => {
        console.error(error);
    })
}