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


/**
 * Esta clase contiene la información de toda la red,
 * ademas de determinar cuando una persona genera un nuevo
 * tweet o re-tweet
 * @author camilovera
 */
public class NetworkManager{
    
    private EventBus eventBus;
    private EventGeneratorAlgoritm algoritm;
    private List users;
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
    
    public List<User> getUsers(){
       
        return users;
    }
    
    public User getUser(int id){
        return null;
    }
    
    public List<User> getFollowers(User user){
        return null;
    }
    
    public List<User> getFollowings(User user){
        return null;
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
        System.out.println("El Numero de Usuarios en la Red es :" + Red.getnNodo());
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
                onAddFollower(event.getUser(), event.getFollower());
            }
        });
        
        eventBus.registerEventHandler(AddFollowingEvent.TYPE, new AddFollowingEventHandler() {

            @Override
            public void addFollowing(AddFollowingEvent event) {
                onAddFollowing(event.getUser(), event.getFollowing());
            }
        });
    }
    
    private void onAddUser(User user){
        if(user instanceof User){
            // inserta un usuario en un nodo de la red
            Red.insertaNodo(user, user.getId());
            
       System.out.println("se a añadido un nuevo usuario al grafo: "+user.getName());
            
        }
    }
    
    private void onAddFollower(User user, User follower){
        if(user instanceof User && follower instanceof User){
            // el grafo tiene pesos en los enlaces, le puse el tiempo para 
            //ponerle algo no mas, en realidad puede ser cualquier numero.
            Red.insertaEnlace( user, follower, time);
            System.out.println("el usuario : " +user.getName() + " sigue al usuario :" +follower.getName());
            
        }
        
    }
    
    private void onAddFollowing(User user, User following){
        if(user instanceof User && following instanceof User){
        Red.insertaEnlace(following, user, time);
        System.out.println(" el usuario : " +user.getName() + " sigue al usuario: " +following.getName());
        }
        
        
    }
    
}