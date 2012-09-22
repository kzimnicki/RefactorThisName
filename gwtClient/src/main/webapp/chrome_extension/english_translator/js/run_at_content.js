var mousePos;
$(document).mousemove(function (e) {
    mousePos = {left: e.pageX + 20, top: e.pageY + 2};
});

function getSelectedText() {
    var text = "";
    if (typeof window.getSelection != "undefined") {
        text = window.getSelection().toString();
    } else if (typeof document.selection != "undefined" && document.selection.type == "Text") {
        text = document.selection.createRange().text;
    }
    return text;
}

function doSomethingWithSelectedText() {
    var selectedText = getSelectedText();
    if (selectedText) {
//        var obj = $("<div style='background-color: red'>TESTETSTETSTE</div>").css({
//            position: 'absolute',
//            top: mousePos.top,
//            left: mousePos.left
//        });
        console.log(obj);
//        $(document.body).append(obj);
    }
}

document.onmouseup = doSomethingWithSelectedText;