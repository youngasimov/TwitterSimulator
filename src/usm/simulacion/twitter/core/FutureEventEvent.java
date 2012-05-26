/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.core;

/**
 *
 * @author camilovera
 */
public class FutureEventEvent implements Event<FutureEventEventHandler> {

    public static Type<FutureEventEventHandler> TYPE = new Type<FutureEventEventHandler>();
    
    private Event futureEvent;
    private Double deltaTime;
    private Double currentTime;
    
    public FutureEventEvent(Double deltaTime,Double currentTime, Event event){
        this.futureEvent = event;
        this.deltaTime = deltaTime;
        this.currentTime = currentTime;
    }

    public Double getCurrentTime() {
        return currentTime;
    }

    public Double getDeltaTime() {
        return deltaTime;
    }

    public Event getFutureEvent() {
        return futureEvent;
    }
    
    @Override
    public Type<FutureEventEventHandler> getType() {
        return TYPE;
    }

    @Override
    public void dispach(FutureEventEventHandler eventHandler) {
        eventHandler.onFutureEvent(this);
    }    
}
