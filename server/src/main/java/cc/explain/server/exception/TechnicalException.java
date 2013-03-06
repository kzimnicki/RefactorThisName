package cc.explain.server.exception;

import org.apache.xmlrpc.XmlRpcException;

/**
 * User: kzimnick
 * Date: 25.11.12
 * Time: 11:38
 */
public class TechnicalException extends RuntimeException {

    public TechnicalException(Throwable cause) {
       super(cause);
    }

     public TechnicalException(String message) {
       super(message);
    }
}
