/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator.ListaSimpleEnlazada;


/**
 *
 * @author Herbyn
 */

    
//public class Lista implements ListaInterface.Lista{ <-- No me pesca esa wea :C

  public class Lista { 
  
    protected Posicion inicio, fin;
    protected int longitud;
    public Lista() {
        inicio = new Posicion();
        longitud = 0;
        fin = inicio;
        fin.sig = null;
    }
    public Object fin() {
        return fin;
    }
    public Object primero() {
        if (vacia()) ;
        return inicio;
    }
    public Object siguiente(Object posicion) {
        Posicion p = (Posicion)posicion;
        if (vacia()) ;
        if (p==fin) ;
        return p.sig;
    }
    public Object anterior(Object posicion){
        
        Posicion p = (Posicion)posicion;
        if (vacia());
        if (p==inicio);
        Posicion q = inicio;
        while(q.sig!=p)
            q=q.sig;        
  return q;
    }

    public boolean vacia() {
        return (longitud==0);
    }
    public Object recupera(Object posicion){
        Posicion p = (Posicion)posicion;
        if (vacia());
        if (p==fin);
        return p.sig.elemento;
    }
    public int longitud() {
        return longitud;
    }
    public void inserta(Object posicion, Object elemento) {
        Posicion p = (Posicion)posicion;
        Posicion q = p.sig;
        p.sig = new Posicion();
        p.sig.elemento = elemento;
        p.sig.sig = q;
        if (q==null) fin=p.sig;
        longitud++;
    }
    public void suprime(Object posicion) 
     {
        Posicion p = (Posicion)posicion;
        if (vacia()) ;
        if (p==fin) ;
        p.sig = p.sig.sig;
        if (p.sig==null) fin=p;
        longitud--;
    }
    public void modifica(Object posicion, Object elemento) 
       {
        Posicion p =(Posicion)posicion;
        if (vacia()) ;
        if (p==fin) ;
        p.sig.elemento = elemento;
    }
  }

    

