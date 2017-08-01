




import function.FuncParameter;
import function.FuncReturnValue;

import java.util.ArrayList;
import java.util.List;




public class WinAPIFunction {

    public List<FuncParameter> funcParameters;
    public FuncReturnValue funcReturn;

    public String syntax;
    public List<String> keywords;

    // Syntax TODO

    public WinAPIFunction() {
        keywords = new ArrayList<String>();
    }

    public WinAPIFunction(String syntax) {
        this.syntax = syntax;
        this.funcParameters = new ArrayList<FuncParameter>();
        parseSyntax();
    }

    public String getSyntax() {
        return this.syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public void addKeywords(String keyword) {
        this.keywords.add(keyword);
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

    /* assume that syntax is in the following format:

    HANDLE FindFirstFileEx(LPCTSTR lpFileName, void *fInfoLevelId, LPVOID lpFindFileData, void *fSearchOp, LPVOID lpSearchFilter, DWORD dwAdditionalFlags) {}
     */
    private void parseSyntax() {
        // temporary function syntax
        this.syntax = "HANDLE FindFirstFileEx(LPCTSTR lpFileName, void *fInfoLevelId, LPVOID lpFindFileData, void *fSearchOp, LPVOID lpSearchFilter, DWORD dwAdditionalFlags) {}";
        int beginIndex = this.syntax.indexOf("(");
        int endIndex = this.syntax.indexOf(")");
        String paramPart = this.syntax.substring(beginIndex + 1, endIndex);
        while (paramPart.contains(",")) {
            String currentParam = paramPart.substring(0, paramPart.indexOf(","));
            paramPart = paramPart.substring(paramPart.indexOf(",") + 1);
        }
        System.out.println(paramPart);
    }

}
