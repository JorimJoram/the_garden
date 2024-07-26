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
            option.value = store.name;
            option.textContent = store.name;
            storeSelect.appendChild(option);
        });
    }).catch(error => {
        console.error('Fetching Error ', error);
    })
}

function sendData(){
    var time = document.getElementById("grouping_create_time");
    console.log(time.value);
}