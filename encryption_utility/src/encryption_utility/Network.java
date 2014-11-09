/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package encryption_utility;

import java.util.ArrayList;

/**
 *
 * @author Ryan Jensen
 * @version Sep 29, 2014
 */
public class Network {
    private ArrayList<Connection> currentConnections;
    private ArrayList<Host> hosts;
    
    public void createConnection(Host from, String hostName){
        Connection connection = new Connection(this);
        for (Host host : hosts){
            if (host.getName().equals(hostName)){
                from.
            }
        }
    }
    
    
}
