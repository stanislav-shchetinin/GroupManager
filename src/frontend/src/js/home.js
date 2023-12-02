import {authHeader, HOME, LOGIN, URL_BACKEND, URL_FRONTEND} from "./main.js";

fetch(URL_BACKEND + HOME, {
    method: 'GET',
    headers : {
        'Authorization' : authHeader()
    }
}).then(r => {
    if (r.status === 401){
        window.location.replace(URL_FRONTEND + LOGIN);
    } else {
        return r.json().then( json => json);
    }
}).then(json => {
    console.log(json)
});