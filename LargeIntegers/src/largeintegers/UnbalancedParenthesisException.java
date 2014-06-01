/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package largeintegers;

/**
 *
 * @author Ryan
 */
public class UnbalancedParenthesisException extends Exception {

    /**
     * Creates a new instance of
     * <code>UnbalancedParenthesisException</code> without detail message.
     */
    public UnbalancedParenthesisException() {
    }

    /**
     * Constructs an instance of
     * <code>UnbalancedParenthesisException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public UnbalancedParenthesisException(String msg) {
        super(msg);
    }
}
