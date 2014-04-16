
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
        System.out.println(num.multiply(num1));
        System.out.println(num1.dividedBy(num));
        System.out.println((new LinkedLargeInteger(-193748203)).multiply(6473876));
        System.out.println((new LinkedLargeInteger(-193748203)).multiply(6473876).dividedBy(6473876));
        System.out.println((new LinkedLargeInteger(Long.MAX_VALUE).multiply(Long.MAX_VALUE)));
        System.out.println((new LinkedLargeInteger(Long.MAX_VALUE).multiply(Long.MAX_VALUE)).dividedBy((new LinkedLargeInteger(Long.MAX_VALUE)).multiply(2)));
        System.out.println(new LinkedLargeInteger(Long.MAX_VALUE));
        System.out.println(new LinkedLargeInteger(Long.MIN_VALUE).multiply(Long.MAX_VALUE).dividedBy((new LinkedLargeInteger(Long.MIN_VALUE)).multiply(2)));
        System.out.println(new LinkedLargeInteger(Long.MIN_VALUE).multiply(Long.MIN_VALUE).dividedBy(new LinkedLargeInteger(Long.MIN_VALUE)));
        System.out.println(Long.MIN_VALUE);
        System.out.println((new LinkedLargeInteger(16)).pow(7));
        System.out.println(new LinkedLargeInteger(Long.MIN_VALUE));
        System.out.println((new LinkedLargeInteger(Long.MAX_VALUE).pow(3)));
        System.out.println((new LinkedLargeInteger(Long.MAX_VALUE)).multiply(Long.MAX_VALUE).multiply(Long.MAX_VALUE));
        System.out.println((new LinkedLargeInteger(Long.MIN_VALUE).pow(3)));
        System.out.println((new LinkedLargeInteger(Long.MAX_VALUE)).pow(20));
        System.out.println((new LinkedLargeInteger(Long.MAX_VALUE)).pow(40));
        
        System.out.println(new LinkedLargeInteger("3343473140982740981723407403921478"));
        System.out.println(new LinkedLargeInteger("-3343473140982740981723407403921478"));
        System.out.println(new LinkedLargeInteger("-0"));
        System.out.println(new LinkedLargeInteger("0"));
        System.out.println(new LinkedLargeInteger("00000000"));
        System.out.println(new LinkedLargeInteger("00000000").biggestDecimalPlace);
        System.out.println(new LinkedLargeInteger("-00000000100000000"));
        System.out.println(new LinkedLargeInteger("100000000").biggestDecimalPlace);
        
        System.out.println((new LinkedLargeInteger(1000000).toString()));
        System.out.println((new LinkedLargeInteger(-1000000).toString()));
        
    }
}
