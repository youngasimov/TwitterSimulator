/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import usm.simulacion.twitter.core.EventBus;

/**
 *
 * @author camilovera
 */
public class EventsManager {


    
    private EventBus eventBus;
    private NetworkManager networkManager;
    private int CountTweets;   // contador global de tweets
    private int CountReTweets;  // contador global de retweets
    public EventsManager(EventBus eventBus,NetworkManager networkManager){
        this.eventBus = eventBus;
        this.networkManager = networkManager;
        bind();
    }
    
    private void bind(){




        this.eventBus.registerEventHandler(NewTweetEvent.TYPE, new NewTweetEventHandler() {

            @Override
            public void onNewTweet(NewTweetEvent event) {
                //EventsManager.this.onAddNewReTweet(event.getUser(),event.getTweet());
                onAddNewTweet(event.getUser(), event.getTweet() );
               // EventsManager.this.onNewTweet(event);

            }


        });
        
        this.eventBus.registerEventHandler(NewReTweetEvent.TYPE, new NewReTweetEventHandler() {

            @Override
            public void onReTweet(NewReTweetEvent event) {
                //EventsManager.this.onAddNewReTweet(event.getUser(),event.getTweet());
                onAddNewReTweet(event.getUser(), event.getTweet() );
               
            }


        });
    }
    
   
   
     private void onAddNewTweet(User user, Tweet tweet) {

         user.addOwnTweet(tweet); // agrega tweet a la lista en usuario
         CountTweets++;



            }
     private void onAddNewReTweet(User user, Tweet reTweet) {

         user.addOwnTweet(reTweet); // agrega retweet a la lista en usuario
         CountReTweets++;
                
            }

     public int getCountReTweets() {
        return CountReTweets;
    }

    public int getCountTweets() {
        return CountTweets;
    }

    // porcentaje promedio de retweets en la red
    public double GetMetrica(){
        double promedio = (CountReTweets/CountTweets)*100;
        return promedio;

    }



}
