/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption_utility;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Ryan Jensen
 * @version Sep 29, 2014
 */
public class AesEncryptor {
    private final Key aesKey;
    private final Cipher c;
    /** The algorithm of encryption this object uses */
    public static final String ALGORITHM = "AES";
    /** The length of the key this object uses */
    public static final int KEY_BYTE_LENGTH = 32;
    /** The Charset this object uses for encoding/decoding of strings */
    public static final Charset CHAR_SET;
        
    static {
        CHAR_SET = Charset.forName("UTF-16");
    }
    
    public static String getStringForBytes(byte[] bytes){
        return new String(bytes, CHAR_SET);
    }
    
    public static Key generateKey(){
        BigInteger bi = new BigInteger(KEY_BYTE_LENGTH * 8, new Random());
        byte[] paddedKey = new byte[KEY_BYTE_LENGTH];
        byte[] nonPaddedKey = bi.toByteArray();
        int padding = paddedKey.length - nonPaddedKey.length;
        for (int i = 0; i < nonPaddedKey.length; i++){
            paddedKey[i+padding] = nonPaddedKey[i]; 
        }
        return new SecretKeySpec(paddedKey, ALGORITHM);
    }
    
    public static String getHexForKey(Key key){
        String secondPart = new BigInteger(key.getEncoded()).toString(16);
        StringBuilder firstPart = new StringBuilder();
        int padding = KEY_BYTE_LENGTH * 2 - secondPart.length();
        if (padding > 0){
            for (int i = 0; i < padding; i++){
                firstPart.append("0");
            }
            firstPart.append(secondPart);
            return firstPart.toString();
        }
        return secondPart;
    }
    
    public static AesEncryptor getEncryptorFromHexKey(String keyAsHex) throws NoSuchAlgorithmException, NoSuchPaddingException {
        if (keyAsHex == null){ throw new NullPointerException("Key should be non-null");}
        if (keyAsHex.length() != KEY_BYTE_LENGTH * 2){throw new IllegalArgumentException("Key should be 64 hex digits long");}
        BigInteger bi = new BigInteger(keyAsHex, 16);
        return new AesEncryptor(new SecretKeySpec(bi.toByteArray(), ALGORITHM));
    }
    
    public static AesEncryptor getEncryptorFromBigInteger(BigInteger key) throws NoSuchAlgorithmException, NoSuchPaddingException {
        if (key == null){ throw new NullPointerException("Key should be non-null");}
        return new AesEncryptor(new SecretKeySpec(key.toByteArray(), ALGORITHM));
    }
    
    public static AesEncryptor getEncryptorFromKey(Key key) throws NoSuchAlgorithmException, NoSuchPaddingException{
        if (key == null){ throw new NullPointerException("Key should be non-null");}
        return new AesEncryptor(key);
    }
    
    public static AesEncryptor getEncryptor() throws NoSuchAlgorithmException, NoSuchPaddingException{
        return new AesEncryptor(generateKey());
    }
    
    private AesEncryptor(Key aesKey) throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.aesKey = aesKey;
        this.c = Cipher.getInstance(ALGORITHM);
    }
    
    public byte[] encryptMessage(Message message) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        return encryptMessage(message.toString());
    }
    
    public byte[] encryptMessage(String message) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        c.init(Cipher.ENCRYPT_MODE, aesKey);
        return c.doFinal(message.getBytes(CHAR_SET));
    }
    
    public String decryptMessage(String encrypted) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        return decryptMessage(encrypted.getBytes(CHAR_SET));
    }
    
    public String decryptMessage(byte[] encrypted) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        c.init(Cipher.DECRYPT_MODE, aesKey);
        return new String(c.doFinal(encrypted),CHAR_SET);
    }
    
    public static void main(String[] args){
        
    }
}
