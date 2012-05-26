/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.core.SimulationEvent;
import usm.simulacion.twitter.core.SimulationEventHandler;
import usm.simulacion.twitter.simulator.AddUserEvent;
import usm.simulacion.twitter.simulator.AddUserEventHandler;
import usm.simulacion.twitter.simulator.NetworkManager;
import usm.simulacion.twitter.simulator.NewReTweetEvent;
import usm.simulacion.twitter.simulator.NewReTweetEventHandler;
import usm.simulacion.twitter.simulator.NewTweetEvent;
import usm.simulacion.twitter.simulator.NewTweetEventHandler;
import usm.simulacion.twitter.simulator.Tweet;
import usm.simulacion.twitter.simulator.User;

/**
 *
 * @author camilovera
 */
public class Output {
    
    private EventBus eventBus;
    private NetworkManager networkManager;
    private boolean finalizo;
    private long tweetCounter;
    private long reTweetCounter;
    
    public Output(EventBus eventBus,NetworkManager networkManager){
        this.eventBus = eventBus;
        this.networkManager = networkManager;
        finalizo = false;
        tweetCounter = 0;
        reTweetCounter = 0;
        bind();
    }
    
    private String getTime(){
        return "Time: "+networkManager.getCurrentTime()+"________>";
    }
    
    private void bind(){
        eventBus.registerEventHandler(AddUserEvent.TYPE, new AddUserEventHandler() {

            @Override
            public void addUser(AddUserEvent event) {
                if(!finalizo){
                    System.out.println(getTime()+"se a añadido un nuevo usuario: "+event.getUser().getName());
                }
            }
        });
        
        eventBus.registerEventHandler(NewTweetEvent.TYPE, new NewTweetEventHandler() {

            @Override
            public void onNewTweet(NewTweetEvent event) {
                if(!finalizo){
                    tweetCounter++;
                    System.out.println(tweetCounter+") "+getTime()+"el usuario: "+event.getUser().getName()+" a generado un nuevo tweet con id: "+event.getTweet().getId());
                }
            }
        });
        
        eventBus.registerEventHandler(NewReTweetEvent.TYPE, new NewReTweetEventHandler() {

            @Override
            public void onReTweet(NewReTweetEvent event) {
                if(!finalizo){
                    reTweetCounter++;
                    System.out.println(reTweetCounter+") "+getTime()+"el usuario: "+event.getUser().getName()+" a retuitiado un tweet con id: "+event.getTweet().getId()+" cuyo autor original es: "+event.getTweet().getOwner().getName());
                }
            }
        });
        
        eventBus.registerEventHandler(SimulationEvent.TYPE, new SimulationEventHandler() {

            @Override
            public void onSimulationEvent(SimulationEvent event) {
                if(event.getState() == SimulationEvent.FINISH){
                    finalizo = true;
                    generateReport();
                }
            }
        });
    }
    
    
    private void generateReport(){
        System.out.println();
        System.out.println();
        System.out.println("===============REPORTE===================");
        System.out.println();
        System.out.println("tweets nuevos generados: "+tweetCounter);
        System.out.println("reTweets generados: "+reTweetCounter);
        System.out.println("tweets totales: "+(tweetCounter+reTweetCounter));
        List<User> users = networkManager.getUsers();
        System.out.println("tweets promedio por usuario: "+((tweetCounter+reTweetCounter)/users.size()));
        System.out.println("tweets promedio por usuario por unidad de tiempo: "+((tweetCounter+reTweetCounter)/users.size())/networkManager.getCurrentTime());
        
        Map<Integer,Integer> counters = new HashMap<Integer, Integer>();
        for(User user:users){
            for(Tweet tweet:user.getIncomingTweets()){
                if(counters.containsKey(tweet.getSteps())){
                    counters.put(tweet.getSteps(),counters.get(tweet.getSteps())+1);
                }else{
                    counters.put(tweet.getSteps(),1);
                }
            }
        }
        System.out.println("--------Histograma------------");
        List<Integer> keys = new ArrayList<Integer>(counters.keySet());
        Collections.sort(keys);
        for(Integer key:keys){
            System.out.println(key+" saltos --------> "+counters.get(key));
        }
    }   
}