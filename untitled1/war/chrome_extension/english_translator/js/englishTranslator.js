var EnglishTranslator = EnglishTranslator || {};

var MAX_PART_SIZE = 1850;

EnglishTranslator.extractWords = function(text, callback) {
    var dataToTranslate = {"text":text,"url":""};
    ajaxExecutor.extractWords(dataToTranslate, function(words) {
        callback(words);
    });
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
    return wordsPart;
}

EnglishTranslator.translate = function(words, callback) {

    var googleTranslatorWordParts = EnglishTranslator.splitWordsToGoogleTranslateParts(words);
    for (var x in googleTranslatorWordParts) {
        translateWords(googleTranslatorWordParts[x], callback)
    }
}

this.translateWords = function(words, callback){
    ajaxExecutor.translate(words, function(translatedData) {
                console.log("inside translate");
                console.log(words);
                var translatedWords = createTranslatedBrackets(words, translatedData);
                callback(translatedWords);
            }
        );

}

this.createTranslatedBrackets = function(words, translatedData, text) {
    var i = 0;
    var translatedMapJSON = {};
    translatedData.forEach(function(item) {
        var wordToTranslate = words[i++];
        var translatedWord = item[0];
            translatedMapJSON[wordToTranslate] = translatedWord;
    });
    return translatedMapJSON;
}

EnglishTranslator.putTranslationInText = function(translatedWords, text){
    for(var key in translatedWords){
        if(key.toLowerCase() != translatedWords[key].toLowerCase()){
            text = textReplace(text, key, translatedWords[key]);
        }
    }
    return text;
}



this.textReplace = function(text, oldString, newString) {
    var regex = new RegExp('(\\b' + oldString + '\\b)', 'gi');
    text = text.replace(regex, "$1(" + newString + ")");
    return text;
}
