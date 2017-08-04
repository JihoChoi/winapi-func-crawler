package function;


public class FuncParameter {

    public final Type type;
    public final String name;
    public boolean shouldDeref; // if the parameter should not be null
    public boolean isBuffer;
    public FuncParameter bufferSize;
    public String inout;


    public FuncParameter(Type type, String name) {
        this.type = type;
        this.name = name;
        this.shouldDeref = false;
        this.isBuffer = false;
        this.bufferSize = null;
    }

}
