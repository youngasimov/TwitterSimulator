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
    private long deltaTime;
    private long currentTime;
    
    public FutureEventEvent(long deltaTime,long currentTime, Event event){
        this.futureEvent = event;
        this.deltaTime = deltaTime;
        this.currentTime = currentTime;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public long getDeltaTime() {
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
