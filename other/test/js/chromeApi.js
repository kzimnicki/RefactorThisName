var ChromeAPI = ChromeAPI || {};


ChromeAPI.getSelectedTab = function(callback) {
    chrome.tabs.getSelected(null, callback);
}

ChromeAPI.requestListener = function(callback) {
    chrome.extension.onRequest.addListener(callback);
}


ChromeAPI.executeDomRequest = function(tab) {
    chrome.tabs.getSelected(null, function(tab) {
        currentTab = tab;
        chrome.tabs.executeScript(tab.id, { file: 'js/jquery-1.7.1.min.js' });
        chrome.tabs.executeScript(tab.id, { file: 'js/executeScript_content.js' }, function() {
            chrome.tabs.executeScript(tab.id, { code: 'getDomText();' });
        });
    });
}

function isSubtitleFile(tab){
    if(tab.url.match(".srt$") != null ){
        return true;
    }
    return false;
}

ChromeAPI.executeDomTranslation = function(tab, wordToTranslate, translatedWord) {
    console.log("isSubtitleFile(tab): "+isSubtitleFile(tab));
    chrome.tabs.executeScript(tab.id, { code: "var subtitleFile='" + isSubtitleFile(tab) + "';" });
    chrome.tabs.executeScript(tab.id, { code: "var oldString='" + wordToTranslate + "';" });
    chrome.tabs.executeScript(tab.id, { code: "var newString='" + translatedWord + "';" });
    chrome.tabs.executeScript(tab.id, { code: "recursiveReplace();"});
}