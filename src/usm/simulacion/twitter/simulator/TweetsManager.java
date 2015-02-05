package usm.simulacion.twitter.simulator;

import java.util.List;
import java.util.Random;
import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.core.FutureEventEvent;
import usm.simulacion.twitter.probability.Normal;

/**
 *
 * @author camilovera
 */
public class TweetsManager {
    
    private EventBus eventBus;
    private NetworkManager networkManager;
    private Normal tweetNormal;
    private Normal reTweetNormal;
    private double divisor;
    private Random r;
    
    public TweetsManager(EventBus eventBus,NetworkManager networkManager, double tweetMean,double tweetDeviation,double reTweetMean,double reTweetDeviation, double divisor){
        this.eventBus = eventBus;
        this.networkManager = networkManager;
        tweetNormal = new Normal(tweetMean, tweetDeviation);
        reTweetNormal = new Normal(reTweetMean,reTweetDeviation);
        this.divisor = divisor; 
        r = new Random((long)divisor);
        bind();
    }
    
    private void bind(){
        this.eventBus.registerEventHandler(NewTweetEvent.TYPE, new NewTweetEventHandler() {

            @Override
            public void onNewTweet(NewTweetEvent event) {
                TweetsManager.this.onNewTweet(event);
            }
        });
        
        this.eventBus.registerEventHandler(NewReTweetEvent.TYPE, new NewReTweetEventHandler() {

            @Override
            public void onReTweet(NewReTweetEvent event) {
                TweetsManager.this.onNewReTweet(event);
            }
        });
    }
    
    /**
     * se ejecuta cada vez que recibe un evento de nuevo tweet
     * @param event 
     */
    private void onNewTweet(NewTweetEvent event){
        event.getTweet().setSteps(1);
        //event.getTweet().setTimeSignature(networkManager.getCurrentTime());
        //event.getUser().addOwnTweet(event.getTweet());
        List<User> followers = networkManager.getFollowers(event.getUser());
        if(followers.isEmpty()){
            return;
        }
        Tweet t;
        for(User follower:followers){
            t = new Tweet(event.getTweet().getId());
            //t.setMessage(event.getTweet().getMessage());
            t.setTimeSignature(event.getTweet().getTimeSignature());
            t.setSteps(1);
            t.setOwner(event.getUser());
            //follower.addIncomingTweet(t);
            double weight = follower.getRetweetEveryXTweets()*(1/event.getUser().getRetweetedEveryXTweets());
            double maxWeight = networkManager.getMaxRetwitterEveryXTweets()*(1/networkManager.getMinRetwitteredEveryXTweets());
            //double d = Math.abs(tweetNormal.nextDuble());
            double d = r.nextDouble();
            if(weight/maxWeight > d-0.05){
                eventBus.fireEvent(new FutureEventEvent(Math.abs(follower.getNextEventTimeGenerator().nextDuble()), networkManager.getCurrentTime(), new NewReTweetEvent(follower, t)));
            }
        }
        t = new Tweet(networkManager.getLastTweetId()+1);
        networkManager.setLastTweetId(t.getId());
        eventBus.fireEvent(new FutureEventEvent(Math.abs(event.getUser().getNextEventTimeGenerator().nextDuble()), networkManager.getCurrentTime(), new NewTweetEvent(event.getUser(), t)));
    }
    
    /**
     * se ejecuta cada vez que recibe un evento de nuevo re-tweet
     * @param event 
     */
    private void onNewReTweet(NewReTweetEvent event){
        List<User> followers = networkManager.getFollowers(event.getUser());
        if(followers.isEmpty()){
            return;
        }
        Tweet t;
        for(User follower:followers){
            t = new Tweet(event.getTweet().getId());
            //t.setMessage(event.getTweet().getMessage());
            t.setSteps(event.getTweet().getSteps()+1);
            t.setTimeSignature(networkManager.getCurrentTime());
            t.setOwner(event.getTweet().getOwner());
            //follower.addIncomingTweet(t);
            //exp = new Exponential(-t.getSteps());
            double weight = follower.getRetweetEveryXTweets()*(1/event.getUser().getRetweetedEveryXTweets())-((1/divisor)*(t.getSteps()));
            double maxWeight = networkManager.getMaxRetwitterEveryXTweets()*(1/networkManager.getMinRetwitteredEveryXTweets());
            //double d = Math.abs(reTweetNormal.nextDuble());
            double d = r.nextDouble();
            if(weight/maxWeight > d){
                eventBus.fireEvent(new FutureEventEvent(Math.abs(follower.getNextEventTimeGenerator().nextDuble()), networkManager.getCurrentTime(), new NewReTweetEvent(follower, t)));
            }
        }
    }
}
