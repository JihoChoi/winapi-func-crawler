import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jiho on 2017. 6. 22..
 */


public class WinAPIFunction {

    String syntax;
    List<String> keywords;


    // Syntax TODO

    String returnType;
    String name;
    List<String> parameters;

    public WinAPIFunction() {
        keywords = new ArrayList<String>();

    }

    public WinAPIFunction(String str) {
        this.syntax = str;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }
    public void addKeywords(String keyword) {

        keywords.add(keyword);
    }

    public void trim() {

        // remove all unnecessary keywords

        // System.out.println("BEFORE TRIM " + this.syntax);

        String str = this.syntax;

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
        str = str.substring(0, str.length() - 1);
        str = str + "{}";

        this.syntax = str;

        // System.out.println("AFTER TRIM " + this.syntax);

    }

}
