import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;


/**
 *
 *  @author     Jiho, Eric, Vanessa
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

//        /* Retrieve Target URLS */
//
//        WinAPIFunctionURLParser parser = new WinAPIFunctionURLParser();
//        List<String> URLs = parser.retrieveFunctionInformation();
//        // Retrieve MSDN URLs and write to file.
//        FileWriter writer = new FileWriter("data/msdn_target_urls.txt");
//        for(String url: URLs) {
//            writer.write(url + "\r\n");
//        }
//        writer.close();

        List<String> URLs = new ArrayList<>();
        FileReader fr = new FileReader("data/msdn_target_urls.txt");
        BufferedReader br = new BufferedReader(fr);

        int num = 0;
        String currentLine;
        while ((currentLine = br.readLine()) != null) {
            URLs.add(currentLine);
            num++;
        }

        // if directory not exist, create
        createDir("log");
        createDir("temp");


        WebVisitor visitor = new WebVisitor();
        FileWriter fw = new FileWriter("./temp/temp.txt");

        int i = 0;
        int index = 863; // Index

        for (String url : URLs)
        {
            if (url.equals("https://msdn.microsoft.com/en-us/library/aa383667")) {
                continue;
            }

            // This line is to skip the existing functions
            if (i++ < index - 2) {
                continue;
            }


//            String str = visitor.retrieveFunctionInformation(url);

//            visitor.retrieveManualPage(url);

            MSDNPage page = visitor.retrieveManualPage(url);
            System.out.println(url);
            WinAPIFunction winFunc = visitor.buildFunction(url);


            String out = winFunc.getSyntax();
            System.out.println(out);

//            fw.write(out + "\r\n");
//            fw.write("\r\n");

        }

        fw.close();


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

