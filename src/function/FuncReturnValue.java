package function;


import java.util.ArrayList;
import java.util.List;

public class FuncReturnValue {

    public final Type type;
    public String errorList;

    public FuncReturnValue(Type type, String errorList) {
        this.type = type;

        this.errorList = errorList;

    }

//
//    public boolean hasErrorList() {
//        if (this.errorList.size() == 0) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public List<String> getErrorList() {
//        return this.errorList;
//    }

    public Type getType() {
        return this.type;
    }
}
