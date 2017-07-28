package function;


import java.util.ArrayList;
import java.util.List;

public class FuncReturnValue {

    public final Type type;
    public List<String> errorList;

    public FuncReturnValue(Type type, List<String> errorList) {
        this.type = type;

        this.errorList = new ArrayList<String>();

        this.errorList.addAll(errorList);

    }


    public boolean hasErrorList() {
        if (this.errorList.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> getErrorList() {
        return this.errorList;
    }

    public Type getType() {
        return this.type;
    }
}
