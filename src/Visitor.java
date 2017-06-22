import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
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


    public void retrieveManualPage(String url) throws Exception {


//        conn.disconnect();

        //        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        //        return br;
    }


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
//            if (hasFunctionFlag == 1 && line.contains("Parameters"))
//            {
//                line = reader.readLine();
//
//                String lowerLine = line.toLowerCase();
//
//                while (lowerLine.contains("see also") == false && lowerLine != null)
//                {
//
//                    System.out.println("line : " + line);
//
//                    String[] keywords = {
//                            "buffer", "dynamic", "must", "handle", "one of the following",
//                            "file", "handle", "allocate", "free", "open", "close", "create", "delete"
//                    };
//
//                    List<String> keywordsList = Arrays.asList(keywords);
//
//                    for (String e : keywordsList)
//                    {
//                        if (line.contains(e)) {
//                            System.out.println(e);
////                            windowsAPIFunction.addKeywords(e);
//                        }
//                    }
//                    line = reader.readLine();
//                }
//                break;
//            }

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




}
