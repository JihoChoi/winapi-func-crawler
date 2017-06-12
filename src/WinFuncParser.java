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

        System.out.println("Function Parser is to retrieve functions from the MSDN Page.");
        System.out.println("");

    }

    public String parse(String target) throws Exception {

//        System.out.println("Target: " + target);

        HttpURLConnection conn = (HttpURLConnection) new URL(target).openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String str = "";
        String line;

        while ((line = br.readLine()) != null) {

            if (line.contains("<pre>")) {
                line = br.readLine();

                if (line.isEmpty()) {
                    System.out.println("Current line is empty!");
                    line = br.readLine();
                }


                while (line.contains("</pre>") == false) {

                    str += line;
                    line = br.readLine();
                }
                System.out.println("ORG  : " + str);

                break;


//                while ((line = br.readLine()).contains("</pre>") == false) {
//                    str += line;
//                    // todo some options to change raw function
//                }

//                break;
                // todo handle parameter
                // ex) if contains DWORD -> swap to unsigned int
            }
        }
        conn.disconnect();
        br.close();

        return str;
    }




}
