import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.lang.Character;

/*
 *
 * Author : Eric Song
 * Date   : 2017. 6. 26
 * 
 */

public class Generator {
	
    public static void main(String[] args) throws Exception {

        // Retrieve Target URLS  
    	List<String> error_array = new ArrayList<>();
    
    	FileReader in_fr = new FileReader("./input.txt");
        BufferedReader br = new BufferedReader(in_fr);
        
        String line;
        int flag = 0;
        FileWriter fw = new FileWriter("output.txt");
        String error;
        String gen = "int x = __sparrow_top;" + "\r\n" + "if(";
        
        line = br.readLine();
        do{
        	if((line.contains("ERROR_") && !line.equals("ERROR_SUCCESS")) || (line.contains("NERR_") && !line.equals("NERR_Success"))){
        		error_array.add(line);
        	}
        	line = br.readLine();
        }while(line != null);
        	
        br.close();
        /*for(String str : error_array){
        	System.out.println(str);
        }*/
        //FileReader fr = new FileReader("./error_list.txt");
        for(int i = 0; i < error_array.size(); i++){
        	FileReader fr = new FileReader("./error_list.txt");
        	br = new BufferedReader(fr);
            error = br.readLine();
        	while(error != null){
        		String err = error_array.get(i);
        		System.out.println(err);
            	if(error.equals(err)){
            			error = br.readLine();
            			error = br.readLine();
            			String temp = "";
            			int y = 0;
            			while(error.charAt(y) != '('){
            				temp += error.charAt(y);
            				//System.out.println(error.charAt(y));
            				y++;
            			}
            			error_array.set(i, temp);
            			//br.close();
            			break;
            	}
            	error = br.readLine();
            }
        }
        
        for (int i =0; i < error_array.size(); i ++) {
        	if(!(i + 1 == error_array.size())){
        		gen += " x == " + error_array.get(i) + "||";
        	}	
        	else{
        		gen += " x == " + error_array.get(i) + ")";
        	}
        }
        gen += "{" + "\r\n";
        gen += "    return x;" + "\r\n" + "}" + "\r\n";
        gen += "else{" + "\r\n";
        gen += "    return 0;" + "\r\n";
        gen += "}";
        fw.write(gen + "\r\n");
        System.out.println(gen);
        fw.close();
    }
}
