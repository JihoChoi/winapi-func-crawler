package function;

/**
 * Created by Jiho on 2017. 7. 28..
 */
public class Type {

    public boolean isPointer;
    public Type type;
    public String name;

    public Type(boolean isPointer, Type type, String name) {
        this.isPointer = isPointer;
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
