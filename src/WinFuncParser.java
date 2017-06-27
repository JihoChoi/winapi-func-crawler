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
 */
public class WinFuncParser {

    public WinFuncParser() {
        System.out.println("Function Parser is to retrieve functions from the MSDN Page.");
        System.out.println("");
    }
    /*public String addWord(String line, String str1, String keyword){
    	if (line.contains(keyword)){
    		if (!str1.contains(keyword))
    			return keyword;
    		else
    			return "";
    	}
    	else {
    		return "";
    	}
    }*/
    public void changeStatus(String line, WinFuncObj obj){
    	if (line.contains("File")){
//    		System.out.println("passed");
        	obj.file=true;
        }
        if (line.contains("allocate")){
        	obj.allocate=true;
        }
        if (line.contains("dynamic")){
        	obj.dynamic=true;
        }
        if (line.contains("must")){
        	obj.must=true;
        }
        if (line.contains("handle")){
        	obj.handle=true;
        }
        if (line.contains("returns")){
        	obj.returns=true;
        }
        if (line.contains("free")){
        	obj.free=true;
        }
        if (line.contains("create")){
        	obj.create=true;
        }
        if (line.contains("delete")){
        	obj.delete=true;
        }
        if (line.contains("buffer")){
        	obj.buffer=true;
        }
        
    }
    public WinFuncObj parse(String target) throws Exception {

//        System.out.println("Target: " + target);
    	WinFuncObj obj = new WinFuncObj();
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

                //while (line.contains("See also") == false && line.contains("See Also") == false) {
                while (line.contains("</pre>")==false) {
                    str += line;
                    line = br.readLine();
                    
                    //below code was for finding only buffer and size keywords
                    /*if (line == null){
                    	break;
                    }
                	if (line.contains("uffer")){
                		if (!str.contains("uffer"))
                			str += "uffer ";
                	}
                	if (line.contains("Size") || line.contains("size")){
                		if (!str.contains("Size") || ! line.contains("size"))
                			str += "size ";
                	}*/
                }
                changeStatus(str, obj);
                hasFunc = 1;
                
                //System.out.println("ORG  : " + str);
            }
            
            if (hasFunc == 1 && line.contains("Parameters")){
            	line = br.readLine();
            	String temp = new String();
            	while (line.contains("See also") == false && line.contains("See Also") == false){
            		// str += addWord(line, str, "");
            		temp += line;
            		line = br.readLine();
            		if(line.contains("ERROR_")){
            			for(int i = 0; i<line.length(); i++){
            				if(line.charAt(i) == 'E'){
            					if(line.substring(i, i+6).equals("ERROR_")){
            						int x = i;
            						String tep = "";
            						while(line.charAt(x) != '<'){
            							tep += line.charAt(x);
            							x++;
            						}
            						if(!tep.equals("ERROR_SUCCESS"))
            						obj.errors.add(tep);
            					}
            				}
            			}
            		}
            	}
            	changeStatus(temp, obj);
            	obj.str = str;
            	System.out.println("ORG  : " + obj.str);
            	break;
            }
            
        }
        conn.disconnect();
        br.close();

        return obj;
    }




}
