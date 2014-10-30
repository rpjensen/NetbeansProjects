

package encryption_utility;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ryan Jensen
 * @version Oct 12, 2014
 */
public class Connection {
    private final Map<KeyObject,EncryptedSession> hosts;
    private final Map<EncryptedSession,KeyObject> keys;
    private final ArrayList<String> log;
    private ConnectionStatus status;
    private final Network network;
    

    
    public Connection(Network network){
        this.hosts = new HashMap<>();
        this.keys = new HashMap<>();
        this.log = new ArrayList<>();
        this.status = ConnectionStatus.CREATED;  
        this.network = network;
    }
    
    public KeyObject acceptConnection(EncryptedSession newHost){
        if (newHost == null){throw new NullPointerException("newHost should not be null");}
        if (hosts.size() > 2){ throw new IllegalStateException("Trying to join an established connection");}
        if (hosts.size() == 1){
            this.status = ConnectionStatus.INITIALIZED;
        }
        KeyObject key = new KeyObject();
        hosts.put(key, newHost);
        keys.put(newHost, key);
        return key;
    }
    
    private void checkStatus(){
        if (this.status == ConnectionStatus.CREATED){
            throw new IllegalStateException("Connection is not initialized");
        }
        if (this.status == ConnectionStatus.ENDED){
            throw new IllegalStateException("Connection is no longer initialized");
        }
    }
    
    private void checkHost(KeyObject authentication){
        if (!this.hosts.containsKey(authentication)){
            throw new IllegalArgumentException("Not a valid host for this connection");
        }
    }
    
    private EncryptedSession getOtherHostFromKey(KeyObject authentication){
        EncryptedSession requestedBy = this.hosts.get(authentication);
        Collection<EncryptedSession> hostView = this.hosts.values();
        for (EncryptedSession host : hostView){
            if (host != requestedBy){
                return host;
            }
        }
        return null;
    }
    
    public void requestHandshake(KeyObject authentication) throws ParseException{
        checkStatus();
        this.checkHost(authentication);
        EncryptedSession host = getOtherHostFromKey(authentication);
        host.handshake();
    }
    
    public String requestPublicKey(KeyObject authentication){
        checkStatus();
        this.checkHost(authentication);
        EncryptedSession host = getOtherHostFromKey(authentication);
        if (host == null){
            throw new IllegalStateException("The other host was not found");
        }
        
        String key = host.returnPublicKey(this.keys.get(host));
        this.log.add(key);
        return key;    
    }
    
    public void sendPublicKey(String publicKey, KeyObject auth) throws ParseException{
        checkStatus();
        this.checkHost(auth);
        EncryptedSession host = getOtherHostFromKey(auth);
        if (host == null){
            throw new IllegalStateException("The other host was not found");
        }
        
        host.acceptPublicKey(publicKey, this.keys.get(host));
        this.log.add(publicKey);
    }
    
    public void sendSessionKey(byte[] sessionKey, KeyObject auth) throws ParseException {
        checkStatus();
        this.checkHost(auth);
        EncryptedSession host = getOtherHostFromKey(auth);
        if (host == null){
            throw new IllegalStateException("The other host was not found");
        }
        
        host.acceptSessionKey(sessionKey, this.keys.get(host));
        this.log.add(new String(sessionKey, RsaEncryptor.CHAR_SET));
    }
    
    public void sendEncryptedMessage(byte[] encryptedMessage, KeyObject auth) throws ParseException {
        checkStatus();
        this.checkHost(auth);
        EncryptedSession host = getOtherHostFromKey(auth);
        if (host == null){
            throw new IllegalStateException("The other host was not found");
        }
        host.acceptEncryptedMessage(encryptedMessage, this.keys.get(host));
        this.log.add((new String(encryptedMessage, AesEncryptor.CHAR_SET)));
    }
    
    
    public enum ConnectionStatus {
        CREATED,
        INITIALIZED,
        ENDED;
    }
}
