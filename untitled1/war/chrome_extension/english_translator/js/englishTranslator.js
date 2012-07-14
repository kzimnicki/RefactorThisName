var EnglishTranslator = EnglishTranslator || {};

var MAX_PART_SIZE = 1850;
var available = true;
var translateCounter = 0;
var currentTab;

EnglishTranslator.start = function(tab, textFromHtml) {
    currentTab = tab;
    var dataToTranslate = EnglishTranslator.createDataToTranslateJSON(localStorage.min, localStorage.max, textFromHtml, currentTab.url);
    ajaxExecutor.ajaxExtractWordsWithFrequency(dataToTranslate, function(wordsArray) {
        EnglishTranslator.createWordsTable(wordsArray);
    });
}

EnglishTranslator.createDataToTranslateJSON = function(min, max, text, url) {
    var dataToTranslate = {"minFrequency":min,"maxFrequency":max,"text":text,"textUrl":url};
    log(dataToTranslate);
    return dataToTranslate;
}

EnglishTranslator.createWordsTable = function(words) {
    console.log(words);
    var numberOfWordsToTranslate = words.length;
    popup.createTable(words);
}

EnglishTranslator.translateWords = function(wordsArray, text) {
    console.log(wordsArray);
    var googleTranslateWordParts = splitWordsToGoogleTranslateParts(wordsArray);
    for (var x in googleTranslateWordParts) {
        translateCounter++;
        translate(googleTranslateWordParts[x], text);
    }
}

EnglishTranslator.splitWordsToGoogleTranslateParts = function(wordsArray) {
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
//    log(wordsPart);
    return wordsPart;
}

this.translate = function(words, text) {
    ajaxExecutor.translate(words, function(translatedData) {
            var translatedMapJSON = createTranslatedBrackets(words, translatedData, text);
            sendTranslatedWords(translatedMapJSON);
        }
    );
}

EnglishTranslator.createTranslatedBrackets = function(words, translatedData, text) {
    var i = 0;
    var translatedMapJSON = {};
    translatedData.forEach(function(item) {
        var wordToTranslate = words[i++];
        var translatedWord = item[0];
        console.log(i, wordToTranslate, translatedWord);
        if (wordToTranslate.toLowerCase() != translatedWord.toLowerCase()) {
            translatedMapJSON[wordToTranslate] = translatedWord;
        } else {
            console.log(translatedWord, "Tego translate nie przetlumaczyl");
        }
    });
    return translatedMapJSON;
}

EnglishTranslator.putTranslationInText = function(translatedMapJSON, text){
    for(var key in translatedMapJSON){
         text = textReplace(text, key, translatedMapJSON[key]);
    }
    return text;
}



this.textReplace = function(text, oldString, newString) {
    var regex = new RegExp('(\\b' + oldString + '\\b)', 'gi');
    text = text.replace(regex, "$1(" + newString + ")");
    console.log(text);
    return text;
}

EnglishTranslator.sendTranslatedWords = function(translatedMapJSON) {
    log(translatedMapJSON);
    log(JSON.stringify(translatedMapJSON));
    ajaxExecutor.sendTranslatedWords(translatedMapJSON, function() {
        log("przetlumaczone slowa wyslane");
        if (--translateCounter == 0) {
//                window.close();
        }
    });
}