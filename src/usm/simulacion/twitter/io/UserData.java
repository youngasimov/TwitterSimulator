/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.io;

import java.util.Map;
import usm.simulacion.twitter.probability.ProbabilisticFunction;

/**
 *
 * @author camilovera
 */
public class UserData {
    
    private int generate;
    private int followerWeight;
    private Map<String,String> paramsOfEventGenerator;
    private String type;
    private String eventGenerator;
    private ProbabilisticFunction daysPerTweet;
    private ProbabilisticFunction tweetsPerRetweet;
    private ProbabilisticFunction tweetsPerRetweeted;
    private ProbabilisticFunction folowings;

    public ProbabilisticFunction getDaysPerTweet() {
        return daysPerTweet;
    }

    public void setDaysPerTweet(ProbabilisticFunction daysPerTweet) {
        this.daysPerTweet = daysPerTweet;
    }

    public String getEventGenerator() {
        return eventGenerator;
    }

    public void setEventGenerator(String eventGenerator) {
        this.eventGenerator = eventGenerator;
    }

    public int getFollowerWeight() {
        return followerWeight;
    }

    public void setFollowerWeight(int followerWeight) {
        this.followerWeight = followerWeight;
    }

    public ProbabilisticFunction getFolowings() {
        return folowings;
    }

    public void setFolowings(ProbabilisticFunction folowings) {
        this.folowings = folowings;
    }

    public int getGenerate() {
        return generate;
    }

    public void setGenerate(int generate) {
        this.generate = generate;
    }

    public Map<String,String> getParamsOfEventGenerator() {
        return paramsOfEventGenerator;
    }

    public void setParamsOfEventGenerator(Map<String,String> paramsOfEventGenerator) {
        this.paramsOfEventGenerator = paramsOfEventGenerator;
    }

    public ProbabilisticFunction getTweetsPerRetweet() {
        return tweetsPerRetweet;
    }

    public void setTweetsPerRetweet(ProbabilisticFunction tweetsPerRetweet) {
        this.tweetsPerRetweet = tweetsPerRetweet;
    }

    public ProbabilisticFunction getTweetsPerRetweeted() {
        return tweetsPerRetweeted;
    }

    public void setTweetsPerRetweeted(ProbabilisticFunction tweetsPerRetweeted) {
        this.tweetsPerRetweeted = tweetsPerRetweeted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
