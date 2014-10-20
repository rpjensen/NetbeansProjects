/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption_utility;

import java.math.BigInteger;
import java.text.ParseException;
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
    
    public static class Builder {
        private BigInteger p;
        private BigInteger q;
        private BigInteger n;
        private BigInteger phiN;
        private BigInteger d;
        private BigInteger e;
        
        private Builder(){
            
        }
        
        public void setP(BigInteger p){
            this.p = p;
        }
        
        public void setQ(BigInteger q){
            this.q = q;
        }
        
        public void setN(BigInteger n){
            this.n = n;
        }
        
        public void setPhiN(BigInteger phiN){
            this.phiN = phiN;
        }
        
        public void setD(BigInteger d){
            this.d = d;
        }
        
        public void setE(BigInteger e){
            this.e = e;
        }
        
        public RsaKey build(){
            return new RsaKey(this);
        }
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
    
    private RsaKey(Builder builder){
        this.p = builder.p;
        this.q = builder.q;
        this.phiN = builder.phiN;
        this.d = builder.d;
        this.n = builder.n;
        this.e = builder.e;
        
        if (p != null && q != null && !p.multiply(q).equals(n)){
            throw new IllegalArgumentException("N is not the product of the two primes");
        }
        if (phiN != null && d != null && e != null && !d.multiply(e).mod(phiN).equals(BigInteger.ONE)){
            throw new IllegalArgumentException("e and d are not inverses mod phi(n)");
        }
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
    
    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        if (this.p != null && this.q != null){
            builder.append("Prime 1: ").append(this.p);
            builder.append("u\fffd").append("Prime 2: ").append(this.q);
            builder.append("u\fffd").append("Phi(n): ").append(this.phiN);
            builder.append("u\fffd").append("Private Exponent: ").append(this.d);
        }
        builder.append("u\fffd").append("Public Exponent: ").append(this.e);
        builder.append("u\fffd").append("Modular Base: ").append(this.n);
        return builder.toString();
    }
    
    public static RsaKey fromString(String rsaKey) throws ParseException {
        return fromString(rsaKey.split("\ufffd"), 0);
    }
    
    protected static RsaKey fromString(String[] splitStrings, int start) throws ParseException {
        String[] headers = {"Prime 1: ", "Prime 2: ", "Phi(n): ", "Private Exponent", "Public Exponent: ", "Modular Base: "};
        int counted = 0;
        Builder builder = new Builder();
        for (int i = start; i < splitStrings.length; i++){
            String current = splitStrings[i];
            int index = current.indexOf(headers[counted]);
            String value = current.substring(index+headers[counted].length());
            if (index == 0){
                counted++;
                i--;
                continue;
            }
            switch (headers[counted]){
                case "Prime 1: ":
                    builder.p = new BigInteger(value);
                    break;
                case "Prime 2: ":
                    builder.q = new BigInteger(value);
                    break;
                case "Phi(n): ":
                    builder.phiN = new BigInteger(value);
                    break;
                case "Private Exponent: ":
                    builder.d = new BigInteger(value);
                    break;
                case "Public Exponenet: ":
                    builder.e = new BigInteger(value);
                    break;
                case "Modular Base: ":
                    builder.n = new BigInteger(value);
                    break;
                default:
                    throw new ParseException("Failed to parse Rsa Key at line ", i);
            }
            counted++;
            if (counted >= headers.length){
                return builder.build();
            }
        }
        return builder.build();
    }
}
