function addZero(value: number) {
    if (value < 10)
        return "0" + value;
    return value.toString();
}

function formatDate(date: Date) {
    const year = date.getFullYear();
    const month = addZero(date.getMonth() + 1);
    const day = addZero(date.getDate());
    const hours = addZero(date.getHours());
    const minutes = addZero(date.getMinutes());
    const seconds = addZero(date.getSeconds());

    return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
}

export {formatDate};

export function getQuestionTypeName(type: string) {
    if (type === "choice") return "单选题";
    else if (type === "multi_choice") return "多选题";
    else if (type === "completion") return "填空题";
    else if (type === "short_answer") return "简答题";
    return "";
}

//获取题型大分类
export function getQuestionGeneralType(type: string) {
    if (type == "choice" || type == "multi_choice")
        return "choice";
    if (type == "completion" || type == "short_answer")
        return "normal";
    return "";
}
