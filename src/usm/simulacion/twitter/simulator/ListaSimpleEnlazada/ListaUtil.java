/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator.ListaSimpleEnlazada;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author Herbyn
 */

    
    public class ListaUtil {
        /**
         *
         * @param lista
         * @param elemento
         * @return
         */
    static public Object buscar(ArrayList lista, Object elemento) {
        //Iterator iterador = lista.listIterator();
        for ( Iterator iterador = lista.listIterator(); iterador.hasNext(); ){
                Object b = (Object) iterador.next();
                if(b.equals(elemento)) return b;

        }

       return null;
        }
        
        //if (lista.isEmpty() ) return null;
          //  int i;
           // for (  i = 0  ; i < lista.size() ; i++);{
            //  Object e = lista.get(i);
             // if (e.equals(elemento)) return i;
        
         //   for(Object p = lista.primero(); !p.equals(lista.fin());
           //            p = lista.siguiente(p)) {
             //   Object e = lista.recupera(p);
              //  if (e.equals(elemento)) return p;
            
            
        
     // fin buscar()
    static public void imprimir(ArrayList lista) {
        if (lista.isEmpty() ) { System.out.println(); return; }
        
            for ( Iterator iterador = lista.listIterator(); iterador.hasNext(); ){

                Object e = (Object) iterador.next();
                System.out.print(e+" ");
            }
            System.out.println();            
        
    } // fin imprimir()
} // fin class ListaUtil

    

