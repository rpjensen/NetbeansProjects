/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bitops;

/**
 *
 * @author jensenrp
 */
import java.util.Random;

public class BitOps {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Random gen = new Random();
       for(int i=0; i<10; ++i){
           int a = gen.nextInt();
           int b = gen.nextInt();
           System.out.printf("%08X %08X\n", a, b);
           System.out.printf("ADD(%08X, %08X) -> %08X\n", a, b, a+b);
           System.out.printf("AND(%08X,%08X) -> %08X\n", a, b, a&b);
           System.out.printf("OR(%08X,%08X) -> %08X\n", a, b, a|b);
           System.out.printf("NOT(%08X) -> %08X\n", a, ~a);
           System.out.println();
           int sb = b&31;
           System.out.printf("SHL(%08X, %2d)  -> %08X\n", a, sb, a>>sb);
           System.out.printf("SHR(%08X, %2d)  -> %08X\n", a, sb, a<<sb);
           System.out.println();
           System.out.printf("PKTLEN(%08X) -> %08X\n", a, a&0XFFFF);
           System.out.printf("HDRLEN(%08X) -> %08X\n", a, (a>>24)&0XF);
           System.out.println();
       }
    }
}
