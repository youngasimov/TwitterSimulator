/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import java.util.List;
import usm.simulacion.twitter.core.Event;

/**
 *
 * @author camilovera
 */
public class RandomEventGenerator implements EventGeneratorAlgoritm{
    
    private NetworkManager network;
    private long deltaTime;

    @Override
    public long getDeltaTime() {
        if(Math.random()>0.5){
            return 2;
        }else{
            return 1;
        }
    }

    @Override
    public void setNetworkManager(NetworkManager network) {
        this.network = network;
    }

    @Override
    public Event generateEvent(Event procecedEvent) {
        List<User> users = network.getUsers();
        User user = users.get(Math.round((float)(Math.random()*(users.size()-1))));
        return new NewTweetEvent(user, new Tweet(Math.round((float)(Math.random()*4 + Math.random()*73 + Math.random()*306 + Math.random()*1234))));
    }
    
}
