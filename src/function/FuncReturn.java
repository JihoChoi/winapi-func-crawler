package function;


import java.util.ArrayList;
import java.util.List;

public class FuncReturn {

    public final Type type;
    public List<String> errorList;

    public FuncReturn(Type type, List<String> errorList) {
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
