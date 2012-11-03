function getDomText() {
    chrome.extension.sendRequest({
            action: "content",
            content: document.body.innerText
        }, function(response) {
        }
    );
}

function recursiveReplace() {
    var node = document.body;
    if (subtitleFile == "true") {
        appendTranslationToSubtitles(node, oldString, newString);
    } else {
        domReplace(node, oldString, newString);
    }

}
function domReplace(node, oldString, newString) {
    if (node.nodeType == 3) { // text node
        var regex = new RegExp('(\\b' + oldString + '\\b)', 'gi');
        node.nodeValue = node.nodeValue.replace(regex, "$1(" + newString + ")");
    } else if (node.nodeType == 1 && node.nodeName != "IFRAME") { // element
        $(node).contents().each(function () {
            domReplace(this, oldString, newString);
        });
    }
}

function appendTranslationToSubtitles(node, oldstring, newString) {
    var content = node.innerText;
    var contentLines = content.split("\n");
    var results = [];
    var buffor = ""; //for phrasal verbs in second line
    for (var i = 0; i < contentLines.length; i++) {
        if (oldstring.split(" ").length > 1) { //phrasal verb
            var phrasalVerbRegexp = oldstring.replace(" ", "(.*?)");
            console.log(phrasalVerbRegexp);
            if (contentLines[i].search(new RegExp('\\b' + phrasalVerbRegexp + '\\b', "i")) != -1) {
                contentLines[i] = contentLines[i] + '\n<font color="yellow">' + oldstring + ' = ' + newString + '</font>';
            } else if (((buffor + " " + contentLines[i]).search(new RegExp('\\b' + phrasalVerbRegexp + '\\b', "i"))) != -1){
                contentLines[i] = contentLines[i] + '\n<font color="yellow">' + oldstring + ' = ' + newString + '</font>';
            }
        } else if (contentLines[i].search(new RegExp('\\b' + oldstring + '\\b', "i")) != -1) {
            buffor = buffor = contentLines[i];
            contentLines[i] = contentLines[i] + '\n<font color="yellow">' + oldstring + ' = ' + newString + '</font>';
            console.log(contentLines[i]);
        } else {
            buffor = contentLines[i];
        }
        results.push(contentLines[i]);
    }
    content = contentLines.join('\n');
    $(node).text(content);
    $(node.firstChild).wrap("<pre></pre>");
}