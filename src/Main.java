import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;


/*
 *
 * Created by Jiho, Eric, Vanessa on 2017. 6. 1..
 *
 * References
    https://www.youtube.com/watch?v=OuPjoiXq9gg
 */

public class Main {

    //  Target URL: http://www.win32-api.narod.ru/

    public static void main(String[] args) throws Exception {

        // Retrieve Target URLS

    /*
        URLParser up = new URLParser();
        List<String> URLs = up.retrieveFunctionInformation();

        // Retrieve MSDN URLs and write to file.

        FileWriter writer = new FileWriter("./msdn_urls.txt");
        for(String url: URLs) {
            writer.write(url + "\r\n");
        }
        writer.close();
    */

        List<String> URLs = new ArrayList<>();

        FileReader fr = new FileReader("./msdn_urls.txt");
        BufferedReader br = new BufferedReader(fr);

        String currentLine;

        int num = 0;
        while ((currentLine = br.readLine()) != null) {
//            System.out.println(num + ": " + currentLine);
            num++;
            URLs.add(currentLine);
        }

        // if directory not exist, create

        File dir = new File("log");
        if ( !dir.exists() ) {
            System.out.println("Creating directory: " + dir.getName());
            try {
                dir.mkdir();
            } catch(SecurityException se) {
                //handle it
            }
        }


        Visitor visitor = new Visitor();
        FileWriter fw = new FileWriter("./log/output.txt");

        int i = 0;

//        Scanner scanIn = new Scanner(System.in);
//        sWhatever = scanIn.nextLine();
//        scanIn.close();
//        System.out.println(sWhatever);


        int index = 626; // Index

        for (String url : URLs) {

            if (url.equals("https://msdn.microsoft.com/en-us/library/aa383667")) {
                continue;
            }

            // This line is to skip the existing functions
            if (i++ < index - 2) { continue; }

//            String str = visitor.retrieveFunctionInformation(url);

//            visitor.retrieveManualPage(url);
            WinAPIFunction winFunc = visitor.buildFunction(url);



            String out = winFunc.getSyntax();
            System.out.println(out);

//            fw.write(out + "\r\n");
//            fw.write("\r\n");

        }

        fw.close();


    }

}
