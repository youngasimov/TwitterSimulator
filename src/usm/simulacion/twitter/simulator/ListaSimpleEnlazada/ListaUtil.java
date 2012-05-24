/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator.ListaSimpleEnlazada;

/**
 *
 * @author Herbyn
 */

    
    public class ListaUtil {
    static public Object buscar(Lista lista, Object elemento) {
        if (lista.vacia()) return null;
        
            for(Object p = lista.primero(); !p.equals(lista.fin()); 
                       p = lista.siguiente(p)) {
                Object e = lista.recupera(p);
                if (e.equals(elemento)) return p;
            }
            return null;
        
    } // fin buscar()
    static public void imprimir(Lista lista) {
        if (lista.vacia()) { System.out.println(); return; }
        
            for(Object p = lista.primero(); !p.equals(lista.fin()); 
                       p = lista.siguiente(p)) {
                Object e = lista.recupera(p);
                System.out.print(e+" ");
            }
            System.out.println();            
        
    } // fin imprimir()
} // fin class ListaUtil

    

