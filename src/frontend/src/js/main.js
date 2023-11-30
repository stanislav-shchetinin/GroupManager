const URL_BACKEND = 'http://localhost:8080';
const validInput = (str) => {
    return str != null && str !== "";
}

const authHeader = () => {
    return 'Bearer ' + localStorage.getItem("jwt");
}