/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package usm.simulacion.twitter.simulator;
import usm.simulacion.twitter.simulator.ListaSimpleEnlazada.ListaUtil;
import usm.simulacion.twitter.simulator.ListaSimpleEnlazada.Lista;
import java.util.ArrayList;
import java.util.Iterator;


/**
 *
 * @author Herbyn
 */
public class Grafo {

   private ArrayList<Nodo> users;  // lista de usuarios
   //private Lista lista;  lista de Nodos que no ocupare
   private int nNodo;   // contador de cantidad de Nodos 


   /**
    * La clase nodo tiene un objeto elemento el cual seran usuarios
    * un arreglo de enlaces a sus followers
    * y un arreglo de enlaces a sus following
    */
   class Nodo {
       
	Object elemento;
        ArrayList<Nodo> listaEnlacesFollower;
        ArrayList<Nodo> listaEnlacesFollowing;
	int id;
        int ContFollowers;
        int ContFollowins;
        //Lista listaEnlaces;  antiguo
	
        
	public Nodo(Object elemento) {
	   this.elemento = elemento;
           listaEnlacesFollower = new ArrayList();
           listaEnlacesFollowing = new ArrayList();
    	   id = 0;
           //listaEnlaces = new Lista();
	   
	}

        public ArrayList<Nodo> getListaEnlacesFollower() {
            return listaEnlacesFollower;
        }

        public ArrayList<Nodo> getListaEnlacesFollowing() {
            return listaEnlacesFollowing;
        }
	public boolean equals(Object nodo) {
	   if (nodo==null) return false;
	   Nodo n = (Nodo)nodo;
	   return elemento.equals(n.elemento);
	}
	public String toString() {
	   return elemento+"";
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

   

    class Enlace {
	Nodo nodo;

	//double peso;
        public Enlace(Object elemento) {
	   			
	  	Object p = ListaUtil.buscar(users ,new Nodo(elemento));
	    	if (p==null) return;
//	    	nodo = (Nodo)lista.recupera(p);
	    	//this.peso = peso;
	    
}

       // public Enlace(Object elemento) {
    	   			
      	//Object p = ListaUtil.buscar(users,new Nodo(elemento));
		//if (p==null) return;
		//nodo = (Nodo)users.; //lista.recupera(p);
    	    
  	



   public boolean equals(Object enlace) {
    	   if (enlace==null) return false;
    	   Enlace e = (Enlace)enlace;
    	   if ((nodo==null)&&(e.nodo==null)) return true;
    	   if (!(nodo.equals(e.nodo))) return false;
    	   return true;
   	}
  	//public String toString() {
    	//   return nodo+"("+peso+")";
  	//}

    }
	
   // Contruye un nuevo grafo
   public Grafo() {

    users = new ArrayList();
    // lista = new Lista();  antiguo
    nNodo = 0;

   }
   // Inserta un nuevo nodo en el grafo con el elemento dado
   public void insertaNodo(Object elemento3) {
    	//if (contieneNodo(elemento3)) return;
    	Nodo nodo = new Nodo(elemento3);

        users.add(nodo);
         //lista.inserta(lista.fin(),nodo);
        //nodo.id = id;
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
	   //Nodo n = (Nodo)users.recupera(p);  * MODIFICAR
	   //Lista l=n.listaEnlaces;  // MODIFICAR
	   //Object q = ListaUtil.buscar(l,new Enlace(elemento2));
	   //if (q==null) return false;
	   return true;
	
   }
   // Borra el nodo con el elemento dado
  /* public void borraNodo(Object elemento) {
    	
	   Object p = ListaUtil.buscar(lista,new Nodo(elemento));
	   if (p==null) return;
	   lista.suprime(p);
	   nNodo--;
	   if (lista.vacia()) return;					
   for(p=lista.primero();!p.equals(lista.fin());
       p=lista.siguiente(p)) {
		Nodo n = (Nodo)lista.recupera(p);
		Lista l=n.listaEnlaces;
		Object q = ListaUtil.buscar(l,new Enlace(elemento));
		if (q!=null) l.suprime(q);
	   }
	
   } */
   // Bora el enlace entre los nodos con elemento1 y elemento2
   /*public void borraEnlace(Object elemento1, Object elemento2) {
    	
	   Object p = ListaUtil.buscar(lista,new Nodo(elemento1));
	   if (p==null) return;
	   Nodo n = (Nodo)lista.recupera(p);
	   Lista l=n.listaEnlaces;
	   Object q = ListaUtil.buscar(l,new Enlace(elemento2));
	   if (q!=null) l.suprime(q);
	 
   }*/
   // Devuelve la lista de nodos
  /* public ArrayList listaNodos() {
    	
       ArrayList l = new ArrayList();
    	   if (l.isEmpty()) return l;
    	   for(Object p=lista.primero();!p.equals(lista.fin());
                    p=lista.siguiente(p)) {
    		Nodo n = (Nodo)lista.recupera(p);
    		l.inserta(l.fin(),n.elemento);
    	   }
	   return l;
	
   }*/
   // Devuelve la lista de elementos enlazados a un nodo
 /*  public Lista listaEnlaces(Object elemento) {
				
	   Object p = ListaUtil.buscar(lista,new Nodo(elemento));
	   if (p==null) return null;
	   Nodo n = (Nodo)lista.recupera(p);
	   Lista le = n.listaEnlaces;
	   Lista l = new Lista();
    	   if (le.vacia()) return l;
    	   for(p=le.primero();!p.equals(le.fin());p=le.siguiente(p)) {
    	      Enlace e = (Enlace)le.recupera(p);
    		l.inserta(l.fin(),e.nodo.elemento);
    	   }
	   return l;
	 
   }*/

   
   // Recupera el numero de nodos del grafo
   public int nNodos() {
	return nNodo;
   }
   
   /*public String toString() {
	
	   String s = "";
	   if (lista.vacia()) return s;
	   for(Object p=lista.primero();!p.equals(lista.fin());
                    p=lista.siguiente(p)) {
		Nodo n = (Nodo)lista.recupera(p);
		s += "Nodo "+n+": ";
		Lista le = n.listaEnlaces;
		if (!le.vacia()) {
		   for(Object q=le.primero();!q.equals(le.fin());
                          q=le.siguiente(q)) {
			Enlace e = (Enlace)le.recupera(q);
			s += e;
		   }
		}
		s += "\n";
	   }
	   return s;
	 
   } */
    
   
    public ArrayList getLista() {
        return users;
    }

    public int getnNodo() {
        return nNodo;
    }

   
   
   
} 

