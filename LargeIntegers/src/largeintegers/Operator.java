
package largeintegers;

/**
 *
 * @author Ryan
 */

public enum Operator {

    OPEN_PAREN(Constants.UNARY),
    ADD(),
    SUBTRACT(ADD),
    MULTIPLY(),
    DIVIDE(MULTIPLY),
    REMAINDER_SHORT(MULTIPLY),
    NEGATE(Constants.UNARY, MULTIPLY),
    POWER(),
    MOD(),
    EXP_NOTATION(),
    CLOSED_PAREN(Constants.UNARY),
    REMAINDER(CLOSED_PAREN),
    GCD(CLOSED_PAREN),
    NUMBER();
       
    private final int orderOfOps;
    private final boolean isUnary;
    
    private Operator(){
        this.orderOfOps = Constants.value;
        Constants.value++;
        this.isUnary = false;
    }
    private Operator(boolean isUnary){
        this.orderOfOps = Constants.value;
        Constants.value++;
        this.isUnary = isUnary;
    }
    
    private Operator(Operator op){
        this.orderOfOps = op.orderOfOps;
        this.isUnary = false;
    }
    
    private Operator(boolean isUnary,Operator op){
        this.orderOfOps = op.orderOfOps;
        this.isUnary = isUnary;
    }
    
    public int getOOP(){
        return this.orderOfOps;
    }
    
    public boolean isUnary(){
        return this.isUnary;
    }
    
    @Override
    public String toString(){
        String returnString = null;
        switch (this){
            case OPEN_PAREN:
                returnString = "Open Parenthesis:";
                break;
            case ADD:
                returnString = "Add:";
                break;
            case SUBTRACT:
                returnString = "Subtract:";
                break;
            case MULTIPLY:
                returnString = "Multiply:";
                break;
            case DIVIDE:
                returnString = "Divide:";
                break;
            case REMAINDER_SHORT:
                returnString = "Short Remainder:";
                break;
            case NEGATE:
                returnString = "Negate:";
                break;
            case POWER:
                returnString = "Power:";
                break;
            case REMAINDER:
                returnString = "Long Remainder:";
                break;
            case MOD:
                returnString = "Modulo:";
                break;
            case EXP_NOTATION:
                returnString = "Exp Notation:";
                break;
            case NUMBER:
                returnString = "Number:";
                break;
            case CLOSED_PAREN:
                returnString = "Closed Parenthesis:";
                break;
            case GCD:
                returnString = "GCD";
                break;  
            default:
                returnString = "Unknown operator";
        }
        return returnString;
    }
    
    private static class Constants{
        private static int value = 1;
        private static final boolean UNARY = true;
    }
    
    public static void main(String[] args){
        for (Operator op : Operator.values()){
            System.out.println(op);
            System.out.println(op.isUnary + " " + op.orderOfOps);
            System.out.println();
        }
    }
}
