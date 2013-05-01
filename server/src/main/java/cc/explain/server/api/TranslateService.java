package cc.explain.server.api;

import cc.explain.server.rest.Rest;

/**
 * User: kzimnick
 * Date: 27.04.13
 * Time: 18:22
 */
public class TranslateService {

    public static final String GOOGLE_API_URL = "http://translate.googleapis.com/translate_a/t?anno=3&client=tee&format=html&v=1.0&logld=v7&tl=pl&sl=en&ie=UTF-8&oe=UTF-8";
    public static final String PARAMETER_KEY = "q";

    public String translate(String english) {
        String[] response = Rest.get()
                .url(GOOGLE_API_URL)
                .addParameter(PARAMETER_KEY, english)
                .execute();

        return response[0];
    }

    public String[] translate(String[] englishWords) {
        Rest rest = Rest.get()
            .url(GOOGLE_API_URL);
        for (String word : englishWords){
            rest.addParameter(PARAMETER_KEY, word);
        }
        return rest.execute();
    }
}
