package server.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

/**
 * User: kzimnick
 * Date: 25.09.12
 * Time: 18:26
 */
public class CsvMessageConverter extends AbstractHttpMessageConverter<CsvResponse> {

   public static final MediaType MEDIA_TYPE = new MediaType("text", "csv", Charset.forName("utf-8"));
   public CsvMessageConverter() {
       super(MEDIA_TYPE);
   }

   protected boolean supports(Class<?> clazz) {
       return CsvResponse.class.equals(clazz);
   }

    @Override
    protected CsvResponse readInternal(Class<? extends CsvResponse> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void writeInternal(CsvResponse response, HttpOutputMessage output) throws IOException, HttpMessageNotWritableException {
       output.getHeaders().setContentType(MEDIA_TYPE);
       output.getHeaders().set("Content-Disposition", "attachment; filename=\"" + response.getFilename() + "\"");
       List<String> allRecords = response.getRecords();
       for (int i = 1; i < allRecords.size(); i++) {
            String rec = allRecords.get(i);
           output.getBody().write(rec.getBytes());
       }
   }
}