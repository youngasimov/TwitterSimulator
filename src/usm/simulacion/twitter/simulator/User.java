/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import java.util.ArrayList;
import java.util.List;
import usm.simulacion.twitter.probability.ProbabilisticFunction;

/**
 *
 * @author camilovera
 */
public class User implements HasIncomingTweets, HasOwnTweets{
    
    public static final String TYPE_COMMON = "Common";
    public static final String TYPE_CELEBRITY = "Celebrity";
    public static final String TYPE_COMPANY = "Company";
    
    private List<Tweet> incomingTweets;
    private List<Tweet> ownTweets;
    private String name;
    private String type;
    private int id;
    private double daysPerTweet;
    private double retweetEveryXTweets;
    private double retweetedEveryXTweets;
    private ProbabilisticFunction nextEventTimeGenerator;
    
    public User(int id, String name, String tipo){
        this.id = id;
        this.name = name;
        this.type = tipo;
        incomingTweets = new ArrayList<Tweet>();
        ownTweets = new ArrayList<Tweet>();
        daysPerTweet = Double.MAX_VALUE;
        retweetEveryXTweets = Double.MAX_VALUE;
        retweetedEveryXTweets = Double.MAX_VALUE;
    }

    public ProbabilisticFunction getNextEventTimeGenerator() {
        return nextEventTimeGenerator;
    }

    public void setNextEventTimeGenerator(ProbabilisticFunction nextEventTimeGenerator) {
        this.nextEventTimeGenerator = nextEventTimeGenerator;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public int getId(){
        return id;
    }

    public double getRetweetEveryXTweets() {
        return retweetEveryXTweets;
    }

    public void setRetweetEveryXTweets(double retweetEveryXTweets) {
        this.retweetEveryXTweets = retweetEveryXTweets;
    }

    public double getRetweetedEveryXTweets() {
        return retweetedEveryXTweets;
    }

    public void setRetweetedEveryXTweets(double retweetedEveryXTweets) {
        this.retweetedEveryXTweets = retweetedEveryXTweets;
    }

    public double getDaysPerTweet() {
        return daysPerTweet;
    }

    public void setDaysPerTweet(double daysPerTweet) {
        this.daysPerTweet = daysPerTweet;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public void clearIncomingTweets(){
        incomingTweets.clear();
    }
    
    
    public void clearOwnTweets(){
        ownTweets.clear();
    }

    @Override
    public Tweet getIncomingTweet(int id) {
        Tweet aux = new Tweet(id);
        if(incomingTweets.contains(aux)){
            return incomingTweets.get(incomingTweets.indexOf(aux));
        }else{
            return null;
        }
    }

    @Override
    public List<Tweet> getIncomingTweets() {
        return incomingTweets;
    }

    @Override
    public void addIncomingTweet(Tweet tweet) {
        incomingTweets.add(tweet);
    }

    @Override
    public List<Tweet> getOwnTweets() {
        return ownTweets;
    }

    @Override
    public Tweet getOwnTweet(int id) {
        Tweet aux = new Tweet(id);
        if(ownTweets.contains(aux)){
            return ownTweets.get(ownTweets.indexOf(aux));
        }else{
            return null;
        }
    }

    @Override
    public void addOwnTweet(Tweet tweet) {
        ownTweets.add(tweet);
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof User && ((User)o).getId() == this.id){
            return true;
        }else if(o instanceof Integer && ((Integer)o) == this.id){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return id;
    }
    
    public User clone(){
        User u = new User(id,name,type);
        u.setDaysPerTweet(daysPerTweet);
        u.setRetweetEveryXTweets(retweetEveryXTweets);
        u.setRetweetedEveryXTweets(retweetedEveryXTweets);
        u.setNextEventTimeGenerator(nextEventTimeGenerator);
        return u;
    }
}