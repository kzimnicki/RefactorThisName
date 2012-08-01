package server;

import org.springframework.beans.factory.annotation.Autowired;
import server.api.DataToTranslate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: kzimnick
 * Date: 25.03.12
 * Time: 11:56
 */
@Controller
public class SpringWS {


    @RequestMapping(method = RequestMethod.GET, value = "/test.json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DataToTranslate> getTest() {
        System.out.println("TEST");
        ArrayList<DataToTranslate> dataToTranslates = new ArrayList<DataToTranslate>();
        DataToTranslate d = new DataToTranslate();
        d.setText("dsadas");
        dataToTranslates.add(d);
        return dataToTranslates;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/test2.json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DataToTranslate> getTest2(@RequestParam("value") int value) {
        System.out.println("TEST");
        ArrayList<DataToTranslate> dataToTranslates = new ArrayList<DataToTranslate>();
        DataToTranslate d = new DataToTranslate();
        d.setText("dsadas");
        dataToTranslates.add(d);
        return dataToTranslates;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/test3.json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void getTest3(@RequestBody DataToTranslate data) {
        System.out.println(data);
        ArrayList<String> strings = new ArrayList<String>();
        strings.add("Hello wordl1");
        strings.add("Hello wordl2");
        strings.add("Hello wordl3");
//        return strings;
    }
}
