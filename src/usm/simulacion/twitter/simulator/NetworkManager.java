/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import usm.simulacion.twitter.core.Event;
import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.core.FutureEventEvent;
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
    private EventGeneratorAlgoritm algoritm;
    private Map<String,String> textData;
    private Map<String,Integer> intData;
    private Map<String,Double> doubleData;
    private Map<User,LocalNetwork> red;
    private Double time;
    //private int nUsers;
    //Grafo Red = new Grafo();  // se crea el grafo que contendra la red.
    
    
    public NetworkManager(EventBus eventBus,EventGeneratorAlgoritm algoritm){
        this.eventBus = eventBus;
        this.algoritm = algoritm;
        this.algoritm.setNetworkManager(this);
        red = new HashMap<User,LocalNetwork>();
        time = 0.0;
        bind();
    }
    
    public Double getCurrentTime(){
        return time;
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
    
   
    
    public void generateInitialRandomEvents(int events){
        
        //ejemplo
        Event e;
        for(int i=0;i<events;i++){
            e = algoritm.generateEvent(null);
            eventBus.fireEvent(new FutureEventEvent(algoritm.getDeltaTime(),time,e));
        }

    }
    
    public void generateEvent(Event procesedEvent){
        Event e = algoritm.generateEvent(procesedEvent);
        eventBus.fireEvent(new FutureEventEvent(time,algoritm.getDeltaTime(),e));
    }
    
    public void addParam(String var,String value){
        if(textData == null){
            textData = new HashMap<String, String>();
        }
        textData.put(var, value);
    }
    
    public void addParam(String var,Integer value){
        if(intData == null){
            intData = new HashMap<String, Integer>();
        }
        intData.put(var, value);
    }
    
    public void addParam(String var,Double value){
        if(doubleData == null){
            doubleData = new HashMap<String, Double>();
        }
        doubleData.put(var, value);
    }
    
    public String getTextParam(String var){
        if(textData != null && textData.containsKey(var)){
            return textData.get(var);
        }else{
            return null;
        }
    }
    
    public Integer getIntParam(String var){
        if(intData != null && intData.containsKey(var)){
            return intData.get(var);
        }else{
            return null;
        }
    }
    
    public Double getDoubleParam(String var){
        if(doubleData != null && doubleData.containsKey(var)){
            return doubleData.get(var);
        }else{
            return null;
        }
    }
    
    
    private void bind(){
        eventBus.registerEventHandler(TimeEvent.TYPE, new TimeEventHandler() {

            @Override
            public void updateTime(Double currentTime) {
                time = currentTime;
            }
        });
        
        eventBus.registerEventHandler(AddUserEvent.TYPE, new AddUserEventHandler() {

            @Override
            public void addUser(AddUserEvent event) {
                onAddUser(event.getUser());
            }
        });
        
        eventBus.registerEventHandler(AddFollowerEvent.TYPE, new AddFollowerEventHandler() {

            @Override
            public void addFollower(AddFollowerEvent event) {
                onAddFollower(event.getUserId(), event.getFollowerId());
            }
        });
        
        eventBus.registerEventHandler(AddFollowingEvent.TYPE, new AddFollowingEventHandler() {

            @Override
            public void addFollowing(AddFollowingEvent event) {
                onAddFollowing(event.getUserId(), event.getFollowingId());
            }
        });
    }
    
    private void onAddUser(User user){
        red.put(user, new LocalNetwork());
        //if(user instanceof User){
            // inserta un usuario en un nodo de la red
            //Red.insertaNodo(user);
            
      // System.out.println("se a añadido un nuevo usuario al grafo: "+user.getName());
        //}
    }
    
    private void onAddFollower(int user, int follower){
        List<User> users = new ArrayList<User>(red.keySet());
        User aux = new User(user, null);
        User aux2 = new User(follower, null);
        User u = users.get(users.indexOf(aux));
        User f = users.get(users.indexOf(aux2));
        
        red.get(u).followers.add(f);
        red.get(f).followings.add(u);


        //if(user instanceof User && follower instanceof User){
            // el grafo tiene pesos en los enlaces, le puse el tiempo para 
            //ponerle algo no mas, en realidad puede ser cualquier numero.
          //  Red.insertaEnlaceFollower( user, follower );
            //System.out.println("el usuario : " +user.getName() + " sigue al usuario :" +follower.getName());
            
       // }
        
    }
    
    private void onAddFollowing(int user, int following){
        
        List<User> users = new ArrayList<User>(red.keySet());
        User aux = new User(user, null);
        User aux2 = new User(following, null);
        User u = users.get(users.indexOf(aux));
        User f = users.get(users.indexOf(aux2));
        
        red.get(u).followings.add(f);
        red.get(f).followers.add(u);
        
        //if(user instanceof User && following instanceof User){
        //Red.insertaEnlaceFollowing(user, following );
        //System.out.println(" el usuario : " +user.getName() + " sigue al usuario: " +following.getName());
        //}
        
        
    }
    
}
