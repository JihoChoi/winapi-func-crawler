package function;

/**
 * Created by Jiho on 2017. 7. 28..
 */
public class Type {

    public boolean isPointer;
    public Type type;

    public Type(boolean isPointer, Type type) {
        this.isPointer = isPointer;
        this.type = type;
    }

}
