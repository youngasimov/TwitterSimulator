/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import java.util.List;

/**
 *
 * @author camilovera
 */
public interface HasIncomingTweets {
    List<TweetBase> getIncomingTweets();
    TweetBase getIncomingTweet(int id);
    void addIncomingTweet(TweetBase tweet);
}
