/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption_utility;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author Ryan Jensen
 * @version Sep 29, 2014
 */
public class RsaKey {
    private final BigInteger p;
    private final BigInteger q;
    private final BigInteger n;
    private final BigInteger phiN;
    private final BigInteger d;
    private final BigInteger e;
    private static final BigInteger eDefault = new BigInteger("65537");
    
    public static RsaKey RsaKeyGen(){
        return new RsaKey();
    }
    
    private RsaKey(){
        Random gen = new Random();
        BigInteger num = null;
        do {
            StringBuilder number = new StringBuilder(1);
            for (int i = 0; i < 510; i++){
                int val = gen.nextInt(2);
                number.append(val);
            }
            number.append(1);
            num = new BigInteger(number.toString(), 2);
        }while (!MathUtilities.millerRabinPrimality(num, 32));
        
        this.p = num;
        
        num = null;
        do {
            StringBuilder number = new StringBuilder(1);
            for (int i = 0; i < 510; i++){
                int val = gen.nextInt(2);
                number.append(val);
            }
            number.append(1);
            num = new BigInteger(number.toString(), 2);
        }while (!MathUtilities.millerRabinPrimality(num, 32));
        
        this.q = num;
        
        this.n = p.multiply(q);
        this.e = eDefault;
        this.phiN = n.subtract(p).subtract(q).add(BigInteger.ONE);
        this.d = MathUtilities.getInverseMod(e, phiN);
    }
    
    private RsaKey(RsaKey key){
        this.p = null;
        this.q = null;
        this.phiN = null;
        this.d = null;
        this.n = key.n;
        this.e = key.e;
    }
    
    
    public RsaKey getPublicKey(){
        return new RsaKey(this);
    }
    
    public BigInteger getPublicExponent(){
        return this.e;
    }
    
    public BigInteger getPrivateExponent(){
        return this.d;
    }
    
    public BigInteger getBase(){
        return this.n;
    }
}
