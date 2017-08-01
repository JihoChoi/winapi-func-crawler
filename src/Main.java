import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;


/**
 *
 *  @author     Jiho Choi, Eric Song, Vanessa Hwang
 *  @since      2017.6.1
 *
 *  Strategy Design Pattern
 *
 *  Flow
 *      [Main]
 *          (Optional ==> [WinAPIFunctionURLParser] : URLs)
 *              ==> [WebVisitor] : Buffer -> WinAPIFunction
 *                  ==> [CodeGenerator] : *.c file
 *
 *  References
 *      https://www.youtube.com/watch?v=OuPjoiXq9gg
 *      https://sourcemaking.com/design_patterns/strategy
 *
 */

public class Main {

    //  Target URL: http://www.win32-api.narod.ru/

    public static void main(String[] args) throws Exception {

//      /* Retrieve Target URLS */
//        WinAPIFunctionURLParser parser = new WinAPIFunctionURLParser();
//        List<String> URLs = parser.retrieveFunctionInformation();
//        FileWriter writer = new FileWriter("resource/msdn_target_urls.txt");
//        for(String url: URLs) {
//            writer.write(url + "\r\n");
//        }
//        writer.close();

        List<String> URLs = retrieveUrlList("resource/msdn_target_urls.txt");

        // if directory not exist, create
        createDir("log");
        createDir("temp");

        WebVisitor visitor = new WebVisitor();
        //FileWriter fw = new FileWriter("./temp/temp.txt");


        int index = 860;
        for (int i = 0; i < URLs.size(); i++)
        {
            if (i < index - 1) {
                continue;
            }

            System.out.println(i+1 + " : " + URLs.get(i) + " ");
            MSDNPage page = visitor.retrieveManualPage(URLs.get(i));

        }


//        int i = 0;
//        int index = 863; // Function Index of Google Sheet
//
//        for (String url : URLs)
//        {
//            if (url.equals("https://msdn.microsoft.com/en-us/library/aa383667")) {
//                continue;
//            }
//            // This line is to skip the existing functions
//            if (i++ < index - 2) {
//                continue;
//            }
//            MSDNPage page = visitor.retrieveManualPage(url);
//            System.out.println(url);
//            if (i++ < index + 50 -2) {
//                break;
//            }
//        }


    }


    public static List<String> retrieveUrlList(String path) throws IOException {
        List<String> URLs = new ArrayList<>();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        int num = 0;
        String currentLine;
        while ((currentLine = br.readLine()) != null) {
            URLs.add(currentLine);
            num++;
        }

        return URLs;
    }

    public static void createDir(String name)
    {
        File directory = new File(name);

        if (!directory.exists())
        {
            System.out.println("Creating directory: " + directory.getName());
            try {
                directory.mkdir();
            } catch(SecurityException se) {
                //handle it
            }
        }
    }

}

