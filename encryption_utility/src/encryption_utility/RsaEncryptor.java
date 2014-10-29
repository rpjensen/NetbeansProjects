/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption_utility;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * An object that can encrypt/decrypt, sign/unsign messages based on the RSA key
 * that it stores.
 * @author Ryan Jensen
 * @version Sep 29, 2014
 */
public class RsaEncryptor {
    private final RsaKey key;
    /** The number of bytes per original message chunk */
    private final int byteLength;
    /** The number of bytes per encrypted message chunk */
    private final int encryptByteLength;
    
    public static final Charset CHAR_SET;
    
    static {
        CHAR_SET = Charset.forName("UTF-16");
    }
    
    private static byte[] getHashValue(String text) throws NoSuchAlgorithmException { 
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        return md.digest(text.getBytes(CHAR_SET));
    }
    
    /**
     * Returns the hexadecimal 40 digit SHA-1 hash of the given string 
     * @param text the string to take the hash of
     * @return the hash of the given string
     * @throws NoSuchAlgorithmException if SHA-1 algorithm is not available on this JVM
     */
    public static String getHashString(String text) throws NoSuchAlgorithmException {
        return toHexString(getHashValue(text));
    }
    
    /**
     * Creates a hexadecimal string of an array of bytes
     * @param bytes the bytes to turn into hex
     * @return a hexadecimal string representation
     */
    public static String toHexString(byte[] bytes){
        return new BigInteger(bytes).toString(16);
    }
    
    public static String getStringFromBytes(byte[] bytes){
        return new String(bytes, CHAR_SET);
    }
    
    
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
    
    /**
     * Create a new RsaEncryptor with the given key
     * @param key the key the encryptor will use to encrypt messages
     * @return 
     */
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
        if (key.getPublicExponent() == null){ throw new IllegalStateException("Cannot encrypt without public key");}        
        return encryptBytesUsing(message.getBytes(CHAR_SET), key.getPublicExponent());
    }
    
    public String decryptMessage(String encrypted){
        return decryptMessage(encrypted.getBytes(CHAR_SET));
    }
    
    public String decryptMessage(byte[] encrypted){
        if (key.getPrivateExponent() == null){ throw new IllegalStateException("Cannot decrypt without private key");}
        return new String(decryptBytes(encrypted, key.getPrivateExponent()), CHAR_SET);
    }
    
    public String decryptWithContentLength(String encrypted, int length){
        return decryptWithContentLength(encrypted.getBytes(CHAR_SET), length);
    }
    
    public String decryptWithContentLength(byte[] encrypted, int length){
        return decryptMessage(encrypted).substring(0, length);
    }
    
    public byte[] signMessage(Message message) throws NoSuchAlgorithmException {
        return signMessage(message.toString());
    }
    
    public byte[] signMessage(String message) throws NoSuchAlgorithmException {
        if (key.getPrivateExponent() == null){ throw new IllegalStateException("Cannot sign without private key");}        
        return encryptBytesUsing(RsaEncryptor.getHashString(message).getBytes(CHAR_SET), key.getPrivateExponent());
    }
    
    public byte[] signFromHashString(String hash){
        if (key.getPrivateExponent() == null){ throw new IllegalStateException("Cannot sign without private key");}
        return encryptBytesUsing(hash.getBytes(CHAR_SET), key.getPrivateExponent());
    }
    
    public String decryptSignature(String signature){
        return decryptSignature(signature.getBytes(CHAR_SET)); 
    }
    
    public String decryptSignature(byte[] signature){
        if (key.getPublicExponent() == null){ throw new IllegalStateException("Cannot unsign without public key");}
        return new String(decryptBytes(signature, key.getPublicExponent()), CHAR_SET).substring(0, 40);         
    }
    
    private byte[] encryptBytesUsing(byte[] encoded, BigInteger exponent){
        int padding = (byteLength - (encoded.length % byteLength)) % byteLength;//how much we add to get the next multiple of byte length
        
        
        int groups = (encoded.length+padding) / byteLength;//this is the number of chunks in the message
        
        //unfortunetly our encrypted message chunk can be up to baseLength bits long since it will be up to n-1
        //Each bythLength chunk of the unencrypted message equals a encryptByteLength chunk
        byte[] encrypted = new byte[groups*this.encryptByteLength + 2];
        //byte order mark -> \uFEFF
        encrypted[0] = -2;//byte order mark 1 -> FE
        encrypted[1] = -1;//byte order mark 2 -> FF
        
        
        for (int i = 0; i < groups; i++){
            int offset = i * byteLength;
            byte[] temp = encryptBytes(Arrays.copyOfRange(encoded, offset, offset + byteLength), exponent);//automatically pads if we go off the end of the array
            for (int j = 0; j < temp.length; j++){
                encrypted[i * this.encryptByteLength + j + 2] = temp[j];
            }
        }
        
        return encrypted;
    }
    
    private byte[] encryptBytes(byte[] number, BigInteger exponent){
       // System.out.println("Enc Bytes: " + Arrays.toString(number));
        Debug deb = new Debug();
        debug.add(deb);
        deb.pre = number;
        //System.out.println("Length: " + number.length);
        BigInteger message = new BigInteger(1, number);//unsigned constructor so number is the magnitude and sign is positive
        BigInteger result = MathUtilities.modularExponent(message, exponent, key.getBase());
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
    
    private byte[] decryptBytes(byte[] encrypted, BigInteger exponent){
        int groups = encrypted.length / this.encryptByteLength;//get number of groups
        int length = groups*byteLength;
        if (length % 2 == 1){length++;}
        byte[] decrypted = new byte[length];//holds the final result
        
        for (int i = 0; i < groups; i++){
            int offset = i * encryptByteLength + 2;//skip two byte order mark
            byte[] preDecrypt = Arrays.copyOfRange(encrypted, offset, offset + encryptByteLength);
            Debug deb = debug.get(i);
            deb.encIn = preDecrypt;
            BigInteger number = new BigInteger(1, preDecrypt);
            BigInteger result = MathUtilities.modularExponent(number, exponent, key.getBase());
            deb.encryptIn = number;
            deb.messageOut = result;
            byte[] encoded = result.toByteArray();
            deb.raw = encoded;
            int decryptOffset = i * byteLength;//how far into decrypted we are starting
            int padding = byteLength - encoded.length;
            for (int j = 0; j < encoded.length; j++){
                if (j == 0 && padding == -1){
                    continue;
                }
                decrypted[decryptOffset+padding+j] = encoded[j];
            }
            
            byte[] values = Arrays.copyOfRange(decrypted, decryptOffset, decryptOffset+byteLength);
            deb.post = values;
        }
        return decrypted;
    }
            
    public static void main(String[] args) throws NoSuchAlgorithmException {
        RsaKey key = RsaKey.RsaKeyGen();
        System.out.println(key.toString());
        System.out.println();
        System.out.println();
        RsaEncryptor encr = RsaEncryptor.getEncryptorForKey(key);

        String test = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec condimentum non mauris vel eleifend. \nCras auctor metus sed nunc efficitur ultrices. Aliquam feugiat, justo sed ullamcorper \nconsectetur, erat lacus sollicitudin orci, a pharetra est \nodio non urna. Donec vehicula nulla sit amet quam rhoncus dignissim. \nIn nisi purus, porta eget fermentum sit amet, euismod vitae neque. \n\n Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec condimentum non mauris vel eleifend. \nCras auctor metus sed nunc efficitur ultrices. Aliquam feugiat, justo sed ullamcorper \nconsectetur, erat lacus sollicitudin orci, a pharetra est \nodio non urna. Donec vehicula nulla sit amet quam rhoncus dignissim. \nIn nisi purus, porta eget fermentum sit amet, euismod vitae neque. \n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Donec condimentum non mauris vel eleifend. \nCras auctor metus sed nunc efficitur ultrices. Aliquam feugiat, justo sed ullamcorper \nconsectetur, erat lacus sollicitudin orci, a pharetra est \nodio non urna. Donec vehicula nulla sit amet quam rhoncus dignissim. \nIn nisi purus, porta eget fermentum sit amet, euismod vitae neque.";
        System.out.println(test);
        System.out.println(test.length());
        
        boolean encodeDecodeTest = true;
        boolean signatureTesting = true;
        
        if (encodeDecodeTest){
            for (int i = 0; i < 1; i++){

                byte[] result = encr.encryptMessage(test);
                //System.out.println(Arrays.toString(result));
                //System.out.println("Encoded Length: " + result.length);
                String results = new String(result, CHAR_SET);
                System.out.println(results);
                //System.out.println("Length: " + results.length());
                System.out.println(encr.byteLength);
                System.out.println(encr.encryptByteLength);
                System.out.println();
                System.out.println();
                System.out.println();
                //String decrypt = encr.decryptMessage(result);
                String decrypt = encr.decryptWithContentLength(result, test.length());
                System.out.println("Byte Length: " + encr.byteLength);
                System.out.println("Enc Byte Length: " + encr.encryptByteLength);
                boolean detailDebug = true;
                if (detailDebug){
                    for (Debug deb : encr.debug){
                        System.out.println(encr.debug.indexOf(deb));
                        System.out.print(deb.toString());
                        if (!Arrays.equals(deb.pre, deb.post)){
                            try {
                                System.out.println();
                                System.out.println("****** Test Failed ******");
                                System.out.println();
                                throw new RuntimeException();
                            }
                            catch (RuntimeException e){

                            }


                        }
                    }
                }
                System.out.println();
                System.out.println();
                System.out.println(decrypt);
                System.out.println("Length: " + decrypt.length());        
            }
        }
        
        
        if (signatureTesting){
            String hash = RsaEncryptor.getHashString(test);
            System.out.println("Hash String: " + hash);
            System.out.println("Hash Bytes:      " + Arrays.toString(getHashValue(test)));
            byte[] signature = encr.signMessage(test);
            System.out.println("Sig String: " + new String(signature, CHAR_SET));
            System.out.println("Sig Bytes: " + Arrays.toString(signature));
            System.out.println("Sig Bytes: " + Arrays.toString(encr.signFromHashString(hash)));
            String hashOut = encr.decryptSignature(signature);
            System.out.println("Sig Check: " + hashOut);
            System.out.println("Sig Check Bytes: " + Arrays.toString(hashOut.getBytes(CHAR_SET)));
        }
        
    }
}
