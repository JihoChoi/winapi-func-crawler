import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Jiho on 2017. 6. 1..
 * Ref
    https://www.youtube.com/watch?v=OuPjoiXq9gg
 */
public class Main {

//    http://www.win32-api.narod.ru/

    public static void main(String[] args) throws Exception {


        WinURLParser up = new WinURLParser();
        List<String> URLs = up.parse();

        WinFuncParser fp = new WinFuncParser();

        FileWriter fw = new FileWriter("output.txt");

        for (String url : URLs) {

            if (url.equals("https://msdn.microsoft.com/en-us/library/aa383667")) {
                continue;
            }

            System.out.println("URL  : " + url);


            String str = fp.parse(url);

            System.out.println("FUNC : " + str);
            System.out.println("");

//            fw.write("URL  : " + url + "\r\n");
//            fw.write("FUNC : " + str + "\r\n");


            fw.write(str + "\r\n");
            fw.write("\r\n");

            // todo file io
        }

        fw.close();


    }

}
