package usm.simulacion.twitter;

import java.util.*;
import usm.simulacion.twitter.core.*;
import usm.simulacion.twitter.io.ConfiguratorReader;
import usm.simulacion.twitter.io.OutputGlobal;
import usm.simulacion.twitter.io.UserData;
import usm.simulacion.twitter.probability.ComplexUniform;
import usm.simulacion.twitter.simulator.*;

/**
 *
 * @author camilovera
 */
public class MainSimulator{
    
    public static ComplexUniform random;
    private static double maxRetwitterEveryXTweets;
    private static double minRetwitteredEveryXTweets;
    private static int simulationsByCore;
    private static int finishedTasks;
    
    public static void main(String[] args){
        
        try{
            finishedTasks = 0;
            //minRetwitteredEveryXTweets = Double.MAX_VALUE;
            //maxRetwitterEveryXTweets = 0;
            maxRetwitterEveryXTweets=0;
            minRetwitteredEveryXTweets = Double.MAX_VALUE;
            final ConfiguratorReader lector = new ConfiguratorReader();
            lector.cargarDatos();
            lector.cargarConfiguracion();
            
            List<Task> hilos = new ArrayList<Task>();
            List<EventBus> buses = new ArrayList<EventBus>();
            simulationsByCore = lector.getSimulations()/lector.getCores();
            for(int i=0;i<lector.getCores();i++){
                EventBus eb = new EventBus();
                buses.add(eb);
                hilos.add(new Task(simulationsByCore,lector,eb));
            }
            
            

            random = new ComplexUniform((long)lector.getGlobalSeed());
            
            UserData udCommon = lector.getUserDataByType(User.TYPE_COMMON);

            UserData udCelebrity = lector.getUserDataByType(User.TYPE_CELEBRITY);

            UserData udCompany = lector.getUserDataByType(User.TYPE_COMPANY);


            //NetworkManager networkManager = new NetworkManager(bus,(double)lector.getSimulationTime());
            //Output output = new Output(bus, networkManager);
            //EventStackManager stackManager = new EventStackManager(bus, new ComparatorBySimulationTime());
            //TweetsManager tweetsManager = new TweetsManager(bus,networkManager);

            User u;
            int id = 0;
            List<User> usuarios = new ArrayList<User>();
            //creación de usuarios normales
            for(int i=0;i<udCommon.getGenerate();i++,id++){
                u = new User(id, nameGenerator(lector), User.TYPE_COMMON);
                u = createUser(u, udCommon, lector);
                usuarios.add(u);
                for(int x=0; x<buses.size(); x++){
                    buses.get(x).fireEvent(new AddUserEvent(u.clone()));
                }
            }
            
            //creación de usuarios famosos
            for(int i=0;i<udCelebrity.getGenerate();i++,id++){
                u = new User(id, nameGenerator(lector), User.TYPE_CELEBRITY);
                u = createUser(u, udCelebrity, lector);
                usuarios.add(u);
                for(int x=0; x<buses.size(); x++){
                    buses.get(x).fireEvent(new AddUserEvent(u.clone()));
                }
            }
            
            //creación de usuarios empresa
            for(int i=0;i<udCompany.getGenerate();i++,id++){
                int position = random.nextInt()%lector.getMarcas().size();
                u = new User(id, lector.getMarcas().get(position), User.TYPE_COMPANY);
                lector.getMarcas().remove(position);
                u = createUser(u, udCompany, lector);
                usuarios.add(u);
                for(int x=0; x<buses.size(); x++){
                    buses.get(x).fireEvent(new AddUserEvent(u.clone()));
                }
            }
            for(int i=0; i < lector.getCores(); i++){
                hilos.get(i).setMaxRetwitterEveryXTweets(maxRetwitterEveryXTweets);
                hilos.get(i).setMinRetwitteredEveryXTweets(minRetwitteredEveryXTweets);
            }
            //networkManager.setMaxRetwitterEveryXTweets(maxRetwitterEveryXTweets);
            //networkManager.setMinRetwitteredEveryXTweets(minRetwitteredEveryXTweets);
            
            //creación de viculos entre usuarios
            double maxWeight = (1/minRetwitteredEveryXTweets)*100;
            Random r = new Random((long)lector.getGlobalSeed());
            for(User user:usuarios){
                int following = 20;
                
                int auxFollowings = 0;
                if(user.getType().equals(User.TYPE_COMMON)){
                    following = udCommon.getFolowings().nextInt();
                }else if(user.getType().equals(User.TYPE_CELEBRITY)){
                    following = udCelebrity.getFolowings().nextInt();
                }else if(user.getType().equals(User.TYPE_COMPANY)){
                    following = udCompany.getFolowings().nextInt();
                }
                following = (following<0)?0:following;
                
                
                
                List<User> users = new ArrayList<User>(usuarios);
                users.remove(user);
                Collections.shuffle(users, r);
                //las empresas no siguen otras empresas
                if(user.getType().equals(User.TYPE_COMPANY)){
                    int h = 0;
                    User u2;
                    while(auxFollowings<following){
                        u2 = users.get(h%(users.size()-auxFollowings-1));
                        if(u2.getType().equals(User.TYPE_CELEBRITY) || u2.getType().equals(User.TYPE_COMMON)){
                            double weight = (1/u2.getRetweetedEveryXTweets())*udCompany.getFollowerWeight();
                            double aux = random.nextDuble();
                            if(weight/maxWeight > (aux - (int)aux) && auxFollowings<following){
                                for(int x=0; x<buses.size(); x++){
                                    buses.get(x).fireEvent(new AddFollowingEvent(user.getId(), u2.getId()));
                                }
                                users.remove(u2);
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
                        u2 =  users.get(h%(users.size()-auxFollowings-1));
                        double weight = (1/u2.getRetweetedEveryXTweets())*udCelebrity.getFollowerWeight();
                        double aux = random.nextDuble();
                        if(weight/maxWeight > (aux - (int)aux) && auxFollowings<following){
                            for(int x=0; x<buses.size(); x++){
                                buses.get(x).fireEvent(new AddFollowingEvent(user.getId(), u2.getId()));
                            }
                            users.remove(u2);
                            auxFollowings++;
                        }
                        h++;
                    }
                }
                // para los usarios comunes, la mitad de los que siguen deben ser otros usuarios comunes
                else if(user.getType().equals(User.TYPE_COMMON)){
                    int h = 0;
                    User u2;
                    int commonfollowing = following/2;
                    int auxCommonFollowings = 0;
                    while(auxFollowings<following){
                        u2 = users.get(h%(users.size()-auxFollowings-1));
                        if(u2.getType().equals(User.TYPE_COMPANY) || u2.getType().equals(User.TYPE_CELEBRITY)){
                            double weight = (1/u2.getRetweetedEveryXTweets())*udCommon.getFollowerWeight();
                            double aux = random.nextDuble();
                            double aux2 = (aux - (int)aux);
                            if(weight/maxWeight > (aux - (int)aux) && auxFollowings-auxCommonFollowings<following-commonfollowing){
                                for(int x=0; x<buses.size(); x++){
                                    buses.get(x).fireEvent(new AddFollowingEvent(user.getId(), u2.getId()));
                                }
                                users.remove(u2);
                                auxFollowings++;
                            }
                        }else{
                            double weight = (1/u2.getRetweetedEveryXTweets())*udCommon.getFollowerWeight();
                            double aux = random.nextDuble();
                            double aux2 = (aux - (int)aux);
                            if(weight/maxWeight > (aux - (int)aux) && auxCommonFollowings<commonfollowing){
                                for(int x=0; x<buses.size(); x++){
                                    buses.get(x).fireEvent(new AddFollowingEvent(user.getId(), u2.getId()));
                                }
                                users.remove(u2);
                                auxCommonFollowings++;
                                auxFollowings++;
                            }
                        }
                        
                        h++;
                        if(h>users.size()*10){
                            h=0;
                        }
                    }
                }
            }
            
            OutputGlobal.generateNetwork(hilos.get(0).getNetworkManager());
            
            
            for(int i=0;i<lector.getCores();i++){
                hilos.get(i).setFinishHandler(new Task.FinishHandler() {

                    @Override
                    public synchronized void onFinish() {
                        finishedTasks++;
                        if(finishedTasks == lector.getCores()){
                            OutputGlobal.generateReport();
                        }
                    }
                });
                hilos.get(i).start();
            }
            
            
            //bus.fireEvent(new FutureEventEvent((double)lector.getSimulationTime(), 0.0, new SimulationEvent(SimulationEvent.FINISH)));
            //bus.fireEvent(new SimulationEvent(SimulationEvent.START));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private static String nameGenerator(ConfiguratorReader lector){
        int size = lector.getFirstNames().size();
        
        int r = (int)(random.nextDuble()*size);
        
        String name = lector.getFirstNames().get((int)(random.nextDuble()*size));
        name = name+" "+lector.getFirstNames().get((int)(random.nextDuble()*size));
        size = lector.getLastNames().size();
        name = name+" "+lector.getLastNames().get((int)(random.nextDuble()*size));
        name = name+" "+lector.getLastNames().get((int)(random.nextDuble()*size));
        return name;
    }
    
    private static User createUser(User u, UserData data,ConfiguratorReader lector){
        u.setDaysPerTweet(Math.abs(data.getDaysPerTweet().nextDuble()));
        double retweet = Math.abs(data.getTweetsPerRetweet().nextDuble());
        if(retweet>maxRetwitterEveryXTweets){
            maxRetwitterEveryXTweets = retweet;
        }
        u.setRetweetEveryXTweets(retweet);
        double retweeted = Math.abs(data.getTweetsPerRetweeted().nextDuble());
        if(retweeted<minRetwitteredEveryXTweets){
            minRetwitteredEveryXTweets = retweeted;
        }
        u.setRetweetedEveryXTweets(retweeted);
        String func = data.getEventGenerator();
        Map<String,String> params = new HashMap<String, String>();
        for(Map.Entry<String,String> entry:data.getParamsOfEventGenerator().entrySet()){
            double value = Double.parseDouble(entry.getValue());
            //params.put(entry.getKey(), ""+value*u.getDaysPerTweet());
            if(entry.getKey().equals("mean")){
                params.put(entry.getKey(), ""+u.getDaysPerTweet());
            }else{
                params.put(entry.getKey(), ""+value);
            }
        }
        u.setNextEventTimeGenerator(ConfiguratorReader.getFunction(func, params,(long)(random.nextDuble()*10000)));
        return u;
    }
    
}
