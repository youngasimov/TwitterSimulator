/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.io;

import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.core.SimulationEvent;
import usm.simulacion.twitter.core.SimulationEventHandler;
import usm.simulacion.twitter.simulator.AddUserEvent;
import usm.simulacion.twitter.simulator.AddUserEventHandler;
import usm.simulacion.twitter.simulator.NetworkManager;
import usm.simulacion.twitter.simulator.NewReTweetEvent;
import usm.simulacion.twitter.simulator.NewReTweetEventHandler;
import usm.simulacion.twitter.simulator.NewTweetEvent;
import usm.simulacion.twitter.simulator.NewTweetEventHandler;

/**
 *
 * @author camilovera
 */
public class Output {
    
    private EventBus eventBus;
    private NetworkManager networkManager;
    
    public Output(EventBus eventBus,NetworkManager networkManager){
        this.eventBus = eventBus;
        this.networkManager = networkManager;
        bind();
    }
    
    private String getTime(){
        return "Time: "+networkManager.getCurrentTime()+"________>";
    }
    
    private void bind(){
        eventBus.registerEventHandler(AddUserEvent.TYPE, new AddUserEventHandler() {

            @Override
            public void addUser(AddUserEvent event) {
                System.out.println(getTime()+"se a añadido un nuevo usuario: "+event.getUser().getName());
            }
        });
        
        eventBus.registerEventHandler(NewTweetEvent.TYPE, new NewTweetEventHandler() {

            @Override
            public void onNewTweet(NewTweetEvent event) {
                System.out.println(getTime()+"el usuario: "+event.getUser().getName()+" a generado un nuevo tweet con id: "+event.getTweet().getId());
            }
        });
        
        eventBus.registerEventHandler(NewReTweetEvent.TYPE, new NewReTweetEventHandler() {

            @Override
            public void onReTweet(NewReTweetEvent event) {
                System.out.println(getTime()+"el usuario: "+event.getUser().getName()+" a retuitiado un tweet con id: "+event.getTweet().getId()+" cuyo autor original es: "+event.getTweet().getOwner());
            }
        });
        
        eventBus.registerEventHandler(SimulationEvent.TYPE, new SimulationEventHandler() {

            @Override
            public void onSimulationEvent(SimulationEvent event) {
                if(event.getState() == SimulationEvent.FINISH){
                    System.out.println(getTime()+"El simulador ha finalizado");
                }
            }
        });
    }
    
}
