/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import usm.simulacion.twitter.core.Event;
import usm.simulacion.twitter.core.Type;

/**
 *
 * @author camilovera
 */
public class AddFollowerEvent implements Event<AddFollowerEventHandler> {
    
    public static Type<AddFollowerEventHandler> TYPE = new Type<AddFollowerEventHandler>();
    
    private User user;
    private User follower;
    
    public AddFollowerEvent(User user, User follower){
        this.user = user;
        this.follower = follower;
    }
    
    public User getUser(){
        return this.user;
    }
    
    public User getFollower(){
        return this.follower;
    }
    
    @Override
    public Type<AddFollowerEventHandler> getType() {
        return TYPE;
    }

    @Override
    public void dispach(AddFollowerEventHandler eventHandler) {
        eventHandler.addFollower(this);
    }
    
}
