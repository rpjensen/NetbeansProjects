

package encryption_utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ryan Jensen
 * @version Oct 12, 2014
 */
public class Connection {
    private final Map<Key,EncryptedSession> hosts;
    private final ArrayList<String> log;
    
    private Connection(){
        this.hosts = new HashMap<>();
        this.log = new ArrayList<>();
    }
    
    public Key acceptConnection(EncryptedSession newHost){
        if (newHost == null){throw new NullPointerException("newHost should not be null");}
        if (hosts.size() > 2){ throw new IllegalStateException("Trying to join an established connection");}
        
        Key key = new Key();
        hosts.put(key, newHost);
        return key;
    }
}
