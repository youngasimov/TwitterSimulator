package usm.simulacion.twitter;

import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.core.EventStackManager;
import usm.simulacion.twitter.core.SimulationEvent;
import usm.simulacion.twitter.io.LectorXml;
import usm.simulacion.twitter.io.Output;
import usm.simulacion.twitter.simulator.ComparatorBySimulationTime;
import usm.simulacion.twitter.simulator.EventsManager;
import usm.simulacion.twitter.simulator.NetworkManager;
import usm.simulacion.twitter.simulator.RandomEventGenerator;

/**
 *
 * @author camilovera
 */
public class MainSimulator {
    
    public static void main(String[] args){
        
        
        EventBus bus = new EventBus();
        NetworkManager networkManager = new NetworkManager(bus,new RandomEventGenerator());
        Output output = new Output(bus, networkManager);
        EventStackManager stackManager = new EventStackManager(bus, new ComparatorBySimulationTime());
        EventsManager eventsManager = new EventsManager(bus,networkManager);
        //Configuración: leer datos de entrada y asignar parametros al simulador
        //Ejemplo
        LectorXml lector = new LectorXml();
        lector.cargarDocumento("user.xml");
        lector.LeerUsuarios(bus);
        lector.leerFollowers(bus);
        
        networkManager.addParam("param1", 0);
        networkManager.addParam("param2", 0.1);
        networkManager.addParam("param3", "parametrox");
        
        //Fin configuración 
        //inicio de simulación
        networkManager.generateInitialRandomEvents(8);
        bus.fireEvent(new SimulationEvent(SimulationEvent.START));
    }
    
}
