function clickNextStepButton() {
    var canvas = document.getElementById("cube");

    var event = new MouseEvent('mousedown', {
        'view': window,
        'bubbles': true,
        'cancelable': true,
        'clientX': document.body.clientWidth / 7 * 5,
        'clientY': canvas.clientHeight
    });

    canvas.dispatchEvent(event);
}

function clickPreviousStepButton() {
    var canvas = document.getElementById("cube");

    var event = new MouseEvent('mousedown', {
        'view': window,
        'bubbles': true,
        'cancelable': true,
        'clientX': document.body.clientWidth / 7 * 1.5,
        'clientY': canvas.clientHeight
    });

    canvas.dispatchEvent(event);
}
