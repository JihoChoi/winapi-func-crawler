import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import javax.json.*;


/**
 * Created by Jiho, Eric, Vanessa on 2017. 7. 28..
         */

// TODO Jiho


public class MSDNPage {


    public String URL;
    public String manualPageBuffer;

    public String title;
    public String description;
    public String syntax;
    public ArrayList<JsonObject> parameters;
    public String returnValue;
    public String remarks;
    public String examples;
    public String requirements;
    public String seeAlso;

    public MSDNPage(String URL) {
        this.URL = URL;
    }



    // parse the page buffer to fill all the fields
    public void refineManualPageBuffer() throws IOException, JSONException {

        // parse and refine mark up
        // this.manualPageBuffer -> this.title + this.description ...

        String[] lines = manualPageBuffer.split(System.getProperty("line.separator"));

        for (int i=0; i < lines.length; i++) {

            if (lines[i].contains("<h2>")) {
                if (lines[i].contains("Syntax")) {
                    String syn = new String();

                    while (!lines[i].contains("<pre>")) {
                        i++;
                    }
                    i++;
                    if (lines[i].isEmpty()) {
                        i++;
                    }

                    while (!lines[i].contains("</pre>")) {
                        syn += lines[i];
                        i++;
                    }

                    this.syntax = trim(syn);
                    System.out.println("Syntax  : " + syntax);

                }
                else if (lines[i].contains("Parameters")) {

                    String paramName = "";
                    String paramInOut = "";
                    String paramDescription = "";



                    // TODO retrieve from markup

                    JsonObject parameters = Json.createObjectBuilder()
                            .add("name", paramName)
                            .add("inout", paramInOut)
                            .add("description", paramDescription)
                            .build();
                    }

                    // reference http://www.java2s.com/Tutorials/Java/JSON/0100__JSON_Java.htm

                    ArrayList<JsonObject> parameters = new ArrayList<JsonObject>();

                    this.parameters = parameters;
                    System.out.println("Parameters  : " + this.parameters.toString());

                }


                else if (lines[i].contains("Return value")) {

                }
                else if (lines[i].contains("Requirements")) {

                }
                else if (lines[i].toLowerCase().contains("example")) {

                }
                else if (lines[i].contains("See also")) {

                }
                System.out.println(lines[i]);
            }

        }




    }

    public static String trim(String str) {

        // remove all unnecessary keywords

        // System.out.println("BEFORE TRIM " + this.syntax);


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


        // System.out.println("AFTER TRIM " + this.syntax);

        return str;


    }

}

