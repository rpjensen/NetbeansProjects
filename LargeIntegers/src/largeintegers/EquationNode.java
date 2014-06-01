

package largeintegers;

/**
 *
 * @author Ryan Jensen
 * @version May 31, 2014
 */
public class EquationNode {
    private EquationNode parent;
    private EquationNode firstChild;
    private EquationNode secondChild;
    private Operator operator;
    private LinkedLargeInteger value;
    
    public EquationNode(Operator operator, EquationNode parent, EquationNode firstChild){
        this.operator = operator;
        this.parent = parent;
        this.firstChild = firstChild;
        this.secondChild = null;
        this.value = null;
               
    }
    
    public EquationNode(Operator operator, EquationNode parent){
        this(operator, parent, (EquationNode)null);
    }
    
    public EquationNode(Operator operator, EquationNode parent, LinkedLargeInteger value){
        this.operator = operator;
        this.parent = parent;
        this.value = value;
        this.firstChild = null;
        this.secondChild = null;
    }
    
    public LinkedLargeInteger solve(){
        LinkedLargeInteger firstChild = null;
        LinkedLargeInteger secondChild = null;
        if (this.firstChild != null){
            firstChild = this.firstChild.solve();
        }
        if (this.secondChild != null){
            secondChild = this.secondChild.solve();
        }
        LinkedLargeInteger returnValue = null;
        switch (this.operator){
            case OPEN_PAREN:
                throw new IllegalStateException("Solving Unclosed Parenthesis");
            case ADD:
                returnValue = firstChild.add(secondChild);
                break;
            case SUBTRACT:
                returnValue = firstChild.subtract(secondChild);
                break;
            case MULTIPLY:
                returnValue = firstChild.multiply(secondChild);
                break;
            case DIVIDE:
                returnValue = firstChild.dividedBy(secondChild);
                break;
            case REMAINDER_SHORT:
                returnValue = firstChild.remainder(secondChild);
                break;
            case NEGATE:
                returnValue = firstChild.negate();
                break;
            case MULT_PARENTHS:
                returnValue = firstChild.multiply(secondChild);
                break;
            case POWER:
                returnValue = firstChild.pow(secondChild);
                break;
            case REMAINDER:
                returnValue = firstChild.remainder(secondChild);
                break;
            case MOD:
                returnValue = firstChild.modulo(secondChild);
                break;
            case EXP_NOTATION:
                returnValue = firstChild.exponent(secondChild);
                break;
            case NUMBER:
                returnValue = this.value;
                break;
            case CLOSED_PAREN:
                returnValue = firstChild;
                break;
            case GCD:
                returnValue = firstChild.gcd(secondChild);
                break;  
            default:
                throw new IllegalStateException("Unknown State");
        }
        return returnValue;
    }
    
    public void setOperator(Operator operator){
        this.operator = operator;
    }
    public void setParent(EquationNode parent){
        this.parent = parent;
    }
    
    public void setFirstChild(EquationNode firstChild){
        this.firstChild = firstChild;
    }
    
    public void setSecondChild(EquationNode secondChild){
        this.secondChild = secondChild;
    }
    
    public void setValue(LinkedLargeInteger value){
        this.value = value;
    }
    
    public Operator getOperator(){
        return this.operator;
    }
    public EquationNode getParent(){
        return this.parent;
    }
    
    public EquationNode getFirstChild(){
        return this.firstChild;
    }
    
    public EquationNode getSecondChild(){
        return this.secondChild;
    }
    
    public LinkedLargeInteger getValue(){
        return this.value;
    }
    
}
