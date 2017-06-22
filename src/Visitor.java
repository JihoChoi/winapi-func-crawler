import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jiho on 2017. 6. 1..
 *
 *
 *
 */


public class Visitor
{

    public Visitor()
    {
        System.out.println("=========================================================================");
        System.out.println("Visitor Class is to retrieve all the information from the WinAPI MSDN Page.");
        System.out.println("=========================================================================");
        System.out.println("");
    }

    /* Connect the URL and retrieve the markup as string buffer */

    public String retrieveFunctionInformation(String url) throws Exception {

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String str = "";
        String line;
        int hasFunctionFlag = 0;

        while ((line = br.readLine()) != null) {

            if (line.contains("<pre>"))
            {
                line = br.readLine();

                if (line.isEmpty()) {
                    line = br.readLine();
                }

                while (line.contains("</pre>") == false) {

                    str += line;
                    line = br.readLine();
                }
                hasFunctionFlag = 1;

                System.out.println("ORG  : " + str);
            }


            if (hasFunctionFlag == 1 && line.contains("Parameters"))
            {
                line = br.readLine();

                while (line.contains("See also") == false && line.contains("See Also") == false)
                {
                    str += addWord(line, str, "uffer");
                    str += addWord(line, str, "allocate");
                    str += addWord(line, str, "dynamic");
                    str += addWord(line, str, "must");
                    str += addWord(line, str, "handle");
                    str += addWord(line, str, "one of the following");
                    str += addWord(line, str, "file");
                    str += addWord(line, str, "free");
                    str += addWord(line, str, "create");
                    str += addWord(line, str, "delete");
                    // str += addWord(line, str, "");

                    line = br.readLine();

                }

                System.out.println("ORG 2  : " + str);
                break;
            }

        }
        conn.disconnect();
        br.close();

        return str;
    }




}
