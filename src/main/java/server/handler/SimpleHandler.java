package server.handler;

import server.api.EnglishTranslator;
import server.api.DataToTranslate;
import server.core.ClientDataConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SimpleHandler{

    public static final int MAX_CONTENT_LENGTH = 1000000;

    private EnglishTranslator englishTranslator;

    public SimpleHandler() {
        super();
        englishTranslator = new EnglishTranslator();
    }

//    public void handle(String s, HttpServletRequest request, HttpServletResponse response, int i) throws IOException, ServletException {
//        response.setContentType("text/html");
//        response.setStatus(HttpServletResponse.SC_OK);
////        ((Request) request).setHandled(true);
//
//        String contentLength = request.getHeader("Content-Length");
//        if (contentLength != null && Integer.valueOf(contentLength) > MAX_CONTENT_LENGTH) {
//            System.out.println("__ERR: Too big REQUEST: " + contentLength);
//            response.setStatus(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
//        } else if (request.getParameter("tw") != null) {
//            saveTranslatedWords(request);
//        } else {
//            String httpResponse = extractWords(request);
//            response.getWriter().append(httpResponse);
//        }
//    }

    private void saveTranslatedWords(HttpServletRequest request) {
        Map<String, String> translatedWords = ClientDataConverter.convertTranslatedWords(request);
//        englishTranslator.saveTranslatedWords(translatedWords);
    }

//    private String extractWords(HttpServletRequest request) {
//        DataToTranslate dataToTranslate = ClientDataConverter.convert(request);
//        List<String> wordsToTranslate = englishTranslator.extractWords(dataToTranslate);
//        String httpResponse = createResponseInHttpFormat(wordsToTranslate);
//        return httpResponse;
//
//    }

    private String createResponseInHttpFormat(List<String> wordsToTranslate) {
        StringBuilder builder = new StringBuilder();
        String delim = "";
        for (String word : wordsToTranslate) {
            builder.append(delim);
            delim = ":";
            builder.append(word);
        }
        return builder.toString();
    }
}
