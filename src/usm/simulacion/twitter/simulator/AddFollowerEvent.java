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
    
    private int userId;
    private int followerId;
    
    public AddFollowerEvent(int user, int follower){
        this.userId = user;
        this.followerId = follower;
    }
    
    public int getUserId(){
        return this.userId;
    }
    
    public int getFollowerId(){
        return this.followerId;
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
