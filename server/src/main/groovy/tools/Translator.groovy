package tools

import java.util.concurrent.Callable
import cc.explain.server.rest.Rest

/**
 * User: kzimnick
 * Date: 13.04.13
 * Time: 15:49
 */
class Translator implements Runnable {


    List<String> words;
    GoogleWordTranslator translator
    int offset
    int start

    public Translator(int start, int offset, GoogleWordTranslator translator) {
        this.start = start;
        this.offset = offset;
        this.translator = translator;
    }

    void run() {
        def words = translator.findNotTranslatedWords(start, offset);
        println " >>> translating"
        def request = Rest.get().url("http://translate.googleapis.com/translate_a/t?anno=3&client=tee&format=html&v=1.0&logld=v7&tl=pl&sl=en&ie=UTF-8&oe=UTF-8")
        for (w in words){
            request.addParameter("q",w);
        }
        def response = request.execute()
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
