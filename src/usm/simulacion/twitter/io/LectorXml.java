package usm.simulacion.twitter.io;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import usm.simulacion.twitter.core.EventBus;
import usm.simulacion.twitter.simulator.AddFollowerEvent;
import usm.simulacion.twitter.simulator.AddUserEvent;
import usm.simulacion.twitter.simulator.User;

public class LectorXml {
    
    private Element e;

    public void cargarDocumento(String file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(file));
            doc.getDocumentElement().normalize();
            e = doc.getDocumentElement();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LeerUsuarios(EventBus bus){
        NodeList listaPersonas = e.getElementsByTagName("User");
        for (int i = 0; i < listaPersonas.getLength(); i ++) {

            Element persona = (Element)listaPersonas.item(i);
            String name = persona.getAttribute("name");
            String id = persona.getAttribute("id");
            User u = new User(Integer.decode(id),name);
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

}