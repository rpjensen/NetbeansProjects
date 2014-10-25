/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption_utility;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
    
    public static final Charset CHAR_SET = Charset.forName("UTF-16");
    private ArrayList<Debug> debug = new ArrayList<>();
    
    private class Debug {
        private byte[] pre;
        private byte[] encOut;
        private byte[] encIn;
        private byte[] post;
        private byte[] raw;
        private BigInteger messageIn;
        private BigInteger encryptOut;
        private BigInteger encryptIn;
        private BigInteger messageOut;
        
        @Override
        public String toString(){
            StringBuilder builder = new StringBuilder();
            builder.append("MesIn:  ").append(Arrays.toString(pre)).append("\n");
            builder.append("EncOut: ").append(Arrays.toString(encOut)).append("\n");
            //builder.append("EncOut Len: ").append(encOut.length).append("\n");
            builder.append("EncIn:  ").append(Arrays.toString(encIn)).append("\n");
            //builder.append("EncIn Len:  ").append(encIn.length).append("\n");
            builder.append("MesOut: ").append(Arrays.toString(post)).append("\n");                        
            builder.append("Raw:    ").append(Arrays.toString(raw)).append("\n");
            builder.append("Len:  ").append(raw.length).append("\n");
            builder.append("MesIn:  ").append(messageIn).append("\n");            
            builder.append("EncOut: ").append(encryptOut).append("\n");            
            builder.append("EncIn:  ").append(encryptIn).append("\n");            
            builder.append("MesOut: ").append(messageOut).append("\n");            
            return builder.toString();
        }
    }
    
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
    
    public byte[] encryptMessage(Message message){
        return encryptMessage(message.toString());
    }
    
    public byte[] encryptMessage(String message){
        byte[] encoded = message.getBytes(CHAR_SET);//get encoded bytes
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
       // System.out.println("Enc Bytes: " + Arrays.toString(number));
        Debug deb = new Debug();
        debug.add(deb);
        deb.pre = number;
        //System.out.println("Length: " + number.length);
        BigInteger message = new BigInteger(1, number);//unsigned constructor so number is the magnitude and sign is positive
        BigInteger result = MathUtilities.modularExponent(message, key.getPublicExponent(), key.getBase());
        deb.messageIn = message;
        deb.encryptOut = result;        
        byte[] encoded = result.toByteArray();
        deb.encOut = encoded;
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
        for (int i = encodedStart; i < encoded.length; i++){
            encrypted[padding+i - encodedStart] = encoded[i];
        }
        
        
        return encrypted;
    }
    
    public String decryptMessage(byte[] encrypted){
        if (key.getPrivateExponent() == null){throw new IllegalArgumentException("Need private key to decrypt message");}
        int groups = encrypted.length / this.encryptByteLength;//get number of groups
        byte[] decrypted = new byte[groups*this.byteLength];//holds the final result
        
        for (int i = 0; i < groups; i++){
            int offset = i * encryptByteLength + 2;//skip two byte order mark
            byte[] preDecrypt = Arrays.copyOfRange(encrypted, offset, offset + encryptByteLength);
            Debug deb = debug.get(i);
            deb.encIn = preDecrypt;
            BigInteger number = new BigInteger(1, preDecrypt);
            BigInteger result = MathUtilities.modularExponent(number, key.getPrivateExponent(), key.getBase());
            deb.encryptIn = number;
            deb.messageOut = result;
            byte[] encoded = result.toByteArray();
            deb.raw = encoded;
            //System.out.println("Before the zero: " + Arrays.toString(encoded));
            int decryptOffset = i * byteLength;//how far into decrypted we are starting
            int padding = byteLength - encoded.length;
            for (int j = 0; j < encoded.length; j++){
                if (j == 0 && padding == -1){
                    continue;
                }
                decrypted[decryptOffset+padding+j] = encoded[j];
            }
            
            
            /*int decryptSet = 0;//number of values we've set into decrypt this iteration
            if (encoded[0] != 0){
                //if the first real bit of result was a negative byte then there is
                //an extra 0 in encoded we should skip
                decrypted[decryptOffset] = encoded[0];
                decryptSet++;
            }
            for (int j = 1; j < encoded.length; j++){
                decrypted[decryptOffset + decryptSet] = encoded[j];
                decryptSet++;
            }*/
            byte[] values = Arrays.copyOfRange(decrypted, decryptOffset, decryptOffset+byteLength);
            deb.post = values;
            //System.out.println("After the zero: " + Arrays.toString(values));
            //System.out.println("Length: " + values.length);
        }
        return new String(decrypted, CHAR_SET);
    }
    
    public String decryptMessage(String encrypted){
        return decryptMessage(encrypted.getBytes(CHAR_SET));
    }
            
    public static void main(String[] args) {
        RsaKey key = RsaKey.RsaKeyGen();
        System.out.println(key.toString());
        System.out.println();
        System.out.println();
        RsaEncryptor encr = RsaEncryptor.getEncryptorForKey(key);
        for (int i = 0; i < 1; i++){
            String test = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec condimentum non mauris vel eleifend. \nCras auctor metus sed nunc efficitur ultrices. Aliquam feugiat, justo sed ullamcorper \nconsectetur, erat lacus sollicitudin orci, a pharetra est \nodio non urna. Donec vehicula nulla sit amet quam rhoncus dignissim. \nIn nisi purus, porta eget fermentum sit amet, euismod vitae neque. \n\n Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec condimentum non mauris vel eleifend. \nCras auctor metus sed nunc efficitur ultrices. Aliquam feugiat, justo sed ullamcorper \nconsectetur, erat lacus sollicitudin orci, a pharetra est \nodio non urna. Donec vehicula nulla sit amet quam rhoncus dignissim. \nIn nisi purus, porta eget fermentum sit amet, euismod vitae neque. \n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Donec condimentum non mauris vel eleifend. \nCras auctor metus sed nunc efficitur ultrices. Aliquam feugiat, justo sed ullamcorper \nconsectetur, erat lacus sollicitudin orci, a pharetra est \nodio non urna. Donec vehicula nulla sit amet quam rhoncus dignissim. \nIn nisi purus, porta eget fermentum sit amet, euismod vitae neque. ";
            System.out.println(test);
            System.out.println(test.length());
            byte[] result = encr.encryptMessage(test);
            //System.out.println(Arrays.toString(result));
            //System.out.println("Encoded Length: " + result.length);
            String results = new String(result, CHAR_SET);
            //System.out.println(results);
            //System.out.println("Length: " + results.length());
            System.out.println(encr.byteLength);
            System.out.println(encr.encryptByteLength);
            System.out.println();
            System.out.println();
            System.out.println();
            String decrypt = encr.decryptMessage(result);
            System.out.println("Byte Length: " + encr.byteLength);
            System.out.println("Enc Byte Length: " + encr.encryptByteLength);
            
            for (Debug deb : encr.debug){
                System.out.println(encr.debug.indexOf(deb));
                System.out.print(deb.toString());
                if (!Arrays.equals(deb.pre, deb.post)){
                    try {
                        /*System.out.println();
                        System.out.println();
                        System.out.println(new BigInteger(deb.pre).toString(2));
                        System.out.println(new BigInteger(deb.raw).toString(2));*/
                        System.out.println();
                        System.out.println("****** Test Failed ******");
                        System.out.println();
                        throw new RuntimeException();
                    }
                    catch (RuntimeException e){
                        
                    }
                    
                    
                }
            }
            System.out.println();
            System.out.println();
            System.out.println(decrypt);
            System.out.println("Length: " + decrypt.length());        }
        
        //System.out.println(Arrays.toString(decrypt.getBytes(CHAR_SET)));

    }
}
