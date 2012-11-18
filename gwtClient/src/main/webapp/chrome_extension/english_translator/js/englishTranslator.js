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
        var translatedWord = item;
            translatedMapJSON[wordToTranslate] = translatedWord;
    });
    return translatedMapJSON;
}

EnglishTranslator.putTranslationInText = function(translatedWords, text, pattern){
    for(var key in translatedWords){
        if(key.toLowerCase() != translatedWords[key][0].toLowerCase()){
            text = textReplace(text, key, translatedWords[key], pattern);
        }
    }
    return text;
}



this.textReplace = function(text, oldString, newString, pattern) {
    pattern = pattern.replace('@@TRANSLATED_TEXT@@', newString);
    var regex = new RegExp('(\\b' + oldString + '\\b)', 'gi');
    text = text.replace(regex, "$1" + pattern);
    return text;
}

EnglishTranslator.loadFile = function(file) {

        if (file.type != "video/mp4") {
            console.log(file.type + " is wrong type!");
            return;
        }

        var fileURL = URL.createObjectURL(file);

        var fr = new FileReader();
        var fr2 = new FileReader();

        fr.onload = function(e) {
            fr2.onload = function(z) {
                var hashData = {"size":file.size,"head":e.target.result,"tail":z.target.result};
                ajaxExecutor.hash(hashData, function(subtitle) {
                    $("#drop_zone").hide();
                    $('#video').append('<video width="770" height="450" src="' + fileURL + '" type="video/mp4" id="player1" controls="controls"></video>');
                    videoSub.videosub_main(subtitle);
                });
            }
            var blob = file.slice(file.size - 64 * 1024, file.size);
            fr2.readAsDataURL(blob);
        }
        var blob = file.slice(0, 64 * 1024);
        fr.readAsDataURL(blob);
}
