var ajaxExecutor = ajaxExecutor || {};

var AJAX_TYPE_POST = 'POST';
var AJAX_TYPE_GET = 'GET';
var AJAX_TYPE_DELETE = 'DELETE';
var CURRENT_TYPE_JSON = 'application/json; charset=UTF-8';
var DATA_TYPE_JSON = 'JSON'
var SERVER_URL = "../";
//var SERVER_URL = "http://localhost:8080/RefactorThisName/"
var TRANSLATE_URL = 'http://translate.googleapis.com/translate_a/t?anno=3&client=tee&format=html&v=1.0&sl=en&tl=pl'; //96 chars +  (word size +3 chars) * words counts
var isLogged = false;


var INCLUDED_WORDS = "app/includedWords";
var EXCLUDED_WORDS = "app/excludedWords";
var EXTARCT_WORDS = "app/extractWords";
var INCLUDED_PHRASAL_VERB = "app/includedPhrasalVerbs";
var OPTIONS = "app/options";
var EXCLUDED_PHRASAL_VERB = "app/excludedPhrasalVerbs";


this.setup = function() {
    commonUtils.showDimmer();
    $.ajaxSetup({
            type : AJAX_TYPE_POST,
            contentType: CURRENT_TYPE_JSON,
            dataType: DATA_TYPE_JSON,
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Authorization", "Basic " + getBase64());
            },
            error: function (xhr, ajaxOptions, thrownError) {
                  errorHandling(xhr);
            }
        }
    );
}

this.errorHandling = function(xhr) {
    commonUtils.hideDimmer();
    if (window.errorHandler) {
        window.errorHandler(xhr.responseText);
    }
}

ajaxExecutor.extractWords = function(dataToTranslate, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + EXTARCT_WORDS,
        data: JSON.stringify(dataToTranslate),
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.hash = function(hashData, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/subtitle",
        data: JSON.stringify(hashData),
        dataType: "html",
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.translate = function(wordsArray, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/translate",
        data: JSON.stringify(wordsArray),
        type: AJAX_TYPE_POST,
        success: callback
    }).done(commonUtils.hideDimmer);
}

this.createUrl = function(wordsArray) {
    var url = [TRANSLATE_URL];
    for (var x in wordsArray) {
        url.push('&q=' + wordsArray[x]);
    }
    return url.join('');
}

ajaxExecutor.sendTranslatedWords = function(translatedMapJSON, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/translatedWords",
        data: JSON.stringify(translatedMapJSON),
        type: AJAX_TYPE_DELETE,
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.sendExcludedWords = function(excludedWordsJSON, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + EXCLUDED_WORDS,
        data: JSON.stringify(excludedWordsJSON),
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.removeExcludedWord = function(word, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + EXCLUDED_WORDS,
        data: JSON.stringify(word),
        type: AJAX_TYPE_DELETE,
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.loadExcludedWords = function(callback) {
    setup();
    $.ajax({
        url: SERVER_URL + EXCLUDED_WORDS,
        type: AJAX_TYPE_GET,
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.exportExcludedWords = function(callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/exportExcludedWords",
        type: AJAX_TYPE_GET,
        dataType: 'HTML',
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.exportIncludedWords = function(callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/exportIncludedWords",
        type: AJAX_TYPE_GET,
        dataType: 'HTML',
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.register = function(username, password, callback, errorCalback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/register",
        data: '{"username":"' + username + '", "password": "' + password + '"}',
        success: callback,
        error:  errorCalback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.resetPassword = function(username, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/resetPassword",
        data: JSON.stringify(username),
        success: callback
    }).done(commonUtils.hideDimmer);
}


ajaxExecutor.changePassword = function(username, password, key, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/changePassword",
        data: '{"username":"' + username + '", "newPassword":"'+password+'", "key":"'+key+'"}',
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.login = function(username, password, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + "app/login",
        data: '{"username":"' + username + '", "password": "' + password + '"}',
        success: callback
    }).done(commonUtils.hideDimmer);
}


ajaxExecutor.loadOptions = function(callback) {
    setup();
    $.ajax({
        url: SERVER_URL + OPTIONS,
        type: AJAX_TYPE_GET,
        success: callback
    }).done(commonUtils.hideDimmer);
}


ajaxExecutor.saveOptions = function(optionsData, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + OPTIONS,
        data: optionsData,
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.sendIncludedWords = function(includedWordsJSON, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + INCLUDED_WORDS,
        data: JSON.stringify(includedWordsJSON),
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.removeIncludedWord = function(word, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + INCLUDED_WORDS,
        data: JSON.stringify(word),
        type: AJAX_TYPE_DELETE,
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.loadIncludedWords = function(callback) {
    setup();
    $.ajax({
        url: SERVER_URL + INCLUDED_WORDS,
        type: AJAX_TYPE_GET,
        success: callback
    }).done(commonUtils.hideDimmer);
}


ajaxExecutor.sendIncludedPhrasalVerb = function(includedPhrasalVerbJSON, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + INCLUDED_PHRASAL_VERB,
        data: JSON.stringify(includedPhrasalVerbJSON),
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.removeIncludedPhrasalVerb = function(phrasalVerb, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + INCLUDED_PHRASAL_VERB,
        data: phrasalVerb,
        type: AJAX_TYPE_DELETE,
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.loadIncludedPhrasalVerb = function(callback) {
    setup();
    $.ajax({
        url: SERVER_URL + INCLUDED_PHRASAL_VERB,
        type: AJAX_TYPE_GET,
        success: callback
    }).done(commonUtils.hideDimmer);
}


ajaxExecutor.sendExcludedPhrasalVerb = function(excludedPhrasalVerbJSON, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + EXCLUDED_PHRASAL_VERB,
        data: JSON.stringify(excludedPhrasalVerbJSON),
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.removeExcludedPhrasalVerb = function(phrasalVerb, callback) {
    setup();
    $.ajax({
        url: SERVER_URL + EXCLUDED_PHRASAL_VERB,
        data: phrasalVerb,
        type: AJAX_TYPE_DELETE,
        success: callback
    }).done(commonUtils.hideDimmer);
}

ajaxExecutor.loadExcludedPhrasalVerb = function(callback) {
    setup();
    $.ajax({
        url: SERVER_URL + EXCLUDED_PHRASAL_VERB,
        type: AJAX_TYPE_GET,
        success: callback
    }).done(commonUtils.hideDimmer);
}




