/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package usm.simulacion.twitter.simulator;
import usm.simulacion.twitter.simulator.ListaSimpleEnlazada.ListaUtil;
import usm.simulacion.twitter.simulator.ListaSimpleEnlazada.Lista;



/**
 *
 * @author Herbyn
 */
public class Grafo {

    
   private Lista lista; // lista de Nodos
   private int nNodo;   // contador de cantidad de Nodos

   class Nodo {
       
	Object elemento;
	Lista listaEnlaces;
	int id;
        
	public Nodo(Object elemento) {
	   this.elemento = elemento;
    	   listaEnlaces = new Lista();
	   id = 0;
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
        public Lista getListaEnlaces() {
            return listaEnlaces;
        }
   }

   

   class Enlace {
	Nodo nodo;
	double peso;
	public Enlace(Object elemento, double peso) {
	   			
	  	Object p = ListaUtil.buscar(lista,new Nodo(elemento));
	    	if (p==null) return;
	    	nodo = (Nodo)lista.recupera(p);
	    	this.peso = peso;
	    
}
       public Enlace(Object elemento) {
    	   			
      	Object p = ListaUtil.buscar(lista,new Nodo(elemento));
		if (p==null) return;
		nodo = (Nodo)lista.recupera(p);
    	    
  	}
  	public boolean equals(Object enlace) {
    	   if (enlace==null) return false;
    	   Enlace e = (Enlace)enlace;
    	   if ((nodo==null)&&(e.nodo==null)) return true;
    	   if (!(nodo.equals(e.nodo))) return false;
    	   return true;
   	}
  	public String toString() {
    	   return nodo+"("+peso+")";
  	}
   }
	
   // Contruye un nuevo grafo
   public Grafo() {
lista = new Lista();
nNodo = 0;
   }
   // Inserta un nuevo nodo en el grafo con el elemento dado
   public void insertaNodo(Object elemento, int id) {
    	if (contieneNodo(elemento)) return;
    	Nodo nodo = new Nodo(elemento);
    	lista.inserta(lista.fin(),nodo);
        nodo.id = id;
        nNodo++;
        
   }
   // Inserta un enlace entre los nodos con elemento1 
   // y elemento2 con el peso dado
   public void insertaEnlace(Object elemento1, Object elemento2, double peso){
    	
	   Object p = ListaUtil.buscar(lista,new Nodo(elemento1));
	   if (p==null) return;
	   Nodo n = (Nodo)lista.recupera(p);
	   Lista l=n.listaEnlaces;
	   Object q = ListaUtil.buscar(l,new Enlace(elemento2));
	   if (q==null) l.inserta(l.fin(),new Enlace(elemento2,peso));
	 
   }
   // Devuelve un booleano que indica si el grafo es vacio
   public boolean esVacio() {
    	return (lista.vacia());
   }
   // Devuelve un booleano que indica si el grafo contiene 
   // un nodo con elemento
    public boolean contieneNodo(Object elemento) {
    	Object p = ListaUtil.buscar(lista,new Nodo(elemento));
	if (p==null) return false;
    	return true;
   }

   // Devuelve un booleano que indica si el grafo contiene un enlace 
   // entre los nodos con elemento1 y elemento2
   public boolean contieneEnlace(Object elemento1, Object elemento2) {
    	
	   Object p = ListaUtil.buscar(lista,new Nodo(elemento1));
	   if (p==null) return false;
	   Nodo n = (Nodo)lista.recupera(p);
	   Lista l=n.listaEnlaces;
	   Object q = ListaUtil.buscar(l,new Enlace(elemento2));
	   if (q==null) return false;
	   return true;
	
   }
   // Borra el nodo con el elemento dado
   public void borraNodo(Object elemento) {
    	
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
	
   }
   // Bora el enlace entre los nodos con elemento1 y elemento2
   public void borraEnlace(Object elemento1, Object elemento2) {
    	
	   Object p = ListaUtil.buscar(lista,new Nodo(elemento1));
	   if (p==null) return;
	   Nodo n = (Nodo)lista.recupera(p);
	   Lista l=n.listaEnlaces;
	   Object q = ListaUtil.buscar(l,new Enlace(elemento2));
	   if (q!=null) l.suprime(q);
	 
   }
   // Devuelve la lista de nodos
   public Lista listaNodos() {
    	
    	   Lista l = new Lista();
    	   if (lista.vacia()) return l;
    	   for(Object p=lista.primero();!p.equals(lista.fin());
                    p=lista.siguiente(p)) {
    		Nodo n = (Nodo)lista.recupera(p);
    		l.inserta(l.fin(),n.elemento);
    	   }
	   return l;
	
   }
   // Devuelve la lista de elementos enlazados a un nodo
   public Lista listaEnlaces(Object elemento) {
				
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
	 
   }

   // Recupera el peso del enlace entre nodo1 y nodo2
   public double recuperaPeso(Object elemento1, Object elemento2) {
    	
	   Object p = ListaUtil.buscar(lista,new Nodo(elemento1));
	   if (p==null) return Double.MAX_VALUE;
	   Nodo nodo1 = (Nodo)lista.recupera(p);
	   p = ListaUtil.buscar(lista,new Nodo(elemento2));
	   if (p==null) return Double.MAX_VALUE;;
	   Nodo nodo2 = (Nodo)lista.recupera(p);
	   if (nodo1.equals(nodo2)) return 0.0;
	   Lista le = nodo1.listaEnlaces;
	   p = ListaUtil.buscar(le,new Enlace(nodo2.elemento));
	   if (p==null) return Double.MAX_VALUE;;
	   Enlace e = (Enlace)le.recupera(p);
	   return e.peso;
	
   }
   // Recupera el numero de nodos del grafo
   public int nNodos() {
	return nNodo;
   }
   
   public String toString() {
	
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
	 
   }
    
   
    public Lista getLista() {
        return lista;
    }

    public int getnNodo() {
        return nNodo;
    }

   
   
   
} 

