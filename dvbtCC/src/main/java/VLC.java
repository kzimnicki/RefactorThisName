
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class VLC {

    private static BufferedReader br;

    public static BufferedReader get(){
        if(br == null){
            ProcessBuilder builder = new ProcessBuilder("vlc", "-vvv","--sub-track=0","--ts-es-id-pid","dvb://frequency=682000000","--vbi-text","--vbi-page=150","--vbi-position=9","--no-vbi-opaque");
            builder.redirectErrorStream(true);
            Process proc = null;
            try {
                proc = builder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
            br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        }
        return br;
    }


    public static void main(String[] args) throws Exception {

        BufferedReader br = VLC.get();
                    String line;
                    while((line = br.readLine()) != null){
                        System.out.println(line);
                        String subtitles = "";
                        if(line.length()> 74){
                            subtitles = line.substring(74);
                        }
                        if(line.contains("##CC##")  && StringUtils.isNotBlank(subtitles)) {
                            sendPost(String.format("http://localhost:8888/app/?v=%s", URLEncoder.encode(subtitles)));
                        }
                    }
    }
        private static void sendPost(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
//        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
//        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
}
