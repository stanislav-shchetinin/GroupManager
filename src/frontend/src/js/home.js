import {authHeader, HOME, LOGIN, MEMBERS, URL_BACKEND, URL_FRONTEND} from "./main.js";

fetch(URL_BACKEND + HOME, {
    method: 'GET',
    headers : {
        'Authorization' : authHeader()
    }
}).then(r => {
    if (r.status === 401){
        window.location.replace(URL_FRONTEND + LOGIN);
    } else {
        return r.json().then( json => {
            const adminGroups = json.adminGroups;
            const memberGroups = json.memberGroups;
            const adminList = document.querySelector('.admin');
            const memberList = document.querySelector('.member');

            adminGroups.forEach(group => {
                adminList.insertAdjacentHTML('beforeend', `
                    <li> 
                        <p><b>${group.name}</b></p>
                        <p>${group.description}</p>
                        <button class="button button-shadow ${group.id}">Info</button>
                    </li>
                `)
                const btn = document.querySelector(`.${group.id}`);
                btn.addEventListener('click', () => {
                    localStorage.setItem("groupId", group.id);
                    localStorage.setItem("groupName", group.name);
                    //console.log(URL_FRONTEND + HOME + "/" + group.id + MEMBERS);
                    window.location.replace(URL_FRONTEND + HOME + "/" + group.id + MEMBERS);
                })
            })

            memberGroups.forEach(group => {
                memberList.insertAdjacentHTML('beforeend', `
                    <li> 
                        <p><b>${group.group.name}</b></p>
                        <p>${group.group.description}</p>
                        <p>Paid classes: ${group.numberClasses}</p>
                    </li>
                `)
            })

        });
    }
});