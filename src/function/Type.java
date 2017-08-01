package function;

/**
 * Created by Jiho on 2017. 7. 28..
 */
public class Type {

    public boolean pointer;
    public Type pointerTo;

    public Type(boolean pointer, Type pointerTo) {
        this.pointer = pointer;
        this.pointerTo = pointerTo;
    }

}
