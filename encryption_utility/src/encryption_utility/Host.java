

package encryption_utility;

import java.util.ArrayList;

/**
 * An encryption message host that can generate RSA keys, create a session to 
 * another host by way of host name lookup. The session will maintain a symmetric
 * encryption two way tunnel until terminated by both hosts. Can create digital
 * signatures and append them inside the message body.
 * @author Ryan Jensen
 * @version Sep 29, 2014
 */
public class Host {
    private static int currentIp;
    
    private ArrayList<Message> recievedMessages;
    private Network network;
    private final int hostIp;
    private final String hostName;
    private final RsaKey rsaKey;
    private EncryptedSession currentSession;
    private KeyObject currentKey;
    
    public String getName(){
        return this.hostName;
    }
    
    public String getIp(){
        return this.hostIp;
    }
    
    public void createConnection(Connection connection, String hostTo, int hostToIp, EncryptedSession.Requested requested){
        currentKey = new KeyObject();
        EncryptedSession.Builder builder = EncryptedSession.Builder.initWithHostAndKey(this, currentKey, requested);
        currentSession = builder.setHostName(hostTo).setHostIP(hostToIp).setConnection(connection).build();
        connection.acceptConnection(currentSession);
    }
    public String getHostname();
    public String getIP();
    public RsaKey getPublicKey();
    public void sendMessage(Message message);
    public void recieveMessage(Message message);
    public void terminateCurrentConnection();
    public ArrayList<EncryptedSession> getActiveSessions();
    public HostType getType();
    public ArrayList<Message> getRecievedRessages();
    
}
