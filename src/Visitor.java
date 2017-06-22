import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
        String body;
        int hasFunctionFlag = 0;
        
        List<String> keywords = new ArrayList<String>();
        keywords.add("buffer");
        keywords.add("Buffer");
        keywords.add("allocate");
        keywords.add("handle");
        keywords.add("returns one of the following");
        keywords.add("free");
        keywords.add("size");
        keywords.add("dynamic");
        keywords.add("file");
        keywords.add("create");
        keywords.add("delete");
        
        List<String> has = new ArrayList<String>();

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
            	WinAPIFunction func = new WinAPIFunction(str);
                func.trim();
//                System.out.println("After trim : " + func.getSyntax());
                line = br.readLine();

                while (line.contains("See also") == false && line.contains("See Also") == false)
                {
                	for (String keyword : keywords) {
                		if (line.contains(keyword)) {
                			has.add(keyword);
                		}
                	}
                	keywords.removeAll(has);
                    line = br.readLine();

                }
                String result = func.getSyntax();
                result += "{";
                if (has.size() > 0) {
                	String keyList = has.toString();
                	result += keyList.substring(1, keyList.length() - 1);
                }
                result += "}";
                
                System.out.println("result : " + result);
//                System.out.println("ORG 2  : " + str);
                break;
            }

        }
        conn.disconnect();
        br.close();

        return str;
    }




}
