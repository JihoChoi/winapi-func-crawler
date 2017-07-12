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
	public static String strReverse(String org){
		String ret = new String();
		for(int i = org.length()-1; i >= 0; i--){
			ret = ret + org.charAt(i);
		}
		return ret;
	}
	
	public static int countNumOfWord(String org, String word){
		int count = 0;
		if (word.length() <= 0){
			return 0;
		}
		for(int i = 0; i < org.length()-word.length(); i ++){
			/*String temp = org.substring(i);
			if(temp.length() < word.length()){
				break;
			}*/
			
			if(org.charAt(i) == word.charAt(0)){
				String tmp1 = org.substring(i, word.length());
				if(tmp1 == word){
					count++;
					i += word.length();
				}
			}
		}
		return count;
	}
	
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

        int index = 1626; // Index\
        WinFuncParser funcParser = new WinFuncParser();
        FileWriter fw = new FileWriter("./log/" + index+"-"+ (index+49) +".c");

        int i = 0;

        for (String url : URLs) {
        	
            if (url.equals("https://msdn.microsoft.com/en-us/library/aa383667")) {
                continue;
            }

            // This line is for skipping the existing functions

            if (i++ < index -1 ) { continue; }
            //if (i > 1492) break;
            if (i > index+49) break;
            WinFuncObj obj = funcParser.parse(url);

            String str = obj.str;
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
            str = str.replaceAll(String.valueOf((char)160), "");
            str = str.replaceAll("  ", " ");
            str = str.replaceAll("CALLBACK ", "");
            str = str.replaceAll("const", "");
            str = str.replaceAll("\\( ", "\\(");
            str = str.trim();
            str = str.replaceAll(";", "");
            
            str = str + "{" + "\r\n" + "//found following keywords: ";
            //int flag = 0 ;
            String gen = "\r\n" + "    int x = __sparrow_top;" + "\r\n" +  "    if(";
            if(!obj.errors.isEmpty() && obj.returns){
            	String error = "";
            	for(int x = 0; x < obj.errors.size(); x++){
                	FileReader in_fr = new FileReader("./error_list.txt");
                	br = new BufferedReader(in_fr);
                	error = br.readLine();
                	while(error != null){
                		String err = obj.errors.get(x);
                    	if(error.equals(err)){
                    			error = br.readLine();
                    			error = br.readLine();
                    			String temp = "";
                    			int y = 0;
                    			while(error.charAt(y) != '('){
                    				temp += error.charAt(y);
                    				y++;
                    			}
                    			obj.errors.set(x, temp);
                    			break;
                    	}
                    	error = br.readLine();
                    }
                }
            }
            obj.str = str;
            String funcs = "";
            if (obj.handle == true){
            	str += "handle,";
            	funcs += "    __sparrow_deref();" + "\r\n";
            }
            if (obj.buffer == true){
            	str += "buffer,";
            	funcs += "    __sparrow_bufacc();" + "\r\n";
            }
            if (obj.file == true){
            	str += "file,";
            	funcs += "    __sparrow_fopen();" + "\r\n";
            }
            if (obj.allocate == true){
            	str += "allocate,";
            	funcs += "    __sparrow_new();" + "\r\n";
            }
            if (obj.dynamic == true){
            	str += "dynamic,";
            	if(funcs.contains("    __sparrow_new();")==false)
            		funcs += "    __sparrow_new();" + "\r\n";
            }
            if (obj.must == true){
            	str += "must,";
            	funcs += "    must" + "\r\n";
            }
            if (obj.returns == true){
            	str += "returns,";
            	funcs += "    return" + "\r\n";
            }
            if (obj.free == true){
            	str += "free,";
            	funcs += "    __sparrow_close_handle();" + "\r\n";
            }
            if (obj.create == true){
            	str += "create,";
            	if(funcs.contains("    __sparrow_new();")==false)
            		funcs += "    __sparrow_new();" + "\r\n";
            }
            if (obj.delete == true){
            	str += "delete,";
            	if(funcs.contains("    __sparrow_new();")==false)
            		funcs += "    __sparrow_delete();" + "\r\n";
            }

            if(funcs != ""){
            	str += "\r\n" + funcs;
            	str += "    return __sparrow_top;" + "\r\n";
            }
            else{
            	str = str.replaceAll("found following keywords: ", "");
            	str += "" + "\r\n";
            }
            
            for (int z =0; z < obj.errors.size(); z ++) {
            	if(!(z + 1 == obj.errors.size())){
            		gen += " x == " + obj.errors.get(z) + "||";
            	}	
            	else{
            		gen += " x == " + obj.errors.get(z) + ")";
            	}
            	/*if(obj.errors.get(z).contains("ERROR") || obj.errors.get(z).contains("NERR"))
            		flag = 1;*/
            }
            if(!gen.equals("    if(") && obj.returns && !obj.errors.isEmpty()){
                gen += "{" + "\r\n";
                gen += "        return x;" + "\r\n" + "    }" + "\r\n";
                gen += "    else{" + "\r\n";
                gen += "        return 0;" + "\r\n";
                gen += "    }" + "\r\n";
            	str += gen; 
            }
            str = str + "}";

            System.out.println(i + ": " + str);
            fw.write(str + "\r\n");
            

        }
        System.out.println("Crawling Finished");
        fw.close();
    }

}
