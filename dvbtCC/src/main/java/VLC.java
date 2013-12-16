
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class VLC {

    private static BufferedReader br;

    public static BufferedReader get(){
        if(br == null){
            ProcessBuilder builder = new ProcessBuilder("vlc", "-vvv","--ts-es-id-pid","dvb://frequency=570000000","--vbi-text","--vbi-page=150","--vbi-position=9","--no-vbi-opaque");
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


    public static void main(String[] args) throws IOException {

//
//        String line = br.readLine();
//        while (line != null) {
//            if(line.contains("##CC##"))
//            System.out.println(line.substring(74));
//            line = br.readLine();
//        }
    }
}
