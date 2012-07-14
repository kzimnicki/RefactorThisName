var ajaxExecutor = ajaxExecutor || {};

var AJAX_TYPE_POST = 'POST';
var CURRENT_TYPE_JSON = 'application/json; charset=utf-8;';
var DATA_TYPE_JSON = 'JSON'
//var SERVER_URL = "http://188.40.66.81:443/";
var SERVER_URL = "http://localhost:8881/";
var TRANSLATE_URL = 'http://translate.googleapis.com/translate_a/t?anno=3&client=tee&format=html&v=1.0&logld=v7&tl=pl'; //96 chars +  (word size +3 chars) * words counts
var isLogged = false;

this.serverDown = function() {
    popup.writeError("Serwer nie odpowiada, prosze sprobowac pozniej.");
}


this.setup = function() {
    showDimmer();
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

function showDimmer(){
    $("#progressBar").css("display","block");
}

function hideDimmer(){
    $("#progressBar").css("display","none");
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
    console.log("cookie1: "+document.cookie);
}


ajaxExecutor.isLogged = function() {
    var result = false;
    if (isLogged || getBase64()) {
        result = true;
    }
    console.log("isLogged: " + result);
    return result;
}

ajaxExecutor.removeCookie = function() {
    document.cookie = "Authorization=;expires=Thu, 01-Jan-70 00:00:01 GMT;";
}

this.getBase64FromCookies = function() {
    console.log("cookie: "+document.cookie);
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
    }).done(hideDimmer);
}

ajaxExecutor.ajaxExtractWordsWithFrequency = function(dataToTranslate, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/extractWordsWithFrequency",
        data: JSON.stringify(dataToTranslate),
        success: callback
    }).done(hideDimmer);
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
    }).done(hideDimmer);
}

ajaxExecutor.sendTranslatedWords = function(translatedMapJSON, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/saveTranslatedWords",
        data: JSON.stringify(translatedMapJSON),
        success: callback
    }).done(hideDimmer);
}

ajaxExecutor.sendExcludedWords = function(excludedWordsJSON, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/saveExcludedWords",
        data: JSON.stringify(excludedWordsJSON),
        success: callback
    }).done(hideDimmer());
}

ajaxExecutor.removeExcludedWord = function(word, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/removeExcludedWord",
        data: word,
        success: callback
    }).done(hideDimmer());
}

ajaxExecutor.register = function(username, password, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/register",
        data: '{"username":"' + username + '", "password": "' + password + '"}',
        success: callback
    }).done(hideDimmer());
}

ajaxExecutor.login = function(username, password, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/login",
        data: '{"username":"' + username + '", "password": "' + password + '"}',
        success: callback
    }).done(hideDimmer());
}

ajaxExecutor.loadExcludedWords = function(callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/excludedWords",
        success: callback
    }).success(hideDimmer);
}


ajaxExecutor.loadOptions = function(callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/options",
        type: "GET",
        success: callback
    }).success(hideDimmer);
}


ajaxExecutor.saveOptions = function(optionsData, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/options",
        type: "POST",
        data: optionsData,
        success: callback
    }).success(hideDimmer);
}



