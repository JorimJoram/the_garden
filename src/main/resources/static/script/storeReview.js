var whoAmI = ''

window.onload = function(){
    whoAmI = document.getElementById('grouping_my_username');
    setStoreReview();
}

function sendReview(){
    var star = document.getElementById('store_review_star')
    var content = document.getElementById('store_review_content')
    if(content.value.trim()== ''){
        alert('댓글을 다시 확인해주세요')
        return ;
    }

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
            document.getElementById('store_review_content').value = '';
            setStoreReview()
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
    const reviewListContainer = document.getElementById('store_review_list')
    reviewListContainer.innerHTML = '';
    itemList.forEach(item => {
        const rowElement = document.createElement('tr');
        //별점 추가
        const starCell = document.createElement('td')
        starCell.textContent = item.star
        rowElement.appendChild(starCell)
        //내용 추가
        const contentCell = document.createElement('td');
        contentCell.textContent = item.content;
        rowElement.appendChild(contentCell);

        reviewListContainer.appendChild(rowElement);
    });
}

function getStoreId(){
    const url = window.location.href;
    const urlParts = url.split('/');
    return urlParts[urlParts.length-1]
}






function setStoreReview(){
    axios.get(`/store/api/review/list?store_id=${getStoreId()}`)
    .then(response => {
        const reviews = response.data;
        const reviewContainer = document.getElementById('review-container');
        reviewContainer.innerHTML = '';
        reviews.forEach(review => {
            console.log(review);
            const reviewElement = document.createElement('div');
            reviewElement.className = 'review';

            const img = document.createElement('img');
            img.src = review.account.imagePath;
            img.alt = review.account.username;

            const content = document.createElement('div');
            content.className = 'content';

            const userNameContainer = document.createElement('div');
            userNameContainer.className = 'user-name-container';

            const userName = document.createElement('h3');
            userName.textContent = review.account.username;

            const deleteSpan = document.createElement('span');
            console.log(`who am i: ${whoAmI} | answerUsername:${review.account.username}`)
            if(whoAmI.textContent == review.account.username){
                deleteSpan.className = 'delete';
                deleteSpan.textContent = '[삭제]';
                deleteSpan.style.cursor = 'pointer';
                deleteSpan.addEventListener('click', () => deleteReview(review.id))
            }            

            userNameContainer.appendChild(userName);
            userNameContainer.appendChild(deleteSpan);

            const reviewText = document.createElement('p');
            reviewText.textContent = review.content;

            const date = document.createElement('p');
            date.className = 'date';
            date.textContent = new Date(review.createdDate).toLocaleDateString();

            content.appendChild(userNameContainer);
            content.appendChild(reviewText);
            content.appendChild(date);

            reviewElement.appendChild(img);
            reviewElement.appendChild(content);

            reviewContainer.appendChild(reviewElement);
            });
        }).catch(error => {
            console.error('Error fetching ', error)
        })
}

function deleteReview(id){
    
    if(confirm('댓글을 삭제하시겠습니까?')){
        axios.delete(`/store/api/review/del/${id}`)
        .then(response => {
            setStoreReview();
        }).catch(error => {
            console.error(error);
        })
    }
}