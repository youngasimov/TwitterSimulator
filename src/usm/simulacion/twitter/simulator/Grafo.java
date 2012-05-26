/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package usm.simulacion.twitter.simulator;
import usm.simulacion.twitter.simulator.ListaSimpleEnlazada.ListaUtil;
import java.util.ArrayList;
import java.util.Iterator;
import usm.simulacion.twitter.simulator.Grafo.Nodo;


/**
 *
 * @author Herbyn
 */
public class Grafo {

   private ArrayList<Nodo> users; 
   private int nNodo;   // contador de cantidad de Nodos 

    class Nodo {
       
	Object elemento;
        ArrayList<Nodo> listaEnlacesFollower;
        ArrayList<Nodo> listaEnlacesFollowing;
	int id;
        int ContFollowers;
        int ContFollowins;
   
	
        
	public Nodo(Object elemento) {
	   this.elemento = elemento;
           listaEnlacesFollower = new ArrayList();
           listaEnlacesFollowing = new ArrayList();
    	   id = 0;
           
	   
	}

        public boolean equals(Object nodo) {
	   if (nodo==null) return false;
	   Nodo n = (Nodo)nodo;
	   return elemento.equals(n.elemento);
	}
       
        public int getId() {
            return id;
        }

        public ArrayList getlistaEnlacesFollower() {
            return listaEnlacesFollower;
        }

        public ArrayList getlistaEnlacesFollowing() {
            return listaEnlacesFollowing;
        }
   }
	
   // Contruye un nuevo grafo
    public Grafo() {

    users = new ArrayList();
    // lista = new Lista();  antiguo
    nNodo = 0;

   }
   // Inserta un nuevo nodo en el grafo con el elemento dado
    public void insertaNodo(Object elemento3, int idUser) {
    	
    	Nodo nodo = new Nodo(elemento3);
        nodo.id = idUser;
        users.add(nodo);
        nNodo++;
        
   }
   // Inserta un enlace entre los nodos con elemento1 y elemento2
    public void insertaEnlaceFollower(Object elemento1, Object elemento2){

           Nodo d = new Nodo(elemento1);
           Nodo c = new Nodo(elemento2);
	   d =(Nodo) ListaUtil.buscar(users,new Nodo(elemento1));
           d.listaEnlacesFollower.add(c);
           d.ContFollowers++; // cuenta cantidad de followers
	 
   }



    public void insertaEnlaceFollowing(Object elemento1, Object elemento2){

         Nodo d = new Nodo(elemento1);
           Nodo c = new Nodo(elemento2);
	   d =(Nodo) ListaUtil.buscar(users,new Nodo(elemento1));
           d.listaEnlacesFollowing.add(c);
           d.ContFollowins++;  // cuenta cantidad de followings

    }
   
   // Devuelve un booleano que indica si el grafo es vacio
    public  boolean esVacio(){
    	return (users.isEmpty());
   }
   // Devuelve un booleano que indica si el grafo contiene 
   // un nodo con elemento
    public boolean contieneNodo(Object elemento) {
    	Object p = ListaUtil.buscar(users,new Nodo(elemento));
	if (p==null) return false;
    	return true;
   }

   // Devuelve un booleano que indica si el grafo contiene un enlace 
   // entre los nodos con elemento1 y elemento2
    public boolean contieneEnlace(Object elemento1, Object elemento2) {
    	
	   Object p = ListaUtil.buscar(users,new Nodo(elemento1));
	   if (p==null) return false;
	   return true;
	
   }
  
   // Devuelve la lista de nodos
    public ArrayList listaNodosFollower(Object elemento) {
      ArrayList l = new ArrayList();
      Nodo d = new Nodo(elemento);
      d =(Nodo) ListaUtil.buscar(users,new Nodo(elemento));

      l= d.getlistaEnlacesFollower();
      
	   return l;
	
   }

    public ArrayList listaNodosFollowing(Object elemento) {
      ArrayList l = new ArrayList();
      Nodo d = new Nodo(elemento);
      d =(Nodo) ListaUtil.buscar(users,new Nodo(elemento));

      l= d.getlistaEnlacesFollower();
      
        return l;

   }
  
    public Object buscarConId(ArrayList lista, int userId) {
       
       for ( Iterator iterador = lista.listIterator(); iterador.hasNext(); ){
                Nodo b = (Nodo) iterador.next();
                if(b.getId() == userId)
                return b;

                             }
              return null;
  }

   
    public ArrayList getLista() {
        return users;
    }

    public int getnNodo() {
        return nNodo;
    }

   
   
   
} 

