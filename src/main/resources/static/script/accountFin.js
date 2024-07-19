function axiosAction(){
    axios.post('/account/api/create', createJSON())
        .then(response => {
            window.location.href='/login';
        })
        .catch(error => {
            console.error(error);
        })
}

function createJSON(){
    return {
        username : document.getElementById('account_username').value,
        password : document.getElementById('account_password').value,
        classRoom: `${document.getElementById('account_campus_container').value} ${document.getElementById('account_class_container').value}`,
        email: document.getElementById('account_email').value,
        name: document.getElementById('account_name').value,
        //phone: document.getElementById('account_tel').value,
    };
}