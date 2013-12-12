import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: kz
 * Date: 10/13/13
 * Time: 6:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class MouseTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello World!");

        BufferedReader br = new BufferedReader(new FileReader(new File("/dev/input/mouse0")));

        while(true){
            int read = br.read();
            System.out.print(read);
            Thread.sleep(10);

        }
    }
}
