package cc.explain.server.utils;

/**
 * User: kzimnick
 * Date: 24.11.12
 * Time: 23:29
 */
public class StringUtils extends org.springframework.util.StringUtils {

    public static final String EMPTY = "";


    public static int length(String value){
        return value != null ? value.length() : 0;
    }


}
