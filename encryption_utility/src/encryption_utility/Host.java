

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
public interface Host {
    public Connection createConnection();
    public String getHostname();
    public String getIP();
    public RsaKey getPublicKey();
    public void sendMessage(Message message);
    public void recieveMessage(Message message);
    public void terminateCurrentConnection();
    public ArrayList<EncryptedSession> getActiveSessions();
    public HostType getType();
    public ArrayList<Message> getSendMessages();
    public ArrayList<Message> getRecievedRessages();
    
    public enum HostType {
        INTERACTIVE,
        WEB_SERVER,
        NON_RESPONDER,
        MAN_IN_THE_MIDDLE;
    }
    
}
