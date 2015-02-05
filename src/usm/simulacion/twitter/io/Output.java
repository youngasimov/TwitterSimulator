/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.core.SimulationEvent;
import usm.simulacion.twitter.core.SimulationEventHandler;
import usm.simulacion.twitter.simulator.*;

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
    private long largo;
    ConfiguratorReader lector = new ConfiguratorReader();
    List<Tweet> tweets;
    



    public Output(EventBus eventBus,NetworkManager networkManager) throws FileNotFoundException, IOException{
        this.eventBus = eventBus;
        this.networkManager = networkManager;
        finalizo = false;
        tweetCounter = 0;
        reTweetCounter = 0;
        bind();
        lector.cargarDatos();
        lector.cargarConfiguracion();
        tweets = new ArrayList<Tweet>();
    }

    private String getTime(){
        return "Time: "+networkManager.getCurrentTime()+"________>";
    }
    
    private void bind() throws FileNotFoundException, IOException{

        
        eventBus.registerEventHandler(AddUserEvent.TYPE, new AddUserEventHandler(){
            int filaCounter = 0;
            @Override
            public void addUser(AddUserEvent event) { 
                if(!finalizo){
                    filaCounter++;
                }
            }
        });
        
        eventBus.registerEventHandler(NewTweetEvent.TYPE, new NewTweetEventHandler() {
           
            
            @Override
            public void onNewTweet(NewTweetEvent event) {
                if(!finalizo){
                    tweetCounter++;
                    if(tweets.contains(event.getTweet())){
                        Tweet t = tweets.get(tweets.indexOf(event.getTweet()));
                        if(t.getSteps()<event.getTweet().getSteps()){
                            tweets.remove(tweets.indexOf(event.getTweet()));
                            tweets.add(event.getTweet());
                        }
                    }else{
                        tweets.add(event.getTweet());
                    }
                    //System.out.println(tweetCounter+") "+getTime()+"el usuario: "+event.getUser().getName()+" a generado un nuevo tweet con id: "+event.getTweet().getId());
                    
                }
            }
        });
        
        eventBus.registerEventHandler(NewReTweetEvent.TYPE, new NewReTweetEventHandler() {
            

            @Override
            public void onReTweet(NewReTweetEvent event) {
                if(!finalizo){
                    reTweetCounter++;
                    
                    if(tweets.contains(event.getTweet())){
                        Tweet t = tweets.get(tweets.indexOf(event.getTweet()));
                        if(t.getSteps()<event.getTweet().getSteps()){
                            tweets.remove(tweets.indexOf(event.getTweet()));
                            tweets.add(event.getTweet());
                        }
                    }else{
                        tweets.add(event.getTweet());
                    }
                    
                    //System.out.println(reTweetCounter+") "+getTime()+"el usuario: "+event.getUser().getName()+" a retuitiado un tweet con id: "+event.getTweet().getId()+" cuyo autor original es: "+event.getTweet().getOwner().getName());
                }
            }
        });
        
        eventBus.registerEventHandler(SimulationEvent.TYPE, new SimulationEventHandler() {
            @Override
            public void onSimulationEvent(SimulationEvent event) {
                if(event.getState() == SimulationEvent.FINISH){
                    try {
                        finalizo = true;
                        generateReport();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    
    private void generateReport() throws FileNotFoundException, IOException{

        List<User> users = networkManager.getUsers();
        /*List<Tweet> uTweets;
        List<Integer> counts = new ArrayList<Integer>(); 
        int count = 0;
        for(User u:users){
            uTweets = new ArrayList<Tweet>();
            uTweets.addAll(u.getIncomingTweets());
            uTweets.addAll(u.getOwnTweets());
            int i = 0;
            while(!uTweets.isEmpty()){
                Tweet t = uTweets.get(i);
                for(int j=i+1;j<uTweets.size();j++){
                    if(t.equals(uTweets.get(j))){
                        count++;
                        uTweets.remove(j);
                        j--;
                    }
                }
                uTweets.remove(i);
                counts.add(count);
                count = 0;
            }
        }
        
        */
        
/*
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
        */
        Map<Integer,Integer> counters = new HashMap<Integer, Integer>();
        

        for(Tweet tweet:tweets){
            if(counters.containsKey(tweet.getSteps())){
                counters.put(tweet.getSteps(),counters.get(tweet.getSteps())+1);
            }else{
                counters.put(tweet.getSteps(),1);
            }
        }
        
        
        
        OutputGlobal.plusCurrentSimulation();
        
        for(Integer key:counters.keySet()){
            OutputGlobal.addValue(key,(double)(counters.get(key)*100)/tweetCounter);
        }
        
        OutputGlobal.addValue3(tweetCounter, reTweetCounter, ((double)(tweetCounter+reTweetCounter))/(double)users.size(),(double)(((tweetCounter+reTweetCounter)/users.size())/networkManager.getCurrentTime()));
        
        tweets.clear();
        tweetCounter = 0;
        reTweetCounter = 0;
        finalizo = false;
    }
}