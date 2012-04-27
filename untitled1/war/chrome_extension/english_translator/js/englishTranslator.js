var EnglishTranslator = EnglishTranslator || {};

var MAX_PART_SIZE = 1850;
var available = true;
var translateCounter = 0;
var currentTab;

EnglishTranslator.start = function(tab, textFromHtml) {
    currentTab = tab;
    var dataToTranslate = createDataToTranslateJSON(localStorage.min, localStorage.max, textFromHtml, currentTab.url);
    ajaxExecutor.ajaxExtractWordsWithFrequency(dataToTranslate, function(wordsArray) {
        createWordsTable(wordsArray);
    });
}

this.createDataToTranslateJSON = function(min, max, text, url) {
    var dataToTranslate = {"minFrequency":min,"maxFrequency":max,"text":text,"textUrl":url};
    log(dataToTranslate);
    return dataToTranslate;
}

function createWordsTable(words) {
    log(words);
    var numberOfWordsToTranslate = words.length;
    popup.createTable(words);
}

EnglishTranslator.translateWords = function(wordsArray) {
    var googleTranslateWordParts = splitWordsToGoogleTranslateParts(wordsArray);
    for (var x in googleTranslateWordParts) {
        translateCounter++;
        translate(googleTranslateWordParts[x]);
    }
}

this.splitWordsToGoogleTranslateParts = function(wordsArray) {
    var wordsPart = [];
    var currentPart = [];
    for (var x in wordsArray) {
        if ((currentPart.length * 3) + currentPart.join('').length + wordsArray[x].length > MAX_PART_SIZE) { // razy 3 poniewaz trzeba obliczyc "&q="
            wordsPart.push(currentPart);
            currentPart = [];
        }
        currentPart.push(wordsArray[x]);
    }
    wordsPart.push(currentPart);
    log(wordsPart);
    return wordsPart;
}

this.translate = function(words) {
    ajaxExecutor.translate(words, function(translatedData) {
            var translatedMapJSON = createTranslatedBrackets(words, translatedData);
            var str = JSON.stringify(translatedMapJSON);
            sendTranslatedWords(translatedMapJSON);
        }
    );
}

this.createTranslatedBrackets = function(words, translatedData) {
    var i = 0;
    var translatedMapJSON = {};
    translatedData.forEach(function(item) {
        var wordToTranslate = words[i++];
        var translatedWord = item[0];
        log(i, wordToTranslate, translatedWord);
        if (wordToTranslate.toLowerCase() != translatedWord.toLowerCase()) {
            ChromeAPI.executeDomTranslation(currentTab, wordToTranslate, translatedWord);
            translatedMapJSON[wordToTranslate] = translatedWord;
        } else {
            log(translatedWord, "Tego translate nie przetlumaczyl");
        }
    });
    return translatedMapJSON;
}

this.sendTranslatedWords = function(translatedMapJSON) {
    log(translatedMapJSON);
    log(JSON.stringify(translatedMapJSON));
    ajaxExecutor.sendTranslatedWords(translatedMapJSON, function() {
        log("przetlumaczone slowa wyslane");
        if (--translateCounter == 0) {
//                window.close();
        }
    });
}