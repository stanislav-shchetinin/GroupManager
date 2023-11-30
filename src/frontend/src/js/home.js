fetch(URL_BACKEND + '/home', {
    method: 'GET',
    headers : {
        'Authorization' : authHeader()
    }
}).then(r => {
    console.log(r.json().then( json => console.log(json) ))
});