package server.core;

import server.api.DataToTranslate;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * User: kzimnick
 * Date: 18.03.12
 * Time: 18:49
 */
public class ClientDataConverter {

    public static DataToTranslate convert(HttpServletRequest request) {
        Integer min = Integer.valueOf(request.getParameter("min"));
        Integer max = Integer.valueOf(request.getParameter("max"));
        String text = request.getParameter("text");
        String textUrl = request.getParameter("url");

        DataToTranslate dataToTranslate = new DataToTranslate();
        dataToTranslate.setMinFrequency(min);
        dataToTranslate.setMaxFrequency(max);
        dataToTranslate.setText(text);
        dataToTranslate.setTextUrl(textUrl);

        return dataToTranslate;
    }


    public static String convertToJson(Object o) {
        return new Gson().toJson(o);
    }

    ;

    public static Object convertToObject(String jsonString) {
        return new Gson().fromJson(jsonString, DataToTranslate.class);
    }

    ;

    public static HashMap<String, String> convertTranslatedWords(HttpServletRequest request) {
        String httpString = request.getParameter("tw");
        return new HashMap<String, String>();
    }
}
