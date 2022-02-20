function addZero(value) {
    if (value < 10)
        return "0" + value;
    return value.toString();
}

function formatDate(date) {
    let year = date.getFullYear();
    let month = addZero(date.getMonth() + 1);
    let day = addZero(date.getDate());
    let hours = addZero(date.getHours());
    let minuts = addZero(date.getMinutes());
    let seconds = addZero(date.getSeconds());

    return year + "-" + month + "-" + day + " " + hours + ":" + minuts + ":" + seconds;
}

export { formatDate };