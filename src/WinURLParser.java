import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiho on 2017. 6. 1..

 This class is used to get all the MSDN urls from the target page.

 String target = "http://www.win32-api.narod.ru/";

 */

public class WinURLParser {


    public WinURLParser() {

    }


    public List<String> parse() throws Exception {

        List<String> msdnURLs = new ArrayList<>();

        String target = "http://www.win32-api.narod.ru/";

        HttpURLConnection conn = (HttpURLConnection) new URL(target).openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String str = "";
        String line;

        int i = 0;

        while ((line = br.readLine()) != null) {
            if (line.contains("msdn.microsoft.com/en-us/library")) {

                String url = line.split("href=\"http:")[1].split("%")[0];
                url = "https:" + url;
                System.out.println(i + ": " + url);
                i++;
                msdnURLs.add(url);

                // this line is for testing purpose
                // if ( i == 4 ) break;

                // Todos: change WIN parameter to c++ parameter



            }
        }
        conn.disconnect();
        br.close();

        return msdnURLs;
    }


}
