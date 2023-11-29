const btnAuth = document.getElementById('btn-auth');
const auth = () => {
    const container = document.getElementById('container');
    const form = document.querySelector('.sign-in-container');
    const inputPassword = form.querySelector('.password')
    const inputEmail = form.querySelector('.email')

    const json = {"username": inputEmail.value, "password": inputPassword.value}

    for (const [key, val] of Object.entries(json)) {
        if (!validInput(val)){
            alert(`Поле ${key} не может быть пустым`);
            return;
        }
    }

    fetch(URL_BACKEND + '/auth', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        },
        body: JSON.stringify(json)
    }).then(r => {
        if (r.ok){
            console.log(r.body)
            return r.body;
        } else if (r.status === 401){
            alert("Please activate the mail");
        } else if (r.status === 404){
            alert("Invalid username or password");
        }
        else {
            alert("Oops.. is something wrong")
        }
    });
}

btnAuth.addEventListener('click', auth);

