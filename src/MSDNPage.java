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
    public String parameters;
    public String returnValue;
    public String remarks;
    public String examples;
    public String requirements;
    public String seeAlso;

    public MSDNPage(String URL) {
        this.URL = URL;
    }

    // TODO
    public MSDNPage(String URL, String title) {
        this.URL = URL;
        this.title = title;
    }


    public void refineManualPageBuffer() {

        // parse and refine
        // this.manualPageBuffer -> this.title + this.description ...



    }



}

