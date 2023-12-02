const URL_BACKEND = 'http://localhost:8080';
const URL_FRONTEND = 'http://localhost:3000';
const HOME = '/home'
const LOGIN = '/login'
const validInput = (str) => {
    return str != null && str !== "";
}
const authHeader = () => {
    return 'Bearer ' + localStorage.getItem("jwt");
}

export {URL_BACKEND, URL_FRONTEND, HOME, LOGIN, validInput, authHeader};