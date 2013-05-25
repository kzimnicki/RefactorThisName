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
    ajaxExecutor.translate(words, function(translatedData) {
                callback(translatedData);
            }
    );
}

EnglishTranslator.putTranslationInText = function(translatedWords, text, pattern){
    for(var key in translatedWords){
        if(key.toLowerCase() != translatedWords[key].toLowerCase()){
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
        var fileURL = URL.createObjectURL(file);

        var fr = new FileReader();
        var fr2 = new FileReader();

        fr.onload = function(e) {
            fr2.onload = function(z) {
                var hashData = {"size":file.size,"head":e.target.result,"tail":z.target.result, "filename":file.name};
                ajaxExecutor.hash(hashData, function(subtitle) {
                      window.setFileUrl(fileURL);
                      $("textarea")[0].innerText = subtitle;
                      $("#drop_zone").hide();
                });
            }
            var blob = file.slice(file.size - 64 * 1024, file.size);
            fr2.readAsDataURL(blob);
        }
        var blob = file.slice(0, 128 * 1024);
        fr.readAsDataURL(blob);
}
