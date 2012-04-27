var ajaxExecutor = ajaxExecutor || {};

var AJAX_TYPE_POST = 'POST';
var CURRENT_TYPE_JSON = 'application/json; charset=utf-8;';
var DATA_TYPE_JSON = 'JSON'
//var SERVER_URL = "http://188.40.66.81:443/";
var SERVER_URL = "http://localhost:8881/";
var TRANSLATE_URL = 'http://translate.googleapis.com/translate_a/t?anno=3&client=tee&format=html&v=1.0&logld=v7&tl=pl'; //96 chars +  (word size +3 chars) * words counts

this.serverDown = function() {
    popup.writeError("Serwer nie odpowiada, prosze sprobowac pozniej.");
}


this.setup = function() {
    $.ajaxSetup({
            type : AJAX_TYPE_POST,
            contentType: CURRENT_TYPE_JSON,
            dataType: DATA_TYPE_JSON,
            beforeSend: function(xhr) {
                console.log(getBase64());
                xhr.setRequestHeader("Authorization", "Basic " + getBase64());
                console.log(xhr);
            },
            error: function() {
                serverDown();
            }
        }
    );
}


this.getBase64 = function() {
    if (window.localStorage.login) {
        return getBase64FromOptions();
    } else {
        return getBase64FromCookies();
    }
}

this.getBase64FromOptions = function() {
    var stringToEncode = window.localStorage.login + ":" + window.localStorage.pass;
    log(stringToEncode);
    return base64_encode(stringToEncode);
}

ajaxExecutor.saveCookie = function(username, password) {
    var header = base64_encode(username + ":" + password);
    document.cookie = "Authorization=" + header;
}

this.getBase64FromCookies = function() {
    var cn = "Authorization=";
    var idx = document.cookie.indexOf(cn)
    if (idx != -1) {
        var end = document.cookie.indexOf(";", idx + 1);
        if (end == -1) end = document.cookie.length;
        return unescape(document.cookie.substring(idx + cn.length, end));
    } else {
        return "";
    }
}


ajaxExecutor.ajaxExtractWords = function(dataToTranslate, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/extractWords",
        data: JSON.stringify(dataToTranslate),
        success: callback
    });
}

ajaxExecutor.ajaxExtractWordsWithFrequency = function(dataToTranslate, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/extractWordsWithFrequency",
        data: JSON.stringify(dataToTranslate),
        success: callback
    });
}


this.createUrl = function(wordsArray) {
    var url = [TRANSLATE_URL];
    for (var x in wordsArray) {
        url.push('&q=' + wordsArray[x]);
    }
    return url.join('');
}

ajaxExecutor.translate = function(wordsArray, callback) {
    setup();
    $.ajax({
        url: createUrl(wordsArray),
        type: 'GET',
        success: callback
    });
}

ajaxExecutor.sendTranslatedWords = function(translatedMapJSON, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/saveTranslatedWords",
        data: JSON.stringify(translatedMapJSON),
        success: callback
    });
}

ajaxExecutor.sendExcludedWords = function(excludedWordsJSON, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/saveExcludedWords",
        data: JSON.stringify(excludedWordsJSON),
        success: callback
    });
}

ajaxExecutor.removeExcludedWord = function(word, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/removeExcludedWord",
        data: word,
        success: callback
    });
}

ajaxExecutor.register = function(username, password, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/register",
        data: '{"username":"' + username + '", "password": "' + password + '"}',
        success: callback
    });
}

ajaxExecutor.login = function(username, password, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/login",
        data: '{"username":"' + username + '", "password": "' + password + '"}',
        success: callback
    });
}

ajaxExecutor.loadExcludedWords = function(callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/excludedWords",
        success: callback
    });
}




