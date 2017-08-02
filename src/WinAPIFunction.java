




import function.*;

import java.util.ArrayList;
import java.util.List;




public class WinAPIFunction {

    public List<FuncParameter> funcParameters;
    public FuncReturnValue funcReturn;

    public String syntax;
    public List<String> keywords;
    public Boolean hasErrorCode;
    public String ErrorCode;



    // Syntax TODO

    public WinAPIFunction() {
        keywords = new ArrayList<String>();
    }

    public WinAPIFunction(String syntax) {
        this.syntax = syntax;
        this.funcParameters = new ArrayList<FuncParameter>();
        parseSyntax();
        this.hasErrorCode = false;
        this.ErrorCode = "";
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
        System.out.println(paramPart);
        while (paramPart.contains(",")) {
            String currentParam = paramPart.substring(0, paramPart.indexOf(","));
            paramPart = paramPart.substring(paramPart.indexOf(",") + 1);
            createParams(currentParam.trim());
        }
        System.out.println(paramPart);
    }


    private void createParams(String paramPart) {
        // "DWORD dwAdditionalFlags"
        // or "void *fInfoLevelId"
        //paramPart = "void *fInfoLevelId";
        String paramType;
        String paramName;
        System.out.println("paramPart = " + paramPart);
        if (paramPart.contains("*")) {
            paramType = paramPart.substring(0, paramPart.lastIndexOf("*") + 1);
            paramName = paramPart.substring(paramPart.lastIndexOf("*") + 1);
            System.out.println("paramType = " + paramType);
            System.out.println("paramName = " + paramName);
        }
        else {
            paramType = paramPart.substring(0, paramPart.indexOf(" ") + 1);
            paramName = paramPart.substring(paramPart.indexOf(" ") + 1);
            System.out.println("paramType = " + paramType);
            System.out.println("paramName = " + paramName);
        }
        FuncParameter param = new FuncParameter(createType(paramType), paramName)
        this.funcParameters.add(param);
    }

    private Type createType(String typeName) {
        // if type is a pointer
        if (typeName.contains(" *")) {
            String pointingTo = typeName.substring(0, typeName.indexOf(" "));
            return new Type(true, createType(pointingTo));
        }
        else if (typeName.contains("* ")) {
            String pointingTo = typeName.substring(0, typeName.indexOf("*"));
            return new Type(true, createType((pointingTo)));
        }
        // TODO : needs another case where typeName is defined in type dictionary

        // if type is not a pointer
        else {
            return new Type(false, NULL);
        }
    }

}
