import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jiho on 2017. 6. 1..
 */
public class WinFuncParser {

    public WinFuncParser() {
        System.out.println("Function Parser is to retrieve functions from the MSDN Page.");
        System.out.println("");
    }
    public String addWord(String line, String str1, String keyword){
    	if (line.contains(keyword)){
    		if (!str1.contains(keyword))
    			return keyword;
    		else
    			return "";
    	}
    	else {
    		return "";
    	}
    }

	
    public String parse(String target) throws Exception {

//        System.out.println("Target: " + target);

        HttpURLConnection conn = (HttpURLConnection) new URL(target).openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        String str = "";
        String line;

        int hasFunc = 0;
        
        while ((line = br.readLine()) != null) {

            if (line.contains("<pre>")) {
                line = br.readLine();

                if (line.isEmpty()) {
//                    System.out.println("Current line is empty!");
                    line = br.readLine();
                }

                while (line.contains("</pre>") == false) {
                    str += line;
                    line = br.readLine();
                }
                hasFunc = 1;
                
                System.out.println("ORG 1  : " + str);
            }
            
            if (hasFunc == 1 && line.contains("Parameters")){
            	line = br.readLine();
            	
            	while (line.contains("See also") == false && line.contains("See Also") == false){
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
