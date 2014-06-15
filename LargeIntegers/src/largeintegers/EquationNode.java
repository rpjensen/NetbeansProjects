

package largeintegers;

import java.util.ArrayList;

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
    
    public EquationNode(Operator operator, LinkedLargeInteger value){
        this.operator = operator;
        this.parent = null;
        this.value = value;
        this.firstChild = null;
        this.secondChild = null;
    }
    
    public LinkedLargeInteger solve(){
        LinkedLargeInteger firstChildVal = null;
        LinkedLargeInteger secondChildVal = null;
        if (this.firstChild != null){
            firstChildVal = this.firstChild.solve();
        }
        if (this.secondChild != null){
            secondChildVal = this.secondChild.solve();
        }
        LinkedLargeInteger returnValue = null;
        switch (this.operator){
            case OPEN_PAREN:
                throw new IllegalStateException("Solving Unclosed Parenthesis");
            case ADD:
                returnValue = firstChildVal.add(secondChildVal);
                break;
            case SUBTRACT:
                returnValue = firstChildVal.subtract(secondChildVal);
                break;
            case MULTIPLY:
                returnValue = firstChildVal.multiply(secondChildVal);
                break;
            case DIVIDE:
                returnValue = firstChildVal.dividedBy(secondChildVal);
                break;
            case REMAINDER_SHORT:
                returnValue = firstChildVal.remainder(secondChildVal);
                break;
            case NEGATE:
                returnValue = firstChildVal.negate();
                break;
            case POWER:
                returnValue = firstChildVal.pow(secondChildVal);
                break;
            case REMAINDER:
                returnValue = firstChildVal.remainder(secondChildVal);
                break;
            case MOD:
                returnValue = firstChildVal.modulo(secondChildVal);
                break;
            case EXP_NOTATION:
                returnValue = firstChildVal.exponent(secondChildVal);
                break;
            case NUMBER:
                returnValue = this.value;
                break;
            case CLOSED_PAREN:
                returnValue = firstChildVal;
                break;
            case GCD:
                returnValue = firstChildVal.gcd(secondChildVal);
                break;  
            default:
                throw new IllegalStateException("Unknown State");
        }
        return returnValue;
    }
    
    public EquationNode copy(){
        //note both operator and value are immutable objects
        return new EquationNode(this.operator, this.value);
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
    
    public int getOrderOfOpsValue(){
        return this.operator.getOOP();
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
    
    @Override
    public String toString(){
        StringBuilder returnString = new StringBuilder("(");
        returnString.append(this.operator.toString());
        if (this.operator == Operator.NUMBER){
            returnString.append(" ").append(this.value.toString());
        }
        if (this.firstChild != null || this.secondChild != null){
            returnString.append(" {");
            if (this.firstChild != null){
                returnString.append("FirstChild: ").append(this.firstChild.toString());             
            }
            if (this.secondChild != null) {
                returnString.append(" SecondChild: ").append(this.secondChild.toString());                
            }
            returnString.append("}");
        }
        returnString.append(")");
        return returnString.toString();
    }
    
    public static void main(String[] args){
        EquationNode num1 = new EquationNode(Operator.NUMBER, new LinkedLargeInteger(60));
        EquationNode num2 = new EquationNode(Operator.NUMBER, new LinkedLargeInteger(5));
        ArrayList<EquationNode> nodes = new ArrayList<>();
        
        for (Operator op : Operator.values()){
            if (op != Operator.OPEN_PAREN && op != Operator.NUMBER){
                EquationNode temp = new EquationNode(op, null, num1);
                if (!op.isUnary()){
                    temp.setSecondChild(num2);
                }
                nodes.add(temp);
            }
        }
        
        for (EquationNode node : nodes){
            System.out.println(node);
            System.out.println("Solution:" + node.solve());
            System.out.println("-----------------------------");
        }
    }
    
}
