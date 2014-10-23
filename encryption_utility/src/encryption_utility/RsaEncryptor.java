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
    /** The number of bytes per original message chunk */
    private final int byteLength;
    /** The number of bytes per encrypted message chunk */
    private final int encryptByteLength;
    
    public static RsaEncryptor getEncryptorForKey(RsaKey key){
        return new RsaEncryptor(key);
    }
    
    private RsaEncryptor(RsaKey key){
        this.key = key;
        int length = this.key.getBase().bitLength();
        int padding = (8 - (length%8)) % 8;//to bring encrypt length to an even bit length
        encryptByteLength = (length + padding)/8;//the length of our encrypted chunks in bytes
        length--;//our message chunks need to be smaller than base.length
        //we will break the message into the next smallest multiple of 8 bits
        this.byteLength = length / 8;//the number of bytes that each unencrypted chunk holds
        
    }
    
    public byte[] encryptMessage(Message message) throws UnsupportedEncodingException {
        return encryptMessage(message.toString());
    }
    
    public byte[] encryptMessage(String message) throws UnsupportedEncodingException {
        byte[] encoded = message.getBytes("UTF-16");//get encoded bytes
        int padding = (byteLength - (encoded.length % byteLength)) % byteLength;//how much we add to get the next multiple of byte length
        /*if (padding != 0){
            encoded = Arrays.copyOf(encoded, encoded.length + padding);//pad the back with zeros if necessary
        }*/
        
        
        int groups = (encoded.length+padding) / byteLength;//this is the number of chunks in the message
        
        //unfortunetly our encrypted message chunk can be up to baseLength bits long since it will be up to n-1
        //Each bythLength chunk of the unencrypted message equals a encryptByteLength chunk
        byte[] encrypted = new byte[groups*this.encryptByteLength + 2];
        //byte order mark -> \uFEFF
        encrypted[0] = -2;//byte order mark 1 -> FE
        encrypted[1] = -1;//byte order mark 2 -> FF
        
        
        for (int i = 0; i < groups; i++){
            int offset = i * byteLength;
            byte[] temp = encryptBytes(Arrays.copyOfRange(encoded, offset, offset + byteLength));//automatically pads if we go off the end of the array
            for (int j = 0; j < temp.length; j++){
                encrypted[i * this.encryptByteLength + j + 2] = temp[j];
            }
        }
        
        return encrypted;
    }
    
    private byte[] encryptBytes(byte[] number){
        BigInteger message = new BigInteger(1, number);//unsigned constructor so number is the magnitude and sign is positive
        BigInteger result = MathUtilities.modularExponent(message, key.getPublicExponent(), key.getBase());
        byte[] encoded = result.toByteArray();
        byte[] encrypted = new byte[encryptByteLength];
        int padding = encrypted.length - encoded.length;
        int encodedStart = 0;
        if (encoded[0] == 0){
            padding++;
            encodedStart++;
        }
        else {
            encrypted[padding] = encoded[encodedStart];
        }
        for (int i = 1; i < encoded.length; i++){
            encrypted[padding+i] = encoded[i];
        }
        
        
        return encoded;
        
    }
            
    public static void main(String[] args){
        
    }
}
