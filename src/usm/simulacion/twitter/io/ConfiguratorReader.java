package usm.simulacion.twitter.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import usm.simulacion.twitter.probability.*;

public class ConfiguratorReader {
    
    private Element e;
    private NodeList usersConfiguration;
    private String reading;
    private List<String> firstNames;
    private List<String> lastNames;
    private List<String> marcas;
    private double seed;

    public void cargarDatos(){
        try{
            firstNames = new ArrayList<String>();
            lastNames = new ArrayList<String>();
            marcas = new ArrayList<String>();
            String file = "data.txt";
            File archivo = new File (file);
            FileReader fr = new FileReader (archivo);
            BufferedReader br = new BufferedReader(fr);
            String data;
            while(br.ready()){
                data = br.readLine();
                if(data.equals("First_Names:")){
                    reading = "first_names";
                }else if(data.equals("Last_Names:")){
                    reading = "last_names";
                }else if(data.equals("Marcas:")){
                    reading = "marcas";
                }else{
                    if(reading.equals("first_names")){
                        firstNames.add(data);
                    }else if(reading.equals("last_names")){
                        lastNames.add(data);
                    }else if(reading.equals("marcas")){
                        marcas.add(data);
                    }
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public List<String> getFirstNames() {
        return firstNames;
    }

    public List<String> getLastNames() {
        return lastNames;
    }

    public List<String> getMarcas() {
        return marcas;
    }
    
    
    public void cargarConfiguracion() {
        try {
            String file = "configuration.xml";
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(file));
            doc.getDocumentElement().normalize();
            e = doc.getDocumentElement();
            usersConfiguration = e.getElementsByTagName("Users");
            Node gseed = e.getElementsByTagName("globalSeed").item(0).getFirstChild();
            seed = Double.parseDouble(e.getElementsByTagName("globalSeed").item(0).getFirstChild().getNodeValue());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int getSimulationTime(){
        return Integer.decode(getTagValue("simulationTime",getTagElement("GlobalConfiguration", e)));
    }
    
    public double getGlobalSeed(){
        return seed;
    }
    
    public UserData getUserDataByType(String type){
        for(int i=0; i < usersConfiguration.getLength();i++){
            if(usersConfiguration.item(i).getAttributes().getNamedItem("type").getNodeValue().equals(type)){
                UserData ud = new UserData();
                Node user = usersConfiguration.item(i);
                if(user.getNodeType() == Node.ELEMENT_NODE){
                    Element eUser = (Element)user;
                    ud.setGenerate(Integer.decode(getTagValue("generate", eUser)));
                    ud.setFollowerWeight(Integer.decode(getTagValue("followers", eUser)));
                    
                    Element element = getTagElement("followings", eUser);
                    String func = element.getAttribute("function");
                    NodeList nl1 = element.getElementsByTagName("param");
                    Map<String,String> params = new HashMap<String,String>();
                    for(int x=0;x<nl1.getLength();x++){
                        Node node = nl1.item(x);
                        String key = node.getAttributes().getNamedItem("name").getNodeValue();
                        String value = node.getChildNodes().item(0).getNodeValue();
                        params.put(key, value);
                    }
                    ud.setFolowings(getFunction(func, params,seed));
                    
                    element = getTagElement("daysPerTweet", eUser);
                    func = element.getAttribute("function");
                    nl1 = element.getElementsByTagName("param");
                    params = new HashMap<String,String>();
                    for(int x=0;x<nl1.getLength();x++){
                        Node node = nl1.item(x);
                        String key = node.getAttributes().getNamedItem("name").getNodeValue();
                        String value = node.getChildNodes().item(0).getNodeValue();
                        params.put(key, value);
                    }
                    ud.setDaysPerTweet(getFunction(func, params,seed));
                    
                    element = getTagElement("tweetsPerRetweet", eUser);
                    func = element.getAttribute("function");
                    nl1 = element.getElementsByTagName("param");
                    params = new HashMap<String,String>();
                    for(int x=0;x<nl1.getLength();x++){
                        Node node = nl1.item(x);
                        String key = node.getAttributes().getNamedItem("name").getNodeValue();
                        String value = node.getChildNodes().item(0).getNodeValue();
                        params.put(key, value);
                    }
                    ud.setTweetsPerRetweet(getFunction(func, params,seed));
                    
                    element = getTagElement("tweetsPerRetweeted", eUser);
                    func = element.getAttribute("function");
                    nl1 = element.getElementsByTagName("param");
                    params = new HashMap<String,String>();
                    for(int x=0;x<nl1.getLength();x++){
                        Node node = nl1.item(x);
                        String key = node.getAttributes().getNamedItem("name").getNodeValue();
                        String value = node.getChildNodes().item(0).getNodeValue();
                        params.put(key, value);
                    }
                    ud.setTweetsPerRetweeted(getFunction(func, params,seed));
                    
                    element = getTagElement("eventGenerator", eUser);
                    func = element.getAttribute("function");
                    nl1 = element.getElementsByTagName("param");
                    params = new HashMap<String,String>();
                    for(int x=0;x<nl1.getLength();x++){
                        Node node = nl1.item(x);
                        String key = node.getAttributes().getNamedItem("name").getNodeValue();
                        String value = node.getChildNodes().item(0).getNodeValue();
                        params.put(key, value);
                    }
                    ud.setEventGenerator(func);
                    ud.setParamsOfEventGenerator(params);
                    
                }
                return ud;
            }
        }
        return null;
        
    }
    
    
    public static ProbabilisticFunction getFunction(String name, Map<String,String> params,double seed){
        if(name.equals("Normal")){
            return new Normal(Double.parseDouble(params.get("mean")), Double.parseDouble(params.get("standartDeviation")),seed);
        }else if(name.equals("Beta")){
            return new Beta(Double.parseDouble(params.get("a")), Double.parseDouble(params.get("b")),seed);
        }else if(name.equals("Weibull")){
            return new Weibull(Double.parseDouble(params.get("k")), Double.parseDouble(params.get("lambda")),seed);
        }else if(name.equals("Exponential")){
            return new Exponential(Double.parseDouble(params.get("mean")),seed);
        }else if(name.equals("Uniform")){
            return new ComplexUniform((long)seed);
        }else{
            return null;
        }
    }
    
    private String getTagValue(String tag, Element elemento) {
        NodeList lista = elemento.getElementsByTagName(tag).item(0).getChildNodes();
        Node valor = (Node) lista.item(0);
        return valor.getNodeValue();
    }
    
    private Element getTagElement(String tag, Element elemento) {
        Node valor = elemento.getElementsByTagName(tag).item(0);
        //Node valor = (Node) lista.item(0);
        if(valor.getNodeType() == Node.ELEMENT_NODE){
            return (Element)valor;
        }else{
            return null;
        }
    }
    
    /*
     public void LeerUsuarios(EventBus bus){
        NodeList listaPersonas = e.getElementsByTagName("User");
        for (int i = 0; i < listaPersonas.getLength(); i ++) {

            Element persona = (Element)listaPersonas.item(i);
            String name = persona.getAttribute("name");
            String id = persona.getAttribute("id");
            String perfil = persona.getAttribute("tipo");
            User u = new User(Integer.decode(id),name,perfil);
            bus.fireEvent(new AddUserEvent(u));
        }

    }
    
    public void leerFollowers(EventBus bus){
        NodeList listaPersonas = e.getElementsByTagName("follow");
        for (int i = 0; i < listaPersonas.getLength(); i ++) {
            Element persona = (Element)listaPersonas.item(i);
            String userId = persona.getAttribute("userid");
            String followerid = persona.getAttribute("Follower");
            bus.fireEvent(new AddFollowerEvent(Integer.decode(userId), Integer.decode(followerid)));
        }
    }
    
    public void leerParameters(NetworkManager networkmanager){
        NodeList listaParameters = e.getElementsByTagName("Parameter");
        for (int i = 0; i < listaParameters.getLength(); i ++) {
            Element Parameter = (Element)listaParameters.item(i);
            String name = Parameter.getAttribute("name");
            String valor = Parameter.getAttribute("valor");
            networkmanager.addParam(name, Double.parseDouble(valor));



        }
    }
    * 
    */

}