/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption_utility;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;

/**
 *
 * @author Ryan Jensen
 * @version Sep 29, 2014
 */
public class RsaEncryptor {
    private final RsaKey key;
    /** The number of bytes per message chunk */
    private final int byteLength;
    private final int encryptByteLength;
    
    public static RsaEncryptor getEncryptorForKey(RsaKey key){
        return new RsaEncryptor(key);
    }
    
    private RsaEncryptor(RsaKey key){
        this.key = key;
        int length = this.key.getBase().bitLength();
        int padding = (8 - (length%8)) % 8;//to bring encrypt length to an even bit length
        encryptByteLength = (length + padding)/8;//the length of our encrypted chunks in bytes
        length--;//
        length = length - (length % 8);//make sure that length is the smallest multiple of 8 less than base
        this.byteLength = length / 8;//the number of bytes that each unencrypted chunk holds
        
    }
    
    public byte[] encryptMessage(Message message) throws UnsupportedEncodingException {
        byte[] encoded = message.toString().getBytes("UTF-16");//get encoded bytes
        int padding = (byteLength - (encoded.length % byteLength)) % byteLength;//how much we add to get the next multiple of byte length
        /*if (padding != 0){
            encoded = Arrays.copyOf(encoded, encoded.length + padding);//pad the back with zeros if necessary
        }*/
        
        
        int groups = (encoded.length+padding) / byteLength;//this is the number of chunks in the message
        
        //unfortunetly our encrypted message chunk can be up to baseLength bits long since it will be up to n-1
        //Each bythLength chunk of the unencrypted message equals a encryptByteLength chunk
        byte[] encrypted = new byte[groups*this.encryptByteLength];
        for (int i = 0; i < encrypted.length; i++){
            encrypted[i] = 0;
        }
        
        for (int i = 0; i < groups; i++){
            int offset = i * byteLength;
            byte[] temp = encryptBytes(Arrays.copyOfRange(encoded, offset, offset + byteLength));
            for (int j = 0; j < temp.length; j++){
                encrypted[i * this.encryptByteLength + j] = temp[j];
            }
        }
        
        return encrypted;
    }
    
    private byte[] encryptBytes(byte[] number){
        BigInteger message = new BigInteger(1, number);//unsigned constructor so number is the magnitude and sign is positive
        BigInteger result = MathUtilities.modularExponent(message, key.getPublicExponent(), key.getBase());
        byte[] encoded = result.toByteArray();
        byte[] encrypted = new byte[encryptByteLength];
        if (encoded.length > encryptByteLength){
            encoded = Arrays.copyOfRange(encoded, encoded.length - encryptByteLength, encryptByteLength);
        }
        
        return encoded;
        
    }
            
    
}
