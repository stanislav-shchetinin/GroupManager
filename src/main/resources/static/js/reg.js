const reg = () => {
    const container = document.getElementById('container');
    const form = document.querySelector('.sign-up-container');
    const inputUsername = form.querySelector('.username')
    const inputPassword = form.querySelector('.password')
    const csrfToken = document.querySelector('meta[name=_csrf_token]').content;

    const json = {"username": inputUsername.value, "password": inputPassword.value}

     fetch('/registration', {
         method: 'POST',
         headers: {
             'Content-Type': 'application/json;charset=utf-8',
             'X-CSRF-TOKEN': csrfToken
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