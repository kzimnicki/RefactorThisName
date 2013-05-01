package tools

import java.util.concurrent.Callable
import cc.explain.server.rest.Rest
import cc.explain.server.api.TranslateService

/**
 * User: kzimnick
 * Date: 13.04.13
 * Time: 15:49
 */
class Translator implements Runnable {


    List<String> words
    GoogleWordTranslator translator
    int offset
    int start
    TranslateService translateService

    public Translator(int start, int offset, GoogleWordTranslator translator, TranslateService translateService) {
        this.start = start;
        this.offset = offset;
        this.translator = translator;
        this.translateService = translateService;
    }

    void run() {
        String[] words = translator.findNotTranslatedWords(start, offset);
        println " >>> translating words size "+ words.size();
        def response = translateService.translate(words);
        def translatedWords = [:]
        for(int i = 0; i<words.size(); i++){
            if(!words[i].equalsIgnoreCase(response[i])){
                translatedWords[words[i]] = response[i]
            }
        }
        int size = translatedWords.size()
        println size
        translator.save(translatedWords)
    }
}
