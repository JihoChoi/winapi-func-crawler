import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;

/*
 *
 *
 * Created by Jiho on 2017. 6. 1..
 * References
    https://www.youtube.com/watch?v=OuPjoiXq9gg
 */

public class Main {

//  Target URL: http://www.win32-api.narod.ru/
	/*public static String addKeyword(String str1, String word){
		if (str1.contains(word)){
			return "//" + word;
		}
		else {
			return "";
		}
	}*/
    public static void main(String[] args) throws Exception {

        // Retrieve Target URLS
        List<String> URLs = new ArrayList<>();     
        
        FileReader fr = new FileReader("./msdn_urls.txt");
        BufferedReader br = new BufferedReader(fr);

        String currentLine;

        int num = 0;
        while ((currentLine = br.readLine()) != null) {
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


        WinFuncParser funcParser = new WinFuncParser();
        FileWriter fw = new FileWriter("./log/output.txt");

        int i = 0;

//        Scanner scanIn = new Scanner(System.in);
//        sWhatever = scanIn.nextLine();
//
//        scanIn.close();
//        System.out.println(sWhatever);


        int index = 835; // Index

        for (String url : URLs) {

            if (url.equals("https://msdn.microsoft.com/en-us/library/aa383667")) {
                continue;
            }

            // This line is for skipping the existing functions

            if (i++ < index - 2) { continue; }
            
            if (i > 892) break;
            
            String str = funcParser.parse(url);
            str = str.replace("_IN_", "");
            str = str.replace("_In_", "");
            str = str.replace("__in", "");
            str = str.replace("_Out_", "");
            str = str.replace("__out", "");
            str = str.replace("_Inout_", "");
            str = str.replace("opt_", ""); // this method is to remove _In_opt_
            str = str.replace("<span style=\"color:Blue;\">", "");
            str = str.replace("</span>", "");
            str = str.replace("WINAPI", "");

            str = str.replace("_Inout_", "");
            str = str.replaceAll("&nbsp;", "");
            str = str.replaceAll(String.valueOf((char)160), "");

            str = str.replaceAll("  ", " ");


            str = str.replaceAll("CALLBACK ", "");
            str = str.replaceAll("const", "");
            str = str.replaceAll("\\( ", "\\(");
            str = str.trim();

            /*if (str.length() != 0){
            	str = str.substring(0, str.length() - 1);
            }*/
            str = str.replaceAll(";", "");
            
            
            str = str + "{//";
            //str += addKeyword(str, "Buffer");
            //str += addKeyword(str, "allocate");
            
            if (str.contains("uffer")){
            	str = str.replaceAll("uffer", "");
            	str += "Buffer, ";
            }
            if (str.contains("allocate")){
            	str = str.replaceAll("allocate", "");
            	str += "allocate, ";
            }
            if (str.contains("dynamic")){
            	str = str.replaceAll("dynamic", "");
            	str += "dynamic, ";
            }
            if (str.contains("must")){
            	str = str.replaceAll("must", "");
            	str += "must, ";
            }
            if (str.contains("handle")){
            	str = str.replaceAll("handle", "");
            	str += "handle, ";
            }
            if (str.contains("one of the following")){
            	str = str.replaceAll("one of the following", "");
            	str += "one of the following, ";
            }
            if (str.contains("file")){
            	str = str.replaceAll("file", "");
            	str += "file, ";
            }
            if (str.contains("free")){
            	str = str.replaceAll("free", "");
            	str += "free, ";
            }
            if (str.contains("create")){
            	str = str.replaceAll("create", "");
            	str += "create, ";
            }
            if (str.contains("delete")){
            	str = str.replaceAll("delete", "");
            	str += "delete, ";
            }

            str = str + "}";
            
            System.out.println(str);
            fw.write(str + "\r\n");
            //fw.write("\r\n");

        }

        fw.close();
    }

}
