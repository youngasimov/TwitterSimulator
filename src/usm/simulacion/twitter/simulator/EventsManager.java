package usm.simulacion.twitter.simulator;

import java.util.List;
import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.core.SimulationEvent;

/**
 *
 * @author camilovera
 */
public class EventsManager {
    
    private EventBus eventBus;
    private NetworkManager networkManager;
    private Double maxTime = 5000.0;
    private Double securityDelta = 200.0;
    
    public EventsManager(EventBus eventBus,NetworkManager networkManager){
        this.eventBus = eventBus;
        this.networkManager = networkManager;
        bind();
    }
    
    private void bind(){
        
        this.eventBus.registerEventHandler(TimeEvent.TYPE, new TimeEventHandler() {

            @Override
            public void updateTime(Double currentTime) {
                if(currentTime >= (maxTime-securityDelta)){
                    eventBus.fireEvent(new SimulationEvent(SimulationEvent.FINISH));
                }
            }
        });
        
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
        event.getTweet().setSteps(0);
        event.getTweet().setTimeSignature(networkManager.getCurrentTime());
        event.getUser().addOwnTweet(event.getTweet());
        List<User> followers = networkManager.getFollowers(event.getUser());
        Tweet t;
        for(User follower:followers){
            t = new Tweet(event.getTweet().getId());
            t.setMessage(event.getTweet().getMessage());
            t.setTimeSignature(event.getTweet().getTimeSignature());
            t.setSteps(1);
            t.setOwner(event.getUser());
            follower.addIncomingTweet(t);
        }
        networkManager.generateEvent(event);
    }
    
    /**
     * se ejecuta cada vez que recibe un evento de nuevo re-tweet
     * @param event 
     */
    private void onNewReTweet(NewReTweetEvent event){
        List<User> followers = networkManager.getFollowers(event.getUser());
        Tweet t;
        for(User follower:followers){
            t = new Tweet(event.getTweet().getId());
            t.setMessage(event.getTweet().getMessage());
            t.setSteps(event.getTweet().getSteps()+1);
            t.setTimeSignature(networkManager.getCurrentTime());
            t.setOwner(event.getTweet().getOwner());
            follower.addIncomingTweet(t);
        }
        networkManager.generateEvent(event);
    }
}
