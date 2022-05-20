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

function clickPlayButton() {
    var canvas = document.getElementById("cube");

    var event = new MouseEvent('mousedown', {
        'view': window,
        'bubbles': true,
        'cancelable': true,
        'clientX': document.body.clientWidth / 7 * 4,
        'clientY': canvas.clientHeight
    });

    canvas.dispatchEvent(event);
}

/*function goToStep(step, maxStep) {
    var canvas = document.getElementById("cube");
    var clientX = (document.body.clientWidth * 0.98) / maxStep * step;

    if (step != maxStep) {
        clientX += (document.body.clientWidth / maxStep) * 0.25
    }

    var event = new MouseEvent('mousedown', {
        'view': window,
        'bubbles': true,
        'cancelable': true,
        'clientX': clientX,
        'clientY': canvas.clientHeight * 0.99
    });

    canvas.dispatchEvent(event);
}*/
