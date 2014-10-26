/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package encoding_testing;

import java.math.BigInteger;
import java.util.Arrays;

/**
 *
 * @author Ryan
 */
public class EncodingTesting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        /*
        BigInteger message = new BigInteger("27433675887985013768714999662564189926295838798223344852850180442944679676760125631323941399909626129690458179943707756320588368575167150908498840963743467432276503314371986230463531270781371937244089998568333012078980470765829658802564383798062186761242555593751197703581511642721549348293256415707201649");
        BigInteger exponent = new BigInteger("65537");
        BigInteger base = new BigInteger("3471296608886407191595236584996886146899308107329193737598303090330145242813139198208884665741942915190109886764236375990560592135181158203479684963114911357465087967812507789329655343857100817321259521014535010061245097791571813384381316790256700703634800250584129016451164602015529610785046023906707300677");
        BigInteger privateKey = new BigInteger("1794785931490851086976892315525878314351939442099115458420716545094175375020571917104964476534869397153010871919772794000948253116569472904235914444895990710096892242841794935993403519850925503818210790059888563029657003771254452879690757545233662906533135180753918364181382084978994863635736032554617565153");
        
        BigInteger result = MathUtilities.modularExponent(MathUtilities.modularExponent(message, exponent, base), privateKey, base);
        System.out.println(result);
        if (!result.equals(message)){
            System.out.println("***** test failed *******");
        }*/
        
        byte[] bytes = {-1, -1, 5,4};
        String string = "hello";
        System.out.println("1: " + string);
        bytes = string.getBytes("UTF-16");
        bytes = Arrays.copyOfRange(bytes, 2, bytes.length);
        String newVal = new String(bytes, "UTF-16");
        System.out.println(newVal);
        byte[] bytes2 = {-128, 0};
        System.out.println("2: " + Arrays.toString(bytes));//print the string bytes
        BigInteger bi = new BigInteger(1, bytes);//bytes to bigInteger
        bi = bi.multiply(BigInteger.TEN).divide(BigInteger.TEN);
        bytes2 = bi.toByteArray();
        System.out.println("3: " + Arrays.toString(bytes2));
        if (bytes2[0] == 0){
            bytes2 = Arrays.copyOfRange(bytes2, 1, bytes2.length);
        }
        System.out.println("4: " + bi);
        System.out.println("5: " + Arrays.toString(bytes2));
        BigInteger b2 = new BigInteger(1,bytes2);
        System.out.println("6: " + b2);
        
        
        try {
            String value = new String (bytes2, "UTF-16");
            System.out.println("7: " + value);
            System.out.println("8: " + Arrays.toString(value.getBytes("UTF-16")));
            
        }
        catch (Exception e){
            
        }

        

    }
}
