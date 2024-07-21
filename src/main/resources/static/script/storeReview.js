window.onload = function(){
    getReviewList();
}

function sendReview(){
    var star = document.getElementById('store_review_star')
    var content = document.getElementById('store_review_content')
    var json = {
        storeId: getStoreId(),
        star: star.value,
        content: content.value
    }

    axios.post(`/store/api/review/create`, json)
    .then(response => {
        console.log(response.data)
        var item = response.data
        if(item.id != -1){
            getReviewList()
        }
    }).catch(error => {
        console.log(error);
    })
}

function getReviewList(){
    axios.get(`/store/api/review/list?store_id=${getStoreId()}`)
    .then(response => {
        setReviewList(response.data)
    }).catch(error => {
        console.log(error);
    })
}

function setReviewList(itemList){
    console.log('getReviewList');
    console.log(itemList)
    const reviewListContainer = document.getElementById('store_review_list')
    reviewListContainer.innerHTML = '';
    itemList.forEach(item => {
        const reviewElement = document.createElement('span');
        reviewElement.textContent = `Star: ${item.star}, Content:${item.content}`;
        reviewListContainer.appendChild(reviewElement);
    });
}

function getStoreId(){
    const url = window.location.href;
    const urlParts = url.split('/');
    return urlParts[urlParts.length-1]
}