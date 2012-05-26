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
    private int tweetsIds = 0;

    @Override
    public Double getDeltaTime() {
        return Math.random()*3;
    }

    @Override
    public void setNetworkManager(NetworkManager network) {
        this.network = network;
    }

    @Override
    public Event generateEvent(Event procecedEvent) {
        Tweet t;
        if(Math.random()>0.6){
            List<User> users = network.getUsers();
            User user = users.get(Math.round((float)(Math.random()*(users.size()-1))));
            //t = new Tweet(Math.round((float)(Math.random()*4 + Math.random()*73 + Math.random()*306 + Math.random()*1234)));
            t = new Tweet(tweetsIds);
            tweetsIds++;
            t.setMessage("generico");
            t.setOwner(user);
            return new NewTweetEvent(user, t);
        }else{
            List<User> users = network.getUsers();
            User user = users.get(Math.round((float)(Math.random()*(users.size()-1))));
            if(user.getIncomingTweets().size()==0){
                //t = new Tweet(Math.round((float)(Math.random()*4 + Math.random()*73 + Math.random()*306 + Math.random()*1234)));
                t = new Tweet(tweetsIds);
                tweetsIds++;
                t.setMessage("generico");
                t.setOwner(user);
                return new NewTweetEvent(user, t);
            }else{
                t = user.getIncomingTweets().get((int)(Math.floor(Math.random()*user.getIncomingTweets().size())));
                return new NewReTweetEvent(user, t);
            }
        }
    }
    
}
