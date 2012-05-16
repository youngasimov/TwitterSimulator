/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import usm.simulacion.twitter.core.Event;
import usm.simulacion.twitter.core.Type;

/**
 *
 * @author camilovera
 */
public class NewTweetEvent implements Event<NewTweetEventHandler>{

    public static Type<NewTweetEventHandler> TYPE = new Type<NewTweetEventHandler>();
    
    private UserBase user;
    private TweetBase tweet;
    
    public NewTweetEvent(UserBase user, TweetBase tweet){
        this.user = user;
        this.tweet = tweet;
    }

    public TweetBase getTweet() {
        return tweet;
    }

    public UserBase getUser() {
        return user;
    }
    
    @Override
    public Type<NewTweetEventHandler> getType() {
        return TYPE;
    }

    @Override
    public void dispach(NewTweetEventHandler eventHandler) {
        eventHandler.onNewTweet(this);
    }
    
}
