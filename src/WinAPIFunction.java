




import function.*;

import java.util.*;


public class WinAPIFunction {

//    public List<FuncParameter> funcParameters;
    public HashMap<String, FuncParameter> funcParameters;
    public FuncReturnValue funcReturn;

    public String syntax;
    public List<String> keywords;
    public List<String> errorCodeKeyStringList;
//    public Boolean hasErrorCode;
//    public String ErrorCode;

    public HashMap<String, Type> typeDictionary;
    public MSDNPage msdnPage;


    public WinAPIFunction() {
        keywords = new ArrayList<String>();
    }

    // will receive MSDNPage in future
    public WinAPIFunction(String syntax) {
//        this.msdnPage = msdnPage;
        this.typeDictionary = createTypeDict();
//        this.syntax = msdnPage.syntax;
//        this.funcParameters = new ArrayList<FuncParameter>();
        this.funcParameters = new HashMap<String, FuncParameter>();
        this.errorCodeKeyStringList = new ArrayList<String>();
        this.errorCodeKeyStringList.add("one of the following error codes");



        parseSyntax();
        for (FuncParameter p : funcParameters.values()) {
            System.out.println("type: " + p.type.getName() + "   isPointer: " + p.type.isPointer + "   name: " + p.name);
        }
        parseParameter();
    }

    private HashMap<String, Type> createTypeDict() {
        HashMap<String, Type> newDict = new HashMap<String, Type>();

        Type voidP = new Type(true, new Type(false, null, "void"), "LPVOID");

        newDict.put("LPVOID", makeVoidP("LPVOID"));
        newDict.put("LPCTSTR", makeVoidP("LPCTSTR"));
        newDict.put("LPTSTR", makeVoidP("LPTSTR"));

        return newDict;
    }

    private Type makeVoidP(String name) {
        return new Type(true, new Type(false, null, "void"), name);
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
    }

    /* assume that syntax is in the following format:

    HANDLE FindFirstFileEx(LPCTSTR lpFileName, void *fInfoLevelId, LPVOID lpFindFileData, void *fSearchOp, LPVOID lpSearchFilter, DWORD dwAdditionalFlags) {}
     */
    private void parseSyntax() {
        // temporary function syntax
        this.syntax = "HANDLE FindFirstFileEx(LPCTSTR lpFileName, void* fInfoLevelId, LPVOID lpFindFileData, void *fSearchOp, LPVOID lpSearchFilter, DWORD dwAdditionalFlags) {}";
        this.syntax = "UINT NDdeGetErrorString(UINT uErrorCode, LPTSTR lpszErrorString, DWORD cBufSize)";

        // create function parameters
        int beginIndex = this.syntax.indexOf("(");
        int endIndex = this.syntax.indexOf(")");
        String paramPart = this.syntax.substring(beginIndex + 1, endIndex);
        while (paramPart.contains(",")) {
            String currentParam = paramPart.substring(0, paramPart.indexOf(","));
            paramPart = paramPart.substring(paramPart.indexOf(",") + 1);
            createParam(currentParam.trim());
        }
        createParam(paramPart.trim());

        // create function return value
        String funcNamePart = this.syntax.substring(0, beginIndex);
        System.out.println(funcNamePart);
        String returnType;
        if (funcNamePart.contains(" *")) {
            returnType = funcNamePart.substring(0, funcNamePart.indexOf("*"));
            System.out.println(returnType);
        }
        else {
            returnType = funcNamePart.substring(0, funcNamePart.indexOf(" "));
            System.out.println(returnType);
        }
        createReturnVal(returnType);

    }

    private void createReturnVal(String returnType) {
        Type returnT = createType(returnType);
        // if return Type is pointer, there can't be an errorList
        if (returnT.isPointer) {
            this.funcReturn = new FuncReturnValue(returnT, null);
        }
        else {
            if (hasErrorCode()) {
                this.funcReturn = new FuncReturnValue(returnT, getErrorCode());
            }
            else {
                this.funcReturn = new FuncReturnValue(returnT, null);
            }
        }
    }

    // TODO: for loop will be uncommented when MSDNPage is completed
    private boolean hasErrorCode() {
//        this.hasErrorCode = false;
        /*for (String errorCodeKeyString : this.errorCodeKeyStringList) {
            if (this.msdnPage.returnValue.contains(errorCodeKeyString)) {
                return true;
            }
        }*/
        return false;
    }

    // TODO: retrieve the list of error codes that will be returned from this.msdnPage.returnVal string
    private String getErrorCode() {
        return "";
    }


    private void createParam(String paramPart) {
        // "DWORD dwAdditionalFlags"
        // or "void *fInfoLevelId"
        //paramPart = "void *fInfoLevelId";
        String paramType;
        String paramName;
        if (paramPart.contains("*")) {
            paramType = paramPart.substring(0, paramPart.lastIndexOf("*") + 1);
            paramName = paramPart.substring(paramPart.lastIndexOf("*") + 1);
        }
        else {
            paramType = paramPart.substring(0, paramPart.indexOf(" ") + 1);
            paramName = paramPart.substring(paramPart.indexOf(" ") + 1);
        }
//            System.out.println("paramType = " + paramType);
//            System.out.println("paramName = " + paramName);
        FuncParameter param = new FuncParameter(createType(paramType), paramName);
        this.funcParameters.put(paramName, param);
    }

    private Type createType(String typeName) {
        typeName = typeName.trim();
        // if type is a pointer
        // "void *paramName"
        if (typeName.contains(" *")) {
            String pointingTo = typeName.substring(0, typeName.indexOf(" "));
            return new Type(true, createType(pointingTo), typeName);
        }
        // "void* paramName"
        else if (typeName.contains("*")) {
            String pointingTo = typeName.substring(0, typeName.indexOf("*"));
            return new Type(true, createType((pointingTo)), pointingTo + " *");
        }
        else if (this.typeDictionary.containsKey(typeName)) {
            return this.typeDictionary.get(typeName);
        }
        // if type is not a pointer
        else {
            return new Type(false, null, typeName);
        }
    }

    /* assume that parameters is in the following format:
    - there should be a new line in between parameters

    uErrorCode [in]
    The error code to be converted into an error string.

    lpszErrorString [out]
    A pointer to a buffer that receives the translated error string. This parameter cannot be NULL. If the buffer is not large enough to store the complete error string, the string is truncated.

    cBufSize [in]
    The size of the buffer to receive the error string, in TCHARs.
    */
    private void parseParameter() {
        String parameters = "uErrorCode [in]\n" +
                            "The error code to be converted into an error string.\n" +
                            "\n" +
                            "lpszErrorString [out]\n" +
                            "A pointer to a buffer that receives the translated error string. This parameter cannot be NULL. If the buffer is not large enough to store the complete error string, the string is truncated.\n" +
                            "\n" +
                            "cBufSize [in]\n" +
                            "The size of the buffer to receive the error string, in TCHARs.";
        String lines[] = parameters.split("\\r?\\n\\r?\\n");

        List<FuncParameter> buffers = new ArrayList<FuncParameter>();
        HashMap<FuncParameter, String> bufferSizes = new HashMap<FuncParameter, String>();

        for (String paramExp : lines) {
            String splited[] = paramExp.split("\\r?\\n");
            // assert (splited.length == 2);
            String paramName = splited[0];
            // set in/out/inout of a parameter
            String inout = null;
            if (paramName.contains("[")) {
                inout = paramName.substring(paramName.indexOf("[") + 1, paramName.indexOf("]"));
            }
            if (paramName.contains(" ")) {
                paramName = paramName.substring(0, paramName.indexOf(" "));
            }
            // just in case if there is no whitespace in between parameter name and [in/out/inout]
            if (paramName.contains("[")) {
                paramName = paramName.substring(0, paramName.indexOf("["));
            }
            String paramExplanation = splited[1];
            if (this.funcParameters.get(paramName) != null) {
                System.out.println(paramName);
                // CASE 1 : BUFFER ACCESS
                // cases when the parameter can be a pointer to a buffer
                if ((paramExplanation.startsWith("A pointer to a buffer")
                || (paramName.contains("Buffer") && !paramName.contains("Size") && !paramName.contains("Length"))
                || paramName.contains("lpsz")) && this.funcParameters.get(paramName).type.isPointer) {
                    this.funcParameters.get(paramName).isBuffer = true;
                    buffers.add(this.funcParameters.get(paramName));
                    System.out.println(paramName);
                }

                // cases when the parameter can be a size of a buffer
                else if (paramExplanation.startsWith("The size of the buffer")
                      || paramName.contains("Size")
                      || paramName.contains("size")
                      || paramName.contains("Length")
                      || paramName.contains("length")) {
                    System.out.println("inside size finder..." + paramName);
                    bufferSizes.put(this.funcParameters.get(paramName), paramExplanation);
                }



                // CASE 2 : NULL DEREFERENCE
                if (paramExplanation.contains("should not be NULL")) {
                    this.funcParameters.get(paramName).shouldDeref = true;
                }

                // CASE 3 : CLOSE HANDLE


                // CASE 4 : DELETE

            }

            // TODO: this if statement should be eliminated, it should be always true
            if (this.funcParameters.get(paramName) != null && inout != null) {
                this.funcParameters.get(paramName).inout = inout;
            }

        }
//
//        System.out.println("buffer list size = " + buffers.size());
//        System.out.println("buffer size list size = " + bufferSizes.size());

        // match buffer and buffer size
        if (buffers.size() == 1 && bufferSizes.size() == 1) {
            FuncParameter size = bufferSizes.keySet().iterator().next();
            buffers.get(0).bufferSize = size;
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!  buffer: " + buffers.get(0).name + "  size: " + size.name);
        }
        else if {

        }
    }

}
