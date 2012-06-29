package usm.simulacion.twitter;

import java.util.*;
import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.core.EventStackManager;
import usm.simulacion.twitter.core.FutureEventEvent;
import usm.simulacion.twitter.core.SimulationEvent;
import usm.simulacion.twitter.io.ConfiguratorReader;
import usm.simulacion.twitter.io.Output;
import usm.simulacion.twitter.io.UserData;
import usm.simulacion.twitter.probability.ComplexUniform;
import usm.simulacion.twitter.simulator.*;

/**
 *
 * @author camilovera
 */
public class MainSimulator {
    
    public static ComplexUniform random;
    
    public static void main(String[] args){
        try{
            double minRetwitteredEveryXTweets = Double.MAX_VALUE;
            double maxRetwitterEveryXTweets = 0;
            EventBus bus = new EventBus();
            ConfiguratorReader lector = new ConfiguratorReader();
            lector.cargarDatos();
            lector.cargarConfiguracion();

            random = new ComplexUniform((long)lector.getGlobalSeed());
            
            UserData udCommon = lector.getUserDataByType(User.TYPE_COMMON);

            UserData udCelebrity = lector.getUserDataByType(User.TYPE_CELEBRITY);

            UserData udCompany = lector.getUserDataByType(User.TYPE_COMPANY);


            NetworkManager networkManager = new NetworkManager(bus,(double)lector.getSimulationTime());
            Output output = new Output(bus, networkManager);
            EventStackManager stackManager = new EventStackManager(bus, new ComparatorBySimulationTime());
            TweetsManager tweetsManager = new TweetsManager(bus,networkManager);

            User u;
            int id = 0;
            
            //creación de usuarios normales
            for(int i=0;i<udCommon.getGenerate();i++,id++){
                String temp = nameGenerator(lector);
                u = new User(id, nameGenerator(lector), User.TYPE_COMMON);
                u.setDaysPerTweet(udCommon.getDaysPerTweet().nextDuble());
                double retweet = udCommon.getTweetsPerRetweet().nextDuble();
                if(retweet>maxRetwitterEveryXTweets){
                    maxRetwitterEveryXTweets = retweet;
                }
                u.setRetweetEveryXTweets(retweet);
                double retweeted = udCommon.getTweetsPerRetweeted().nextDuble();
                if(retweeted<minRetwitteredEveryXTweets){
                    minRetwitteredEveryXTweets = retweeted;
                }
                u.setRetweetedEveryXTweets(retweeted);
                String func = udCommon.getEventGenerator();
                Map<String,String> params = new HashMap<String, String>();
                for(Map.Entry<String,String> entry:udCommon.getParamsOfEventGenerator().entrySet()){
                    double value = Double.parseDouble(entry.getValue());
                    //params.put(entry.getKey(), ""+value*u.getDaysPerTweet());
                    if(entry.getKey().equals("mean")){
                        params.put(entry.getKey(), ""+u.getDaysPerTweet());
                    }else{
                        params.put(entry.getKey(), ""+value);
                    }
                }
                u.setNextEventTimeGenerator(ConfiguratorReader.getFunction(func, params,lector.getGlobalSeed()));
                bus.fireEvent(new AddUserEvent(u));
            }
            
            //creación de usuarios famosos
            for(int i=0;i<udCelebrity.getGenerate();i++,id++){
                u = new User(id, nameGenerator(lector), User.TYPE_CELEBRITY);
                u.setDaysPerTweet(udCelebrity.getDaysPerTweet().nextDuble());
                double retweet = udCelebrity.getTweetsPerRetweet().nextDuble();
                if(retweet>maxRetwitterEveryXTweets){
                    maxRetwitterEveryXTweets = retweet;
                }
                u.setRetweetEveryXTweets(retweet);
                double retweeted = udCelebrity.getTweetsPerRetweeted().nextDuble();
                if(retweeted<minRetwitteredEveryXTweets){
                    minRetwitteredEveryXTweets = retweeted;
                }
                u.setRetweetedEveryXTweets(retweeted);
                String func = udCelebrity.getEventGenerator();
                Map<String,String> params = new HashMap<String, String>();
                for(Map.Entry<String,String> entry:udCelebrity.getParamsOfEventGenerator().entrySet()){
                    double value = Double.parseDouble(entry.getValue());
                    //params.put(entry.getKey(), ""+value*u.getDaysPerTweet());
                    if(entry.getKey().equals("mean")){
                        params.put(entry.getKey(), ""+u.getDaysPerTweet());
                    }else{
                        params.put(entry.getKey(), ""+value);
                    }
                }
                u.setNextEventTimeGenerator(ConfiguratorReader.getFunction(func, params,lector.getGlobalSeed()));
                bus.fireEvent(new AddUserEvent(u));
            }
            
            //creación de usuarios empresa
            for(int i=0;i<udCompany.getGenerate();i++,id++){
                int position = random.nextInt()%lector.getMarcas().size();
                u = new User(id, lector.getMarcas().get(position), User.TYPE_COMPANY);
                lector.getMarcas().remove(position);
                u.setDaysPerTweet(udCompany.getDaysPerTweet().nextDuble());
                double retweet = udCompany.getTweetsPerRetweet().nextDuble();
                if(retweet>maxRetwitterEveryXTweets){
                    maxRetwitterEveryXTweets = retweet;
                }
                u.setRetweetEveryXTweets(retweet);
                double retweeted = udCompany.getTweetsPerRetweeted().nextDuble();
                if(retweeted<minRetwitteredEveryXTweets){
                    minRetwitteredEveryXTweets = retweeted;
                }
                u.setRetweetedEveryXTweets(retweeted);
                String func = udCompany.getEventGenerator();
                Map<String,String> params = new HashMap<String, String>();
                for(Map.Entry<String,String> entry:udCompany.getParamsOfEventGenerator().entrySet()){
                    double value = Double.parseDouble(entry.getValue());
                    //params.put(entry.getKey(), ""+value*u.getDaysPerTweet());
                    if(entry.getKey().equals("mean")){
                        params.put(entry.getKey(), ""+u.getDaysPerTweet());
                    }else{
                        params.put(entry.getKey(), ""+value);
                    }
                }
                u.setNextEventTimeGenerator(ConfiguratorReader.getFunction(func, params,lector.getGlobalSeed()));
                bus.fireEvent(new AddUserEvent(u));
            }
            
            
            networkManager.setMaxRetwitterEveryXTweets(maxRetwitterEveryXTweets);
            networkManager.setMinRetwitteredEveryXTweets(minRetwitteredEveryXTweets);
            
            //creación de viculos entre usuarios
            double maxWeight = minRetwitteredEveryXTweets*100;
            Random r = new Random((long)lector.getGlobalSeed());
            for(User user:networkManager.getUsers()){
                int following = 20;
                
                int auxFollowings = 0;
                if(user.getType().equals(User.TYPE_COMMON)){
                    following = udCommon.getFolowings().nextInt();
                }else if(user.getType().equals(User.TYPE_CELEBRITY)){
                    following = udCelebrity.getFolowings().nextInt();
                }else if(user.getType().equals(User.TYPE_COMPANY)){
                    following = udCompany.getFolowings().nextInt();
                }
                
                
                
                List<User> users = new ArrayList<User>(networkManager.getUsers());
                Collections.shuffle(users, r);
                //las empresas no siguen otras empresas
                if(user.getType().equals(User.TYPE_COMPANY)){
                    int h = 0;
                    User u2;
                    while(auxFollowings<following){
                        u2 = users.get(h%users.size());
                        if(u2.getType().equals(User.TYPE_CELEBRITY) || u2.getType().equals(User.TYPE_COMMON)){
                            double weight = (1/u2.getRetweetedEveryXTweets())*udCompany.getFollowerWeight();
                            double aux = random.nextDuble();
                            if(weight/maxWeight > (aux - (int)aux) && auxFollowings<following){
                                bus.fireEvent(new AddFollowingEvent(user.getId(), u2.getId()));
                                auxFollowings++;
                            }
                        }
                        h++;
                    }
                }
                //los famosos siguen a famosos, empresas y usuarios comunes
                else if(user.getType().equals(User.TYPE_CELEBRITY)){
                    int h = 0;
                    User u2;
                    while(auxFollowings<following){
                        u2 = users.get(h%users.size());
                        double weight = (1/u2.getRetweetedEveryXTweets())*udCelebrity.getFollowerWeight();
                        double aux = random.nextDuble();
                        if(weight/maxWeight > (aux - (int)aux) && auxFollowings<following){
                            bus.fireEvent(new AddFollowingEvent(user.getId(), u2.getId()));
                            auxFollowings++;
                        }
                        h++;
                    }
                }
                // para los suarios comunes, la mitad de los que siguen deben ser otros usuarios comunes
                else if(user.getType().equals(User.TYPE_COMMON)){
                    int h = 0;
                    User u2;
                    int commonfollowing = following/2;
                    int auxCommonFollowings = 0;
                    while(auxFollowings<following){
                        u2 = users.get(h%users.size());
                        if(u2.getType().equals(User.TYPE_COMPANY) || u2.getType().equals(User.TYPE_CELEBRITY)){
                            double weight = (1/u2.getRetweetedEveryXTweets())*udCommon.getFollowerWeight();
                            double aux = random.nextDuble();
                            if(weight/maxWeight > (aux - (int)aux) && auxFollowings-auxCommonFollowings<following-commonfollowing){
                                bus.fireEvent(new AddFollowingEvent(user.getId(), u2.getId()));
                                auxFollowings++;
                            }
                        }else{
                            double weight = (1/u2.getRetweetedEveryXTweets())*udCommon.getFollowerWeight();
                            double aux = random.nextDuble();
                            if(weight/maxWeight > (aux - (int)aux) && auxCommonFollowings<commonfollowing){
                                bus.fireEvent(new AddFollowingEvent(user.getId(), u2.getId()));
                                auxCommonFollowings++;
                                auxFollowings++;
                            }
                        }
                        
                        h++;
                    }
                }
            }
            
            
            
            bus.fireEvent(new FutureEventEvent((double)lector.getSimulationTime(), 0.0, new SimulationEvent(SimulationEvent.FINISH)));
            bus.fireEvent(new SimulationEvent(SimulationEvent.START));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static String nameGenerator(ConfiguratorReader lector){
        int size = lector.getFirstNames().size();
        
        int r = random.nextInt();
        
        String name = lector.getFirstNames().get(random.nextInt()%size);
        name = name+" "+lector.getFirstNames().get(random.nextInt()%size);
        size = lector.getLastNames().size();
        name = name+" "+lector.getLastNames().get(random.nextInt()%size);
        name = name+" "+lector.getLastNames().get(random.nextInt()%size);
        return name;
    }
    
}
