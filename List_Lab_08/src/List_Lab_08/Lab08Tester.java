package List_Lab_08;

import java.util.NoSuchElementException;
/**
 * This class tests the <code>LinkedLab08</code> class. 
 *
 * @author Dr. Mark A. Holliday
 * @author Dr. Charles M. Sheaffer
 * @version  3/23/2014
 */

public class Lab08Tester {
    /** The number of tests that have passed. */
    private int passes;

    /** The number of tests that have failed. */
    private int failures;

    /**
     * A method that initializes the two counters (passes and failures)
     * and calls helper methods to run the tests.
     */
    public void runTests() {
        passes   = 0;
        failures = 0;

        System.out.println("Testing of class LinkedLab08");
     
        testMoveFirstToEnd();
        testInsert();
        testRemove();

        System.out.println("Tests executed:    " + (passes + failures));
        System.out.println("     Successful:   " + passes);
        System.out.println("     Unsuccessful: " + failures);
    }

    /**
     * A helper method that contains the common aspects of conducting a test.
     * The first argument involves calling the method being tested and comparing 
     * its return value with the expected value. This is a boolean expression
     * that evaluates to true only if the method being tested returned the
     * expected value. If that boolean value is true, then counter of passed
     * tests is incremented; otherwise the counter of failed tests is incremented.
     * A string is constructed and displayed indicating whether the test passed
     * or failed and the expected and actual return values from the method being
     * tested. 
     *
     * @note The type of the third parameter is Object to handle the case when
     * the third argument is a method call that returns a null value. 
     *
     * @param condition The result of the boolean expression which compares the
     *      return value of the tested method with its expected value. Thus, if the 
     *      result is true, the test is passed and otherwise the test fails.
     * @param expected A string describing the expected return value of the tested
     *      method.
     * @param actual A string describing the actual return value of the tested method.
     *      The corresponding argument is a call to the method being tested.
     */
    private void test(boolean condition, String  expected, Object actual) {
        String msg = "EXPECTED: " + expected + "\tACTUAL: " + (String) actual;
        if (condition) {
            msg = msg + "\tPASSED";
            passes++;
        } else {
            msg = msg + "\tFAILED";
            failures++;
        }
        System.out.println(msg);
    }

    /**
     * Test the <code>addFront</code> and <code>addRear</code> methods.
     */
    private void testMoveFirstToEnd()
    {
        boolean exceptionThrown;
        System.out.println("\n Test moveFirstToEnd.\n");

		LinkedLab08<String> one = new LinkedLab08<String>();

        // moveFirstToEnd should not be called if the list is empty
        exceptionThrown = false;
        try {
            one.moveFirstToEnd();
        } catch (NoSuchElementException e) {
            exceptionThrown = true;
        }
        test(exceptionThrown, 
            "Should not call moveFirstToEnd on an empty list.", "no return value ");

		one.addFirst("red");
		test(one.toString().equals("<red>"), 
            "one.toString() is \"<red>\"", one.toString());
        one.moveFirstToEnd();
		test(one.toString().equals("<red>"), 
            "one.toString() is \"<red>\"", one.toString());

		one.addFirst("white");
		test(one.toString().equals("<white, red>"), 
            "one.toString() is \"<white, red>\"", one.toString());
        one.moveFirstToEnd();
		test(one.toString().equals("<red, white>"), 
            "one.toString() is \"<red, white>\"", one.toString());

		one.addFirst("blue");
		test(one.toString().equals("<blue, red, white>"), 
            "one.toString() is \"<blue, red, white>\"", one.toString());
        one.moveFirstToEnd();
		test(one.toString().equals("<red, white, blue>"), 
            "one.toString() is \"<red, white, blue>\"", one.toString());
		test(one.toStringSize().equals("<red, white, blue>"), 
            "one.toStringSize() is \"<red, white, blue>\"", one.toStringSize());
	}

    /**
     * Test the cases of the <code>insert</code> method that would cause an exception
     * to be thrown.
     */
    private void testInsert()
    {
        System.out.println("\n Test insert.\n");

        boolean exceptionThrown;

		LinkedLab08<String> one = new LinkedLab08<String>();

        // insert should not be called if the list is empty
        exceptionThrown = false;
        try {
            one.insert("green", 1);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        test(exceptionThrown, 
            "Should not call insert on an empty list.", "no return value ");


        // insert should not be called if the argument that is supposed
        // to be the new element is null
        exceptionThrown = false;
        try {
            one.insert(null, 1);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        test(exceptionThrown, 
            "Should not call insert if the argument that is supposed"
                + " to be the new element is null.", "no return value ");
		
		one.add("red");
        test(one.toString().equals("<red>"), 
            "one.toString() is \"<red>\"", one.toString());
        one.insert("blue", 0);
		test(one.toString().equals("<blue, red>"), 
            "one.toString() is \"<blue, red>\"", one.toString());

        one.insert("green", 1);
		test(one.toString().equals("<blue, green, red>"), 
            "one.toString() is \"<blue, green, red>\"", one.toString());
		test(one.toStringSize().equals("<blue, green, red>"), 
            "one.toStringSize() is \"<blue, green, red>\"", one.toStringSize());
	}

    /**
     * Test the <code>remove</code> method.
     */
    private void testRemove()
    {
        System.out.println("\n Test remove.\n");

        boolean exceptionThrown;

		LinkedLab08<String> one = new LinkedLab08<String>();

        // remove should not be called if the list is empty
        exceptionThrown = false;
        try {
            one.remove(2);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        test(exceptionThrown, 
            "Should not call remove on an empty list.", "no return value ");

        one.addFirst("red");

        one.remove(0);
		test(one.toString().equals("<>"), 
            "one.toString() is \"<>\"", one.toString());

        one.addFirst("red");
        one.addFirst("blue");
        one.addFirst("green");
		test(one.toString().equals("<green, blue, red>"), 
            "one.toString() is \"<green, blue, red>\"", one.toString());

        one.remove(1);
		test(one.toString().equals("<green, red>"), 
            "one.toString() is \"<green, red>\"", one.toString());
        one.remove(1);
		test(one.toString().equals("<green>"), 
            "one.toString() is \"<green>\"", one.toString());
        one.remove(0);
		test(one.toString().equals("<>"), 
            "one.toString() is \"<>\"", one.toString());
		test(one.toStringSize().equals("<>"), 
            "one.toStringSize() is \"<>\"", one.toStringSize());
	}
	

    /**
     * Provides the entry point for this application.  This method creates
     * a <code>Lab6IntNodeTester</code> and executes the
     * runTests() method.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        Lab08Tester tester = new Lab08Tester();
        tester.runTests();
    }
}
