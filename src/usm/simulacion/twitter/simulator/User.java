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
public class User implements UserBase, HasIncomingTweets, HasOwnTweets{
    
    
    private List<TweetBase> incomingTweets;
    private List<TweetBase> ownTweets;
    private String name;
    private int id;
    
    public User(int id, String name){
        this.id = id;
        this.name = name;
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
    public TweetBase getIncomingTweet(int id) {
        TweetBase aux = new Tweet(id);
        if(incomingTweets.contains(aux)){
            return incomingTweets.get(incomingTweets.indexOf(aux));
        }else{
            return null;
        }
    }

    @Override
    public List<TweetBase> getIncomingTweets() {
        return incomingTweets;
    }

    @Override
    public void addIncomingTweet(TweetBase tweet) {
        incomingTweets.add(tweet);
    }

    @Override
    public List<TweetBase> getOwnTweets() {
        return ownTweets;
    }

    @Override
    public TweetBase getOwnTweet(int id) {
        TweetBase aux = new Tweet(id);
        if(ownTweets.contains(aux)){
            return ownTweets.get(ownTweets.indexOf(aux));
        }else{
            return null;
        }
    }

    @Override
    public void addOwnTweet(TweetBase tweet) {
        ownTweets.add(tweet);
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof User && ((User)o).getId() == this.id){
            return true;
        }else{
            return false;
        }
    }
}
