package usm.simulacion.twitter;

import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.core.EventStackManager;
import usm.simulacion.twitter.core.SimulationEvent;
import usm.simulacion.twitter.io.Output;
import usm.simulacion.twitter.simulator.AddFollowerEvent;
import usm.simulacion.twitter.simulator.AddFollowingEvent;
import usm.simulacion.twitter.simulator.AddUserEvent;
import usm.simulacion.twitter.simulator.ComparatorBySimulationTime;
import usm.simulacion.twitter.simulator.EventsManager;
import usm.simulacion.twitter.simulator.NetworkManager;
import usm.simulacion.twitter.simulator.RandomEventGenerator;
import usm.simulacion.twitter.simulator.User;

/**
 *
 * @author camilovera
 */
public class MainSimulator {
    
    public static void main(String[] args){
        
        
        EventBus bus = new EventBus();
        EventStackManager stackManager = new EventStackManager(bus, new ComparatorBySimulationTime());
        NetworkManager networkManager = new NetworkManager(bus,new RandomEventGenerator());
        EventsManager eventsManager = new EventsManager(bus,networkManager);
        Output output = new Output(bus, networkManager);
        //Configuración: leer datos de entrada y asignar parametros al simulador
        //Ejemplo
        User pedro = new User(1, "pedro");
        User juan = new User(2, "juan");
        User diego = new User(3, "diego");
        bus.fireEvent(new AddUserEvent(pedro));
        bus.fireEvent(new AddUserEvent(juan));
        bus.fireEvent(new AddUserEvent(diego));
        bus.fireEvent(new AddFollowerEvent(pedro, juan));
        bus.fireEvent(new AddFollowerEvent(pedro, diego));
        bus.fireEvent(new AddFollowerEvent(diego, pedro));
        bus.fireEvent(new AddFollowerEvent(juan, diego));
        bus.fireEvent(new AddFollowingEvent(pedro, juan));
        
        //Ejemplo
        networkManager.addParam("param1", 0);
        networkManager.addParam("param2", 0.1);
        networkManager.addParam("param3", "parametrox");
        
        //Fin configuración 
        //inicio de simulación
        networkManager.generateInitialRandomEvents(3);
        bus.fireEvent(new SimulationEvent(SimulationEvent.START));
    }
    
}
