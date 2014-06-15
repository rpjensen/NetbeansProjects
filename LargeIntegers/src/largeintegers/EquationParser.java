

package largeintegers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Ryan Jensen
 * @version Jun 14, 2014
 */
public class EquationParser {
    private EquationTree equation;
    private StringBuilder currentNode;
    private String inputString;
    private Scanner inputScanner;
    
    private static final Map<Operator, String> regularExpressions;
    static {
        regularExpressions = new HashMap<>();
    }
    
    public EquationParser(String inputString){
        this.inputString = inputString;
        this.inputScanner = new Scanner(inputString);
        this.inputScanner.useDelimiter("");
        this.equation = new EquationTree();
        this.currentNode = new StringBuilder();
    }
    
    public void parseEquation() throws ParseException{
        while (this.inputScanner.hasNext()){
            this.processChar(this.inputScanner.next());
        }
    }
    
    public void processChar(String currentChar) throws ParseException{
        
    }
}
