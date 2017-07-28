import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jiho on 2017. 6. 1..
 *
 * This class is to retrieve all the information from the WinAPI MSDN Page.
 *
 */



public class WebVisitor
{

    public WebVisitor()
    {


    }

    /* Connect the URL and retrieve the markup as string buffer */



    public WinAPIFunction buildFunction(String url) throws IOException {


        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        WinAPIFunction windowsAPIFunction = new WinAPIFunction();

        String str = "";
        String line;
        int hasFunctionFlag = 0;

        while ((line = reader.readLine()) != null) {

            // Parsing Syntax from the MSDN Manual Page
            if (line.contains("<pre>"))
            {
                line = reader.readLine();

                if (line.isEmpty()) {
                    line = reader.readLine();
                }

                while (line.contains("</pre>") == false) {
                    str += line;
                    line = reader.readLine();
                }
                hasFunctionFlag = 1;
                System.out.println("ORG  : " + str);

                windowsAPIFunction.setSyntax(str);
                windowsAPIFunction.trim();
            }


//            // Searching Keywords
            if (hasFunctionFlag == 1 && line.contains("Parameters"))
            {
                line = reader.readLine();
                while (line != null && line.toLowerCase().contains("see also") == false)
                {
                    String lowerLine = line.toLowerCase();

                    String[] keywords = {
                            "buffer", "dynamic", "must", "handle", "one of the following",
                            "file", "handle", "allocate", "free", "open", "close", "create", "delete"
                    };

                    List<String> keywordsList = Arrays.asList(keywords);
                    for (String e : keywordsList)
                    {
                        if (lowerLine.contains(e)) {
                            windowsAPIFunction.addKeywords(e);
                        }
                    }
                    line = reader.readLine();
                }
                break;
            }

        }



        reader.close();

        return windowsAPIFunction;
    }



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


                System.out.println("ORG 2  : " + str);
                break;
            }

        }
        conn.disconnect();
        br.close();

        return str;
    }



    public MSDNPage retrieveManualPage(String url) throws Exception
    {

        // TODO Try Catch Error Handling

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        MSDNPage page = new MSDNPage(url);

        String line;
        String buffer = new String();

        while ((line = br.readLine()) != null) {
            buffer = buffer + line + "\n\r";

        }


        System.out.println(url);
        System.out.println(buffer);
        br.close();

        conn.disconnect();

        page.setManualPageBuffer(buffer);

        return page;
    }



}
