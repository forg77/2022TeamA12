function addZero(value:number) {
    if (value < 10)
        return "0" + value;
    return value.toString();
}

function formatDate(date:Date) {
    const year = date.getFullYear();
    const month = addZero(date.getMonth() + 1);
    const day = addZero(date.getDate());
    const hours = addZero(date.getHours());
    const minutes = addZero(date.getMinutes());
    const seconds = addZero(date.getSeconds());

    return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
}

export { formatDate };
