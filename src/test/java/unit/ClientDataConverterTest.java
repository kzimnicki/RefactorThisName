package unit;

import server.api.DataToTranslate;
import server.core.ClientDataConverter;

import static junit.framework.Assert.assertEquals;

/**
 * User: kzimnick
 * Date: 24.03.12
 * Time: 17:03
 */
public class ClientDataConverterTest {

    public static final String JSON_STRING = "{\"minFrequency\":5,\"maxFrequency\":90,\"text\":\"Simple English text\",\"textUrl\":\"http://google.com\"}";

    @org.junit.Test
    public void testConvertToJson() throws Exception {
        DataToTranslate data = createData();

        String jsonString = ClientDataConverter.convertToJson(data);

        assertEquals(JSON_STRING, jsonString);

    }

    @org.junit.Test
    public void testConvertToObject() throws Exception {
        Object o = ClientDataConverter.convertToObject(JSON_STRING);

        assertEquals(o, createData());
    }

    private DataToTranslate createData() {
        DataToTranslate data = new DataToTranslate();
        data.setMaxFrequency(90);
        data.setMinFrequency(5);
        data.setText("Simple English text");
        data.setTextUrl("http://google.com");
        return data;
    }






}
