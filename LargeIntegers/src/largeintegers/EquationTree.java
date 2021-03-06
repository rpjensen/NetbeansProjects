
package largeintegers;

/**
 *
 * @author Ryan Jensen
 * @version May 31, 2014
 */
public class EquationTree {
    private EquationNode top;
    private EquationIterator iterator;
    
    public EquationTree(EquationNode top){
        this.top = top.copy();
        this.iterator = new EquationIterator(this.top);
    }
    
    public EquationTree(){
        this.top = null;
        this.iterator = null;
    }
    
    public EquationIterator getIterator(){
        return new EquationIterator(this.top);
    }
    
    public void addNodeAtCurrent(EquationNode newNode){
        newNode = newNode.copy();//get own copy without references
        //Short circuit to allow an empty equation tree to work seemlessly
        if (this.top == null){
            this.setTop(newNode);
            return;
        }
        if (newNode.getOperator() == Operator.OPEN_PAREN || (this.getCurrentOperator().getOOP() < newNode.getOrderOfOpsValue())){
            if (this.getCurrentOperator().isUnary()){
                this.iterator.setFirstChild(newNode);
                this.iterator.goToFirstChild();
            }
            else{
                this.iterator.setSecondChild(newNode);
                this.iterator.goToSecondChild();
            }
        }
        else if (this.iterator.hasParent()){
            if (this.iterator.parent().getOrderOfOpsValue() < newNode.getOrderOfOpsValue()){
                //Insert above
                EquationNode originalParent = this.iterator.parent(); 
                this.iterator.setParent(newNode);
                this.iterator.goToParent();
                this.iterator.setParent(originalParent);
            }
            else {
                //move up tree one then recursive call
                this.iterator.goToParent();
                this.addNodeAtCurrent(newNode);
            }
        }
        else{
            //add above
            this.iterator.setParent(newNode);
            this.top = newNode;
        }
    }
   
    public void closeParenthesis() throws UnbalancedParenthesisException{
        if (this.getCurrentOperator() == Operator.OPEN_PAREN){
            this.iterator.getCurrent().setOperator(Operator.CLOSED_PAREN);
        }else if (this.iterator.hasParent()){
            this.iterator.goToParent();
            this.closeParenthesis();
        }else {
            throw new UnbalancedParenthesisException("Unbalanced Parenthesis");
        }
    }
    
    public void closeAllParenthesis(){
        boolean finished = false;
        while (!finished){
            try {
                this.closeParenthesis();
            }
            catch (UnbalancedParenthesisException e){
                finished = true;
            }
        }
    }
    
    public LinkedLargeInteger solve(){
        return this.top.solve();
    }
    
    private Operator getCurrentOperator(){
        return this.iterator.getCurrent().getOperator();
    }
       
    private void setTop(EquationNode top){
        this.top = top.copy();
        this.iterator = new EquationIterator(this.top);
    }
}
