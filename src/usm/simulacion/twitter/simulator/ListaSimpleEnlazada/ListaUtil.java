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
         *Busca un elemento en la lista y lo devuelve
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

        
     // fin buscar()
    static public void imprimir(ArrayList lista) {
        if (lista.isEmpty() ) { System.out.println(); return; }
        
            for ( Iterator iterador = lista.listIterator(); iterador.hasNext(); ){

                Object e = (Object) iterador.next();
                System.out.print(e+" ");
            }
            System.out.println();            
        
    } // 
} // 

    

