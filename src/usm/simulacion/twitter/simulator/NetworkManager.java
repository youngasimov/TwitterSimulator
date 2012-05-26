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
import usm.simulacion.twitter.core.SimulationEvent;
import usm.simulacion.twitter.simulator.Grafo;
import usm.simulacion.twitter.simulator.Grafo.Nodo;


/**
 * Esta clase contiene la información de toda la red,
 * ademas de determinar cuando una persona genera un nuevo
 * tweet o re-tweet
 * @author camilovera
 */
public class NetworkManager{
    
    private EventBus eventBus;
    private EventGeneratorAlgoritm algoritm;
    private ArrayList<User> users;
    private Map<String,String> textData;
    private Map<String,Integer> intData;
    private Map<String,Double> doubleData;
    private long time;
    private int nUsers;
    Grafo Red = new Grafo();  // se crea el grafo que contendra la red.
    
    
    public NetworkManager(EventBus eventBus,EventGeneratorAlgoritm algoritm){
        this.eventBus = eventBus;
        this.algoritm = algoritm;
        this.algoritm.setNetworkManager(this);
        users = new ArrayList<User>();
        bind();
    }
    
    public long getCurrentTime(){
        return time;
    }

    /**
     * lista de usuarios en la Red
     * @return
     */
    public ArrayList<User> getUsers(){
        users = Red.getLista();
        return users;
    }
    
    public User getUser(int id){

        Object b = Red.buscarConId(users, id);
        return (User)b;
    }
    
    public ArrayList getFollowers(User user){
        ArrayList followers = new ArrayList();
        Red.listaNodosFollower(user);
        return followers;
    }
    
    public ArrayList getFollowings(User user){
        ArrayList followings = new ArrayList();
        Red.listaNodosFollowing(user);
        return followings;
    }
    
    public void generateInitialRandomEvents(int events){
        
        //ejemplo
        Event e;
        for(int i=0;i<events;i++){
            e = algoritm.generateEvent(null);
            eventBus.fireEvent(new FutureEventEvent(time,algoritm.getDeltaTime(),e));
        }

    }
    
    public void generateEvent(Event procesedEvent){
        //ejemplo
        Event e = algoritm.generateEvent(procesedEvent);
        eventBus.fireEvent(new FutureEventEvent(time,algoritm.getDeltaTime(),e));
        if(time > 20){
            eventBus.fireEvent(new SimulationEvent(SimulationEvent.FINISH));
        }
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
    
     public int getnUsers() {
        nUsers = Red.getnNodo();
        return nUsers;
    } 
    
    private void bind(){
        eventBus.registerEventHandler(TimeEvent.TYPE, new TimeEventHandler() {

            @Override
            public void updateTime(long currentTime) {
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
                onAddFollower(event.getUserId() , event.getFollower() );
                onAddFollowing(event.getFollower(), event.getUserId() );
            }
        });

/*        eventBus.registerEventHandler(AddFollowingEvent.TYPE, new AddFollowingEventHandler() {

            @Override
            public void addFollowing(AddFollowingEvent event) {
                onAddFollowing(event.getFollower(), event.getUserId() );
            }
        }); */
    }
    
    private void onAddUser(User user ){
        if(user instanceof User){
            // inserta un usuario en un nodo de la red
            int id = user.getId();
            Red.insertaNodo(user, id);
            
      // System.out.println("se a añadido un nuevo usuario al grafo: "+user.getName());
            
        }
    }
    
    private void onAddFollower(int UserId , int FollowerId){
        //if(user instanceof User && follower instanceof User){
          Object a = Red.buscarConId(users, UserId);
          Object b = Red.buscarConId(users, FollowerId);
          
          Red.insertaEnlaceFollower(a,b );
            //System.out.println("el usuario : " +user.getName() + " sigue al usuario :" +follower.getName());
            return;
        //}
        
    }
    
    private void onAddFollowing(int UserId , int followingId){
        //if(user instanceof User && following instanceof User){
        Object a = Red.buscarConId(users, UserId);
          Object b = Red.buscarConId(users, followingId);
          
          Red.insertaEnlaceFollowing(a,b );
            //System.out.println("el usuario : " +user.getName() + " sigue al usuario :" +follower.getName());
            return;
        
        //System.out.println(" el usuario : " +user.getName() + " sigue al usuario: " +following.getName());
        }
        
        
    
    
}
