import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jiho on 2017. 6. 1..
 */
public class WinFuncParser {


    public WinFuncParser() {

    }

    public String parse(String target) throws Exception {

//        System.out.println("Target: " + target);

        HttpURLConnection conn = (HttpURLConnection) new URL(target).openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String str = "";
        String line;

        while ((line = br.readLine()) != null) {

            if (line.contains("<pre>")) {
                br.readLine();

                while ((line = br.readLine()).contains("</pre>") == false) {
                    str += line;
                    // todo some options to change raw function
                }
                System.out.println("ORG  : " + str);

                break;

                // todo handle parameter

                // ex) if contains DWORD -> swap to unsigned int

            }
        }
        conn.disconnect();
        br.close();

        return str;
    }




}
