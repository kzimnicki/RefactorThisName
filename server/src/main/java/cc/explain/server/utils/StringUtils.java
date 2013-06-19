package cc.explain.server.utils;

import cc.explain.server.exception.TechnicalException;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: kzimnick
 * Date: 24.11.12
 * Time: 23:29
 */
public class StringUtils extends org.springframework.util.StringUtils {

    public static final String EMPTY = "";
    public static final String UTF_8 = "UTF-8";


    public static int length(String value){
        return value != null ? value.length() : 0;
    }

    public static String md5(String value){
        if(value != null){
            byte[] md5 = new byte[0];
            try {
                md5 = DigestUtils.md5Digest(value.getBytes(UTF_8));
            } catch (UnsupportedEncodingException e) {
                throw new TechnicalException(e);
            }
            return new String(Hex.encode(md5));
        }
        return EMPTY;

    }


}
