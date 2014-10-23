/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption_utility;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ryan Jensen
 * @version Sep 29, 2014
 */
public class EncryptedSession {
    private final Map<Key,Host> creator;
    private final Map<Key,Connection> connection;
    private RsaEncryptor hostRsaSign;    
    private final String hostName;
    private final String hostIP;
    private AesEncryptor aesEncrypt;
    private RsaEncryptor rsaEncrypt;
    private Status status;
    
    public static class Builder {
        private final Host creator;
        private final Key creatorKey;
        private Connection connection;
        private String hostName;
        private String hostIP;
        private RsaKey rsaKey;
        
        
        public static Builder initWithHostAndKey(Host host, Key key){
            return new Builder(host, key);
        }
        
        private Builder(Host host, Key key){
            this.creator = host;
            this.creatorKey = key;
        }
        
        public void setConnection(Connection connection){
            this.connection = connection;
        }
        
        public void setHostName(String hostname){
            this.hostName = hostname;
        }
        
        public void setHostIP(String ip){
            this.hostIP = ip;
        }
        
        public void setRsaKey(RsaKey key){
            this.rsaKey = key;
        }
        
        public EncryptedSession build(){
            return new EncryptedSession(creator, creatorKey, connection, hostName, hostIP, rsaKey);
        }
    }
    
    private EncryptedSession(Host creator, Key creatorKey, Connection connection, String hostName, String hostIP, RsaKey rsaKey){
        this.creator = new HashMap<>();
        this.creator.put(creatorKey, creator);
        this.connection = new HashMap<>();
        Key connectionKey = connection.acceptConnection(this);
        this.connection.put(connectionKey, connection);
        this.hostName = hostName;
        this.hostIP = hostIP;
        this.hostRsaSign = RsaEncryptor.getEncryptorForKey(rsaKey);
    }
    
    
    
    public enum Status {
        WAITING_ON_KEY,
        KEY_SENT,
        HANDSHAKE_SENT,
        HANDSHAKE_ECHO,
        SESSION_INITIALIZED,
        SESSION_REJECTED;
    }
    
}
