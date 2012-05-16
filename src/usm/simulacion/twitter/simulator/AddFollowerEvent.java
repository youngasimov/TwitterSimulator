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
    
    private UserBase user;
    private UserBase follower;
    
    public AddFollowerEvent(UserBase user, UserBase follower){
        this.user = user;
        this.follower = follower;
    }
    
    public UserBase getUser(){
        return this.user;
    }
    
    public UserBase getFollower(){
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
