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
public class TimeEvent implements Event<TimeEventHandler> {

    public static Type<TimeEventHandler> TYPE = new Type<TimeEventHandler>();
    
    private Double currentTime;
    
    public TimeEvent(Double currentTime){
        this.currentTime = currentTime;
    }
    
    @Override
    public Type<TimeEventHandler> getType() {
        return TYPE;
    }

    @Override
    public void dispach(TimeEventHandler eventHandler) {
        eventHandler.updateTime(currentTime);
    }
    
}
