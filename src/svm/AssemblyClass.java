package svm ;

public class AssemblyClass {
    private int code;
    private String arg1;
    private String arg2;
    private String arg3;

    public AssemblyClass(int _code, String _arg1, String _arg2, String _arg3) {
        code = _code;
        arg1 = _arg1;
        arg2 = _arg2;
        arg3 = _arg3;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int _code) {
        code = _code;
    }
  /*  
    AssemblyClass assemblyClass(String o) {
        this.operation = o;
        return this;
    }

    AssemblyClass assemblyClass(String o, String a1) {
        this.operation = o;
        this.arg1 = a1;
        return this;
    }

    AssemblyClass assemblyClass(String o, String a1, String a2) {
        this.operation = o;
        this.arg1 = a1;
        this.arg2 = a2;
        return this;
    }

    AssemblyClass AssemblyClass(String o, String a1, String a2, String a3) {
        this.operation = o;
        this.arg1 = a1;
        this.arg2 = a2;
        this.arg3 = a3;
        return this;
    }
*/

//   public String getOperation() {
//      return operation;
//   }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String _arg1) {
        arg1 = _arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public String getArg3() {
        return arg3;
    }

    public void setArg3(String _arg3) {
        arg3 = _arg3;
    }
}