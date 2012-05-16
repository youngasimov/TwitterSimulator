package usm.simulacion.twitter.core;

import java.util.Comparator;
import java.util.TreeSet;
import usm.simulacion.twitter.simulator.TimeEvent;

/**
 *
 * @author camilovera
 */
public class EventStackManager {
    
    
    private EventBus eventBus;
    private TreeSet<FutureEventEvent> eventStack;
    private boolean working;
    
    
    
    public EventStackManager(EventBus eventBus, Comparator comparator){
        this.eventBus = eventBus;
        eventStack = new TreeSet<FutureEventEvent>(comparator);
        working = false;
        bind();
    }
    
    private void bind(){
        eventBus.registerEventHandler(FutureEventEvent.TYPE, new FutureEventEventHandler() {

            @Override
            public void onFutureEvent(FutureEventEvent event) {
                eventStack.add(event);
            }
        });
        eventBus.registerEventHandler(SimulationEvent.TYPE, new SimulationEventHandler() {

            @Override
            public void onSimulationEvent(SimulationEvent event) {
                if(event.getState() == SimulationEvent.START){
                    onSimulationStart();
                }else if(event.getState() == SimulationEvent.FINISH){
                    onSimulationFinish();
                }
            }
        });
    }
    
    private void onSimulationStart(){
        working = true;
        while(working){
            FutureEventEvent e = nextEvent();
            if(e == null){
                eventBus.fireEvent(new SimulationEvent(SimulationEvent.FINISH));
            }
            eventBus.fireEvent(new TimeEvent(e.getCurrentTime()+e.getDeltaTime()));
            eventBus.fireEvent(e.getFutureEvent());
        }
        
    }
    
    private void onSimulationFinish(){
        working = false;
    }
    
    private FutureEventEvent nextEvent(){
        return eventStack.pollFirst();
    }
}
