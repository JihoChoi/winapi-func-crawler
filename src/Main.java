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

        int index = 1051; // Index\
        WinFuncParser funcParser = new WinFuncParser();
        FileWriter fw = new FileWriter("./log/" + index+"-"+ (index+50) +".txt");

        int i = 0;

//        Scanner scanIn = new Scanner(System.in);
//        sWhatever = scanIn.nextLine();
//
//        scanIn.close();
//        System.out.println(sWhatever);

        for (String url : URLs) {
        	
            if (url.equals("https://msdn.microsoft.com/en-us/library/aa383667")) {
                continue;
            }

            // This line is for skipping the existing functions

            if (i++ < index - 2) { continue; }
            
            if (i > 1100) break;
            
            WinFuncObj obj = funcParser.parse(url);

            //String str = funcParser.parse(url);
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
            //str += addKeyword(str, "Buffer");
            //str += addKeyword(str, "allocate");
            obj.str = str;
            String funcs = "";
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
            	funcs += "    __sparrow_new();" + "\r\n";
            }
            if (obj.must == true){
            	str += "must,";
            	funcs += "    must" + "\r\n";
            }
            if (obj.handle == true){
            	str += "handle,";
            	funcs += "    __sparrow_deref();" + "\r\n";
            }
            if (obj.returns == true){
            	str += "returns,";
            	funcs += "    return" + "\r\n";
            }
            if (obj.free == true){
            	str += "free,";
            	funcs += "    __sparrow_delete();" + "\r\n";
            }
            if (obj.create == true){
            	str += "create,";
            	funcs += "    __sparrow_new();" + "\r\n";
            }
            if (obj.delete == true){
            	str += "delete,";
            	funcs += "    __sparrow_delete();" + "\r\n";
            }
            /*
            if (str.contains("allocate")){
            	str = strReverse(str);
            	str = str.replaceFirst(strReverse("allocate"), "");
            	str = strReverse(str);
            	str += "allocate, ";
            }
            if (str.contains("dynamic")){
            	str = strReverse(str);
            	str = str.replaceFirst(strReverse("dynamic"), "");
            	str = strReverse(str);
            	str += "dynamic, ";
            }
            if (str.contains("must")){
            	str = strReverse(str);
            	str = str.replaceFirst(strReverse("must"), "");
            	str = strReverse(str);
            	str += "must, ";
            }
            if (str.contains("handle")){
            	str = strReverse(str);
            	str = str.replaceFirst(strReverse("handle"), "");
            	str = strReverse(str);
            	str += "handle, ";
            }
            if (str.contains("returns one of the following")){
            	str = strReverse(str);
            	str = str.replaceFirst(strReverse("returns one of the following"), "");
            	str = strReverse(str);
            	str += "returns one of the following, ";
            }
            if (str.contains("file")){
            	str = strReverse(str);
            	str = str.replaceFirst(strReverse("file"), "");
            	str = strReverse(str);
            	str += "file, ";
            }
            if (str.contains("free")){
            	str = strReverse(str);
            	str = str.replaceFirst(strReverse("free"), "");
            	str = strReverse(str);
            	str += "free, ";
            }
            if (str.contains("create")){
            	str = strReverse(str);
            	str = str.replaceFirst(strReverse("create"), "");
            	str = strReverse(str);
            	str += "create, ";
            }
            if (str.contains("delete")){
            	str = strReverse(str);
            	str = str.replaceFirst(strReverse("delete"), "");
            	str = strReverse(str);
            	str += "delete, ";
            }
*/
            if(funcs != ""){
            	str += "\r\n" + funcs;
            }
            else{
            	str = str.replaceAll("found following keywords: ", "");
            	str += "found none" + "\r\n";
            }
            str = str + "}";
/*      
            if (str.contains("uffer") && str.contains("size")){
            	System.out.println("found");
                fw.write("found" + "\r\n");
            }
            else{
            	System.out.println("not found");
            	fw.write("\r\n");
            }*/
            System.out.println(i + ": " + str);
            fw.write(str + "\r\n");
            //fw.write("\r\n");

        }
        System.out.println("Crawling Finished");
        fw.close();
    }

}
