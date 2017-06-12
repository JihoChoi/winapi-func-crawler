import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiho on 2017. 6. 1..
 * Ref
    https://www.youtube.com/watch?v=OuPjoiXq9gg
 */

public class Main {

//  Target URL: http://www.win32-api.narod.ru/

    public static void main(String[] args) throws Exception {

        // Retrieve Target URLS

    /*
        WinURLParser up = new WinURLParser();
        List<String> URLs = up.parse();

        FileWriter writer = new FileWriter("./msdn_urls.txt");
        for(String url: URLs) {
            writer.write(url + "\r\n");
        }

        writer.close();
    */

        List<String> URLs = new ArrayList<>();

        FileReader fr = new FileReader("./msdn_urls.txt");
        BufferedReader br = new BufferedReader(fr);
        // br = new BufferedReader(new FileReader("./msdn_urls.txt"));

        String currentLine;

        int num = 0;
        while ((currentLine = br.readLine()) != null) {
            System.out.println(num + ": " + currentLine);
            num++;
            URLs.add(currentLine);
        }

        WinFuncParser fp = new WinFuncParser();
        FileWriter fw = new FileWriter("./Output.txt");

        int i = 0;
        for (String url : URLs) {

            if (url.equals("https://msdn.microsoft.com/en-us/library/aa383667")) {
                continue;
            }

            // This line is for skipping the existing functions
            if (i++ < 11) { continue; }


//          skip removed num, 112
//            i++;

            System.out.println("#" + i);
            System.out.println("URL  : " + url);

            String str = fp.parse(url);

            str = str.replace("_IN_", "");
            str = str.replace("_In_", "");
            str = str.replace("__in", "");

            str = str.replace("_Out_", "");
            str = str.replace("__out", "");

            str = str.replace("_Inout_", "");

            str = str.replace("opt_", ""); // this method is to remove _In_opt_

            /* CSS Tag removal */

            str = str.replace("<span style=\"color:Blue;\">", "");
            str = str.replace("</span>", "");
            str = str.replace("WINAPI", "");

            System.out.println("FUNC : " + str + "\n");

//            fw.write("URL  : " + url + "\r\n");
//            fw.write("FUNC : " + str + "\r\n");

            fw.write(str + "\r\n");
            fw.write("\r\n");

            // todo file io
        }

        fw.close();


    }

}
