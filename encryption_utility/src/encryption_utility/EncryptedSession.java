/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption_utility;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ryan Jensen
 * @version Sep 29, 2014
 */
public class EncryptedSession {
    private final Map<Host,KeyObject> creatorAuthentication;
    private final Host creator;
    private final Map<Connection,KeyObject> connectionAuthentication;
    private final Connection connection;
    private final String hostName;
    private final int hostIP;
    private AesEncryptor aesEncrypt;
    private RsaEncryptor rsaEncrypt;
    private InStatus inStatus;
    private OutStatus outStatus;
    private final Requested requestedByMe;
    private final ArrayList<Message> messages;
    
    public static class Builder {
        private final Host creator;
        private final KeyObject creatorKey;
        private Connection connection;
        private String hostName;
        private int hostIP;
        private final Requested requestedByMe;
        
        
        public static Builder initWithHostAndKey(Host host, KeyObject key, Requested requestedByMe){
            return new Builder(host, key, requestedByMe);
        }
        
        private Builder(Host host, KeyObject key, Requested requestedByMe){
            this.creator = host;
            this.creatorKey = key;
            this.requestedByMe = requestedByMe;
        }
        
        public Builder setConnection(Connection connection){
            this.connection = connection;
            return this;
        }
        
        public Builder setHostName(String hostname){
            this.hostName = hostname;
            return this;
        }
        
        public Builder setHostIP(int ip){
            this.hostIP = ip;
            return this;
        }
        
        public EncryptedSession build(){
            return createFromBuilder(this);
        }
    }
    
    private static EncryptedSession createFromBuilder(Builder build){
        EncryptedSession built = new EncryptedSession(build.creator, build.creatorKey, build.connection, build.hostName, build.hostIP, build.requestedByMe);
        KeyObject connectionKey = build.connection.acceptConnection(built);
        built.connectionAuthentication.put(built.connection, connectionKey);
        return built;
    }
    
    private EncryptedSession(Host creator, KeyObject creatorKey, Connection connection, String hostName, String hostIP, Requested requestedByMe){
        this.creatorAuthentication = new HashMap<>();
        this.creator = creator;
        this.creatorAuthentication.put(creator, creatorKey);
        this.connectionAuthentication = new HashMap<>();
        this.connection = connection;
        this.hostName = hostName;
        this.hostIP = hostIP;
        this.requestedByMe = requestedByMe;
        this.inStatus = InStatus.WAITING_ON_KEY;
        this.outStatus = OutStatus.KEY_NOT_SENT;
        this.messages = new ArrayList<>();
    }
    
    public Requested getRequested(){
        return this.requestedByMe;
    }
    
    public ArrayList<Message> getMessagesView(KeyObject auth){
        if (this.creatorAuthentication.get(creator) != auth){
            throw new IllegalArgumentException("Key doesn't match the host");
        }
        return messages;
    }
    
    public void handshake() throws ParseException{
        if (this.inStatus == InStatus.WAITING_ON_KEY && this.outStatus == OutStatus.KEY_NOT_SENT){
            acceptPublicKey(this.connection.requestPublicKey(this.connectionAuthentication.get(connection)));
            this.connection.sendPublicKey(this.returnPublicKey(), this.connectionAuthentication.get(connection));
            this.outStatus = OutStatus.KEY_SENT;
            this.aesEncrypt = AesEncryptor.getEncryptor();
            
            this.connection.sendSessionKey(rsaEncrypt.encryptMessage(this.returnSessionKey()), this.connectionAuthentication.get(connection));
            this.outStatus = OutStatus.HANDSHAKE_SENT;
            this.inStatus = InStatus.HANDSHAKE_RECIEVED;//No reason for this since we created the session key
        }
        
    }
    
    public void requestHandshakeStart(KeyObject auth) throws ParseException{
        if (this.connectionAuthentication.get(connection) != auth){
            throw new IllegalArgumentException("Request Failed: Unknown connection");
        }
        if (this.requestedByMe == Requested.YES){
            handshake();
        }
        else {
            this.connection.requestHandshake(this.connectionAuthentication.get(connection));
        }
    }
    
    public String returnPublicKey(KeyObject auth){
        if (this.connectionAuthentication.get(connection) != auth){
            throw new IllegalArgumentException("Request Failed: Unknown connection");
        }
         return this.returnPublicKey();
    }
    
    private String returnPublicKey(){
        RsaKey key = this.creator.getPublicKey();
        Message message = getMessageHeader().setRsaKey(key).build();
        this.outStatus = OutStatus.KEY_SENT;
        return message.toString();
    }
    
    public void acceptPublicKey(String receivedMessage, KeyObject auth) throws ParseException{
        if (this.connectionAuthentication.get(connection) != auth){
            throw new IllegalArgumentException("Request Failed: Unknown connection");
        }
        this.acceptPublicKey(receivedMessage);
    }
    
    private void acceptPublicKey(String receivedMessage) throws ParseException {
        if (this.inStatus != InStatus.WAITING_ON_KEY){
            throw new IllegalStateException("Key already received");
        }
        Message message = Message.fromString(receivedMessage);
        messages.add(message);
        RsaKey key = message.getRsaKey();
        if (key == null){ throw new NullPointerException("Message didn't contain a key");}
        this.rsaEncrypt = RsaEncryptor.getEncryptorForKey(key);
        this.inStatus = InStatus.WAITING_ON_HANDSHAKE;
        
    }
    
    private String returnSessionKey(){
        String key = this.aesEncrypt.getHexKey();
        Message message = getMessageHeader().setSessionKey(key).build();
        return message.toString();
    }
    
    public void acceptSessionKey(byte[] receivedMessage, KeyObject auth) throws ParseException {
        if (this.connectionAuthentication.get(connection) != auth){
            throw new IllegalArgumentException("Request Failed: Unknown connection");
        }
        this.acceptSessionKey(this.rsaEncrypt.decryptMessage(receivedMessage));
    }
    
    private void acceptSessionKey(String receivedMessage) throws ParseException {
        if (this.inStatus != InStatus.WAITING_ON_HANDSHAKE){
            throw new IllegalStateException("Key already received");
        }
        Message message = Message.fromString(receivedMessage);
        messages.add(message);
        String sessionKey = message.getSessionKey();
        if (sessionKey == null){ throw new NullPointerException("Message didn't contain a key");}
        this.aesEncrypt = AesEncryptor.getEncryptorFromHexKey(sessionKey);
        this.inStatus = InStatus.HANDSHAKE_RECIEVED;
        this.outStatus = OutStatus.HANDSHAKE_SENT;//no need for this since we didn't create the key
    }
    
    public void acceptEncryptedMessage(byte[] encryptedMessage, KeyObject auth) throws ParseException{
        if (this.connectionAuthentication.get(connection) != auth){
            throw new IllegalArgumentException("Request Failed: Unknown connection");
        }
        this.acceptEncryptedMessage(this.aesEncrypt.decryptMessage(encryptedMessage));
    }
    
    private void acceptEncryptedMessage(String decryptedMessage) throws ParseException {
        if (this.inStatus != InStatus.HANDSHAKE_RECIEVED && this.outStatus != OutStatus.HANDSHAKE_SENT){
            throw new IllegalStateException("Handshake not done so we can't encrypt symmetric messages");
        }
        Message message = Message.fromString(decryptedMessage);
        messages.add(message);  
    }
    
    private Message.Builder getMessageHeader(){
        Message.Builder builder = Message.Builder.createMessageFrom(creator);
        builder.setHostTo(hostName).setHostIp(hostIP);
        return builder;
    }
    
    public enum Requested {
        YES,
        NO;
    }
    
    public enum InStatus {
        WAITING_ON_KEY,
        WAITING_ON_HANDSHAKE,
        HANDSHAKE_RECIEVED;
    }
    
    public enum OutStatus {
        KEY_NOT_SENT,
        KEY_SENT,
        HANDSHAKE_SENT;
    }
    
    public enum Accept {
        HANDSHAKE_ACCEPTED,
        HANDSHAKE_REJECTED;
    }
    
    
}
