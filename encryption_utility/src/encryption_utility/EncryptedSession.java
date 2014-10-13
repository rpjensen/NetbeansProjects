/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption_utility;

/**
 *
 * @author Ryan Jensen
 * @version Sep 29, 2014
 */
public class EncryptedSession {
    private final Host creator;
    private final Key creatorKey;
    private RsaEncryptor hostRsaSign;
    private final Connection connection;    
    private final Key connectionKey;
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
        this.creator = creator;
        this.creatorKey = creatorKey;
        this.connection = connection;
        this.hostName = hostName;
        this.hostIP = hostIP;
        this.hostRsaSign = new RsaEncrypt(rsaKey);
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
