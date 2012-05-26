/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author camilovera
 */
public class User implements HasIncomingTweets, HasOwnTweets{
    
    
    private List<Tweet> incomingTweets;
    private List<Tweet> ownTweets;
    private String name;
    private int id;
    
    public User(int id, String name){
        this.id = id;
        this.name = name;
        incomingTweets = new ArrayList<Tweet>();
        ownTweets = new ArrayList<Tweet>();
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
}
