const btnReg = document.getElementById('btn-reg');
const validInput = (str) => {
    return str != null && str !== "";
}
const reg = () => {
    const container = document.getElementById('container');
    const form = document.querySelector('.sign-up-container');
    const inputPassword = form.querySelector('.password')
    const inputEmail = form.querySelector('.email')

    const json = {"username": inputEmail.value, "password": inputPassword.value}

    for (const [key, val] of Object.entries(json)) {
        if (!validInput(val)){
            alert(`Поле ${key} не может быть пустым`);
            return;
        }
    }

     fetch(URL_BACKEND + '/registration', {
         method: 'POST',
         headers: {
             'Content-Type': 'application/json;charset=utf-8',
         },
         body: JSON.stringify(json)
     }).then(r => {
         if (r.ok){
             container.classList.remove("right-panel-active");
         } else if (r.status === 409){
            alert("User with this name is already exist");
         } else {
             alert("Oops.. is something wrong")
         }
     });
}

btnReg.addEventListener('click', reg);