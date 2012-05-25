/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import usm.simulacion.twitter.core.EventBus;

/**
 *
 * @author camilovera
 */
public class EventsManager {
    
    private EventBus eventBus;
    private NetworkManager networkManager;
    
    public EventsManager(EventBus eventBus,NetworkManager networkManager){
        this.eventBus = eventBus;
        this.networkManager = networkManager;
        bind();
    }
    
    private void bind(){
        
        this.eventBus.registerEventHandler(NewTweetEvent.TYPE, new NewTweetEventHandler() {

            @Override
            public void onNewTweet(NewTweetEvent event) {
                EventsManager.this.onNewTweet(event);
            }
        });
        
        this.eventBus.registerEventHandler(NewReTweetEvent.TYPE, new NewReTweetEventHandler() {

            @Override
            public void onReTweet(NewReTweetEvent event) {
                EventsManager.this.onNewReTweet(event);
            }
        });
    }
    
    /**
     * se ejecuta cada vez que recibe un evento de nuevo tweet
     * @param event 
     */
    private void onNewTweet(NewTweetEvent event){
        
        
        networkManager.generateEvent(event);
    }
    
    /**
     * se ejecuta cada vez que recibe un evento de nuevo re-tweet
     * @param event 
     */
    private void onNewReTweet(NewReTweetEvent event){
        
        
        networkManager.generateEvent(event);
    }
}
