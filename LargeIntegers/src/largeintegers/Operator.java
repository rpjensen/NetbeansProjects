
package largeintegers;

/**
 *
 * @author Ryan
 */

public enum Operator {
    OPEN_PAREN(),
    ADD(),
    SUBTRACT(ADD),
    MULTIPLY(),
    DIVIDE(MULTIPLY),
    REMAINDER_SHORT(MULTIPLY),
    NEGATE(MULTIPLY),
    MULT_PARENTHS(MULTIPLY),
    POWER(),
    MOD(),
    EXP_NOTATION(),
    NUMBER(),
    CLOSED_PAREN(NUMBER),
    REMAINDER(NUMBER),
    GCD(NUMBER);
       
    private int orderOfOps;
    
    private Operator(){
        this.orderOfOps = Counter.value;
        Counter.value++;
    }
    
    private Operator(Operator op){
        this.orderOfOps = op.orderOfOps;
    }
    
    public int getOOP(){
        return this.orderOfOps;
    }
    
    @Override
    public String toString(){
        String returnString = null;
        switch (this){
            case OPEN_PAREN:
                returnString = "Open Parenthesis";
                break;
            case ADD:
                returnString = "Add +";
                break;
            case SUBTRACT:
                returnString = "Subtract -";
                break;
            case MULTIPLY:
                returnString = "Multiply * or X or x";
                break;
            case DIVIDE:
                returnString = "Divide /";
                break;
            case REMAINDER_SHORT:
                returnString = "Short Remainder %";
                break;
            case NEGATE:
                returnString = "Negate -num";
                break;
            case MULT_PARENTHS:
                returnString = "Parenthesis Multiplication a(b)";
                break;
            case POWER:
                returnString = "Power ^";
                break;
            case REMAINDER:
                returnString = "Long Remainder rem(a,b)";
                break;
            case MOD:
                returnString = "Modulo a mod b";
                break;
            case EXP_NOTATION:
                returnString = "Exp Notation aE10";
                break;
            case NUMBER:
                returnString = "Number";
                break;
            case CLOSED_PAREN:
                returnString = "Closed Parenthesis";
                break;
            case GCD:
                returnString = "Greatest Common Factor gcd(a,b)";
                break;  
            default:
                returnString = "Unknown operator";
        }
        return returnString;
    }
    
    private static class Counter{
        private static int value = 1;
    }
}
