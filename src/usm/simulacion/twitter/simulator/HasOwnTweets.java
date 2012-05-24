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
public interface HasOwnTweets {
    List<Tweet> getOwnTweets();
    Tweet getOwnTweet(int id);
    void addOwnTweet(Tweet tweet);
}
