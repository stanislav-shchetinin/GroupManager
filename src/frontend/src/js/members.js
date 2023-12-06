import {authHeader, HOME, LOGIN, MEMBERS, URL_BACKEND, URL_FRONTEND} from "./main.js";

const groupId = localStorage.getItem("groupId");
const groupName = localStorage.getItem("groupName");

function isNumeric(value) {
    return /^-?\d+$/.test(value);
}
fetch(URL_BACKEND + HOME + "/" + groupId + MEMBERS, {
    method: 'GET',
    headers : {
        'Authorization' : authHeader()
    }
}).then(r => {
    if (r.status === 401){
        window.location.replace(URL_FRONTEND + LOGIN);
    } else {
        document.querySelector(".header").insertAdjacentHTML('beforeend', ` in ${groupName}`)
        return r.json().then( json => {
            console.log(json)
            const membersList = document.querySelector('.members');

            json.forEach(member => {
                let paidText = "Admin";
                if (member.numberClasses != null){
                    localStorage.setItem(member.username, crypto.randomUUID())
                    paidText = "Paid classes: " + member.numberClasses;
                    membersList.insertAdjacentHTML('beforeend', `
                        <li> 
                            <p><b>${member.username}</b></p>
                            <p>${paidText}</p>
                            <button class="button button-shadow btn-number-classes minus ${localStorage.getItem(member.username)}">Minus</button>
                            <br>
                            <button class="button button-shadow btn-number-classes plus ${localStorage.getItem(member.username)}">Add</button>
                            <input class="input-number-classes">         
                        </li>
                    `)
                    const btnPlus = document.getElementsByClassName(`plus ${localStorage.getItem(member.username)}`);
                    const btnMinus = document.getElementsByClassName(`minus ${localStorage.getItem(member.username)}`);
                    btnPlus[0].addEventListener('click', () => {
                        const input = document.querySelector('.input-number-classes');
                        if (!isNumeric(input.value)){
                            alert('Invalid input. Please, enter number');
                        } else {
                            changeNumberClasses("plus", member.username, input.value);
                        }

                    })
                    btnMinus[0].addEventListener('click', () => {
                        changeNumberClasses("minus", member.username, 1);
                    })
                } else {
                    membersList.insertAdjacentHTML('beforeend', `
                        <li> 
                            <p><b>${member.username}</b></p>
                            <p>${paidText}</p>
                        </li>
                    `)
                }

            })

        });
    }
});

const changeNumberClasses = (operation, username, cnt) => {
    const json = {"username": username};
    if (operation === "plus") {
        json["plus"] = cnt;
    } else if (operation === "minus") {
        json["minus"] = cnt;
    }
    console.log(json);
    fetch(URL_BACKEND + HOME + "/" + groupId + MEMBERS + "/numberOfClasses/" + operation, {
        method: 'POST',
        headers : {
            'Authorization' : authHeader(),
            'Content-Type': 'application/json;charset=utf-8'
        },
        body : JSON.stringify(json)
    }).then(r => {
        if (r.status === 401){
            window.location.replace(URL_FRONTEND + LOGIN);
        } else {
            location.reload();
        }
    });
}

const btnBack = document.querySelector('.btn-back');
btnBack.addEventListener('click', () => {
    window.location.replace(URL_FRONTEND + HOME)
})