package usm.simulacion.twitter.io;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

public class LectorXml {

  public void cargarDocumento(String file) {
      

        try {
	  DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	  DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	  Document doc = dBuilder.parse(new File(file));
	  doc.getDocumentElement().normalize();
          Element e = doc.getDocumentElement();
          NodeList listaPersonas = doc.getElementsByTagName("user");
          
        }   catch (Exception e) {
                e.printStackTrace();
            }
  }
	  
  public void LeerDatos(){
      
          //System.out.println("El elemento raíz es: " + doc.getDocumentElement().getNodeName());
            //NodeList listaPersonas = doc.getElementsByTagName("user");

	  for (int i = 0; i < listaPersonas.getLength(); i ++) {

	    Element persona = (Element)listaPersonas.item(i);
            persona.getAttribute("name");
            persona.getAttribute("id");
            
            NamedNodeMap nnm = persona.getAttributes();
            
	    if (persona.getNodeType() == Node.ELEMENT_NODE) {

            Element elemento = (Element) persona;

            System.out.println("Nombre : " + getTagValue("nombre", elemento));
            System.out.println("Apellido : " + getTagValue("apellido", elemento));
            System.out.println("Edad : " + getTagValue("edad", elemento));

	    }
          }
      
  }
  
  /*catch (Exception e) {
    e.printStackTrace();*/
} 
  finally{
      
  }

 

 private static String getTagValue(String sTag, Element eElement)
 {
	  NodeList nlList= eElement.getElementsByTagName(sTag).item(0).getChildNodes();
	  Node nValue = (Node) nlList.item(0);

	  return nValue.getNodeValue();

 }

}