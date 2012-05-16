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
public class AddUserEvent implements Event<AddUserEventHandler> {

    public static Type<AddUserEventHandler> TYPE = new Type<AddUserEventHandler>();
    
    private UserBase user;
    
    public AddUserEvent(UserBase user){
        this.user = user;
    }
    
    public UserBase getUser(){
        return this.user;
    }
    
    @Override
    public Type<AddUserEventHandler> getType() {
        return TYPE;
    }

    @Override
    public void dispach(AddUserEventHandler eventHandler) {
        eventHandler.addUser(this);
    }
    
}
