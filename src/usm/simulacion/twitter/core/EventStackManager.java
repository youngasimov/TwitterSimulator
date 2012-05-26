package usm.simulacion.twitter.core;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import usm.simulacion.twitter.simulator.TimeEvent;

/**
 *
 * @author camilovera
 */
public class EventStackManager {
    
    
    private EventBus eventBus;
    private Map<Type,AutoSortedList<FutureEventEvent>> eventStack;
    private Comparator<FutureEventEvent> comparator;
    private boolean working;
    
    
    
    public EventStackManager(EventBus eventBus, Comparator<FutureEventEvent> comparator){
        this.eventBus = eventBus;
        eventStack = new HashMap<Type,AutoSortedList<FutureEventEvent>>();
        this.comparator = comparator;
        working = false;
        bind();
    }
    
    private void bind(){
        eventBus.registerEventHandler(FutureEventEvent.TYPE, new FutureEventEventHandler() {

            @Override
            public void onFutureEvent(FutureEventEvent event) {
                if(!eventStack.containsKey(event.getFutureEvent().getType())){
                    eventStack.put(event.getFutureEvent().getType(), new AutoSortedList<FutureEventEvent>(comparator));
                }
                eventStack.get(event.getFutureEvent().getType()).add(event);
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
            }else{
                eventBus.fireEvent(new TimeEvent(e.getCurrentTime()+e.getDeltaTime()));
                eventBus.fireEvent(e.getFutureEvent());
            }
        }
        
    }
    
    private void onSimulationFinish(){
        working = false;
    }
    
    private FutureEventEvent nextEvent(){
        Iterator<AutoSortedList<FutureEventEvent>> listas = eventStack.values().iterator();
        AutoSortedList<FutureEventEvent> primeros = new AutoSortedList<FutureEventEvent>(comparator);
        while(listas.hasNext()){
            AutoSortedList<FutureEventEvent> lista = listas.next();
            if(!lista.isEmpty()){
                primeros.add(lista.first());
            }
        }
        FutureEventEvent selectedEvent = primeros.first();
        return eventStack.get(selectedEvent.getFutureEvent().getType()).pollFirst();
    }
}
