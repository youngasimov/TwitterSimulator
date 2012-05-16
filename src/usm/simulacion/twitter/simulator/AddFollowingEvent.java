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
public class AddFollowingEvent implements Event<AddFollowingEventHandler> {
    
    public static Type<AddFollowingEventHandler> TYPE = new Type<AddFollowingEventHandler>();

    private UserBase user;
    private UserBase following;
    
    public AddFollowingEvent(UserBase user, UserBase following){
        this.user = user;
        this.following = following;
    }
    
    public UserBase getUser(){
        return this.user;
    }
    
    public UserBase getFollowing(){
        return this.following;
    }
    
    
    @Override
    public Type<AddFollowingEventHandler> getType() {
        return TYPE;
    }

    @Override
    public void dispach(AddFollowingEventHandler eventHandler) {
        eventHandler.addFollowing(this);
    }
    
}
