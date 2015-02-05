/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import usm.simulacion.twitter.MainSimulator;
import usm.simulacion.twitter.core.*;
//import usm.simulacion.twitter.core.SimulationEvent;


/**
 * Esta clase contiene la información de toda la red,
 * ademas de determinar cuando una persona genera un nuevo
 * tweet o re-tweet
 * @author camilovera
 */
public class NetworkManager{
    
    private class LocalNetwork{
        
        public List<User> followers;
        public List<User> followings;
        
        public LocalNetwork(){
            followers = new ArrayList<User>();
            followings = new ArrayList<User>();
        }
    }
    
    private EventBus eventBus;
    private Map<User,LocalNetwork> red;
    private double time;
    private double maxSimulationTime;
    private double minRetwitteredEveryXTweets;
    private double maxRetwitterEveryXTweets;
    private int lastTweetId;
    //private int nUsers;
    //Grafo Red = new Grafo();  // se crea el grafo que contendra la red.
    
    
    public NetworkManager(EventBus eventBus, double maxSimulationTime){
        this.eventBus = eventBus;
        red = new HashMap<User,LocalNetwork>();
        time = 0.0;
        lastTweetId = 0;
        this.maxSimulationTime = maxSimulationTime;
        bind();
    }

    public double getMaxSimulationTime() {
        return maxSimulationTime;
    }

    public void setMaxSimulationTime(double maxSimulationTime) {
        this.maxSimulationTime = maxSimulationTime;
    }
    
    public Double getCurrentTime(){
        return time;
    }

    public int getLastTweetId() {
        return lastTweetId;
    }

    public void setLastTweetId(int lastTweetId) {
        this.lastTweetId = lastTweetId;
    }
    

    public double getMaxRetwitterEveryXTweets() {
        return maxRetwitterEveryXTweets;
    }

    public void setMaxRetwitterEveryXTweets(double maxRetwitterEveryXTweets) {
        this.maxRetwitterEveryXTweets = maxRetwitterEveryXTweets;
    }

    public double getMinRetwitteredEveryXTweets() {
        return minRetwitteredEveryXTweets;
    }

    public void setMinRetwitteredEveryXTweets(double minRetwitteredEveryXTweets) {
        this.minRetwitteredEveryXTweets = minRetwitteredEveryXTweets;
    }
    
    public ArrayList<User> getUsers(){
        return new ArrayList<User>(red.keySet());
    }
    
    public User getUser(int id){
        Integer idUser = new Integer(id);
        if(red.keySet().contains(idUser)){
            List<User> aux = new ArrayList<User>(red.keySet());
            return aux.get(aux.indexOf(idUser));
        }
        return null;
    }
    
    public List<User> getFollowers(User user){
        if(!red.containsKey(user)){
            return new ArrayList<User>();
        }else{
            return red.get(user).followers;
        }
    }
    
    public List<User> getFollowings(User user){
        if(!red.containsKey(user)){
            return new ArrayList<User>();
        }else{
            return red.get(user).followings;
        }
    }
    
    
    private void bind(){
        eventBus.registerEventHandler(TimeEvent.TYPE, new TimeEventHandler() {

            @Override
            public void updateTime(Double currentTime) {
                time = currentTime;
                //System.out.println(time+"/"+maxSimulationTime);
            }
        });
        
        eventBus.registerEventHandler(SimulationEvent.TYPE, new SimulationEventHandler() {

            @Override
            public void onSimulationEvent(SimulationEvent event) {
                if(event.getState() == SimulationEvent.RESET){
                    onReset();
                }
            }
        });
        
        eventBus.registerEventHandler(AddUserEvent.TYPE, new AddUserEventHandler() {

            @Override
            public void addUser(AddUserEvent event) {
                //System.out.println("nuev@"+event.getUser().getType()+":\t\t"+event.getUser().getName());
                onAddUser(event);
            }
        });
        
        eventBus.registerEventHandler(AddFollowerEvent.TYPE, new AddFollowerEventHandler() {

            @Override
            public void addFollower(AddFollowerEvent event) {
                //System.out.println("el usuario "+event.getUserId()+" a empezado a ser seguido por el usuario "+event.getFollowerId());
                onAddFollower(event.getUserId(), event.getFollowerId());
            }
        });
        
        eventBus.registerEventHandler(AddFollowingEvent.TYPE, new AddFollowingEventHandler() {

            @Override
            public void addFollowing(AddFollowingEvent event) {
                //System.out.println("el usuario "+event.getUserId()+" a empezado a seguir al usuario "+event.getFollowingId());
                onAddFollowing(event.getUserId(), event.getFollowingId());
            }
        });
    }
    
    private void onReset(){
        time = 0;
        lastTweetId = 0;
        for(User u:red.keySet()){
            u.getIncomingTweets().clear();
            u.getOwnTweets().clear();
            Tweet t = new Tweet(lastTweetId++);
            t.setOwner(u);
            t.setSteps(0);
            //t.setMessage("generico");
            Event e = new NewTweetEvent(u, t);
            u.getNextEventTimeGenerator().setSeed((long)(MainSimulator.random.nextDuble()*10000));
            eventBus.fireEvent(new FutureEventEvent(Math.abs(u.getNextEventTimeGenerator().nextDuble()),time,e));
        }
    }
    
    private void onAddUser(AddUserEvent userEvent){
        User u = userEvent.getUser();
        red.put(u, new LocalNetwork());
        Tweet t = new Tweet(lastTweetId++);
        t.setOwner(u);
        t.setSteps(0);
        //t.setMessage("generico");
        Event e = new NewTweetEvent(u, t);
        double delta = u.getNextEventTimeGenerator().nextDuble();
        eventBus.fireEvent(new FutureEventEvent(Math.abs(delta),time,e));
    }
    
    private void onAddFollower(int user, int follower){
        List<User> users = new ArrayList<User>(red.keySet());
        User aux = new User(user, null,null);
        User aux2 = new User(follower, null,null);
        User u = users.get(users.indexOf(aux));
        User f = users.get(users.indexOf(aux2));
        
        if(!red.get(u).followers.contains(f)){
            red.get(u).followers.add(f);
        }
        if(!red.get(f).followings.contains(u)){
            red.get(f).followings.add(u);
        }
        
    }
    
    private void onAddFollowing(int user, int following){
        
        List<User> users = new ArrayList<User>(red.keySet());
        User aux = new User(user, null,null);
        User aux2 = new User(following, null,null);
        User u = users.get(users.indexOf(aux));
        User f = users.get(users.indexOf(aux2));
        
        if(!red.get(u).followings.contains(f)){
            red.get(u).followings.add(f);
        }
        if(!red.get(f).followers.contains(u)){
            red.get(f).followers.add(u);
        }
    }
    
}
