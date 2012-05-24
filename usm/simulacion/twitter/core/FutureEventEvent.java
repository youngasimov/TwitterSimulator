/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.core;

/**
 *
 * @author camilovera
 */
public class FutureEventEvent implements Event<FutureEventEventHandler>/*,Comparable<FutureEventEvent>*/ {

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

    /*
    
    @Override
    public int compareTo(FutureEventEvent t) {
        Long timeObject = new Long(t.getCurrentTime()+t.getDeltaTime());
        Long timeThis = new Long(this.getCurrentTime()+this.getDeltaTime());
        return timeThis.compareTo(timeObject);
    }

    @Override
    public int hashCode() {
        return (int)(this.getCurrentTime()+this.getDeltaTime());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FutureEventEvent other = (FutureEventEvent) obj;
        if (this.futureEvent != other.futureEvent && (this.futureEvent == null || !this.futureEvent.equals(other.futureEvent))) {
            return false;
        }
        if (this.deltaTime != other.deltaTime) {
            return false;
        }
        if (this.currentTime != other.currentTime) {
            return false;
        }
        return true;
    }*/
    
}
