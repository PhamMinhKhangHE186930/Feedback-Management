function submitForms() {
    document.getElementById("form-search").submit();
    document.getElementById("form-search-many").submit();
}

function change() {
    document.getElementById('form-search-many').submit();
}

function truncateText(selector, maxLength) {
    var element = document.getElementById(selector),
            truncated = element.innerHTML;

    if (truncated.length > maxLength) {
        truncated = truncated.substr(0, maxLength) + '...';
    }
    return truncated;
}

document.getElementById("contents").innerText = truncateText("content", 50);

// auto reload page if user go back
if (performance.navigation.type == 2) {
    location.reload(true);
}
