/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter;

import java.io.IOException;
import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.core.EventStackManager;
import usm.simulacion.twitter.core.FutureEventEvent;
import usm.simulacion.twitter.core.SimulationEvent;
import usm.simulacion.twitter.io.ConfiguratorReader;
import usm.simulacion.twitter.io.Output;
import usm.simulacion.twitter.io.OutputGlobal;
import usm.simulacion.twitter.simulator.ComparatorBySimulationTime;
import usm.simulacion.twitter.simulator.NetworkManager;
import usm.simulacion.twitter.simulator.TweetsManager;

/**
 *
 * @author camilovera
 */
public class Task extends Thread {
    
    public interface FinishHandler {
        void onFinish();
    }
    
    private int simulations;
    private ConfiguratorReader lector;
    private EventBus bus;
    private NetworkManager networkManager;
    private FinishHandler fh;
    
    
    
    public Task(int simulations, ConfiguratorReader lector, EventBus eventBus) throws IOException{
        this.simulations = simulations;
        this.lector = lector;
        this.bus = eventBus;
        EventStackManager stackManager = new EventStackManager(bus, new ComparatorBySimulationTime());
        networkManager = new NetworkManager(bus,(double)lector.getSimulationTime());
        
        Output output = new Output(eventBus, networkManager);
        TweetsManager tweetsManager = new TweetsManager(bus,networkManager,lector.getTweetMean(),lector.getTweetDeviation(),lector.getReTweetMean(),lector.getReTweetDeviation(),lector.getReTweetDivisor());
    }

    public void setMaxRetwitterEveryXTweets(double maxRetwitterEveryXTweets) {
        networkManager.setMaxRetwitterEveryXTweets(maxRetwitterEveryXTweets);
    }

    public void setMinRetwitteredEveryXTweets(double minRetwitteredEveryXTweets) {
        networkManager.setMinRetwitteredEveryXTweets(minRetwitteredEveryXTweets);
    }
    
    public NetworkManager getNetworkManager(){
        return networkManager;
    }

    public void setFinishHandler(FinishHandler fh){
        this.fh = fh;
    }
    
    @Override
    public void run() {
        for(int i=0; i<simulations; i++){
            long time = System.currentTimeMillis();
            System.out.println("simulacion iniciada");
            bus.fireEvent(new FutureEventEvent((double)lector.getSimulationTime(), 0.0, new SimulationEvent(SimulationEvent.FINISH)));
            bus.fireEvent(new SimulationEvent(SimulationEvent.START));
            long delta = System.currentTimeMillis() - time;
            System.out.println("simulacion terminada en "+delta+" milisegundos");
            System.out.println();
            System.gc();
            bus.fireEvent(new SimulationEvent(SimulationEvent.RESET));
        }
        fh.onFinish();
    }
    
    
    
}
