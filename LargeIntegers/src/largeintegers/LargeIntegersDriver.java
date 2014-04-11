
package largeintegers;

/**
 *
 * @author Ryan Jensen
 * @version April 28, 2014
 */
public class LargeIntegersDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LinkedLargeInteger num = new LinkedLargeInteger(100000000);
        LinkedLargeInteger one = new LinkedLargeInteger(1);
        System.out.println(num);
        LinkedLargeInteger num1 = num.add(num);
        System.out.println(num1);
        System.out.println(num.subtract(one));
        System.out.println(num.subtract(num));
        System.out.println(num1.subtract(num));
        System.out.println(num.subtract(num1));
        System.out.println(num.add(one).add(new LinkedLargeInteger(-193748203)));
        System.out.println(num.subtract(one).negate().add(num));
    }
}
