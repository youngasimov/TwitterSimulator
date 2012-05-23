/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator.ListaInterface;

/**
 *
 * @author Herbyn
 */
public interface Lista {
    
    public Object fin();
    public Object primero(); 
    public Object siguiente(Object posicion); 
    public Object anterior(Object posicion);
    public boolean vacia();
    public Object recupera(Object posicion);
    public int longitud();
    public void inserta(Object posicion, Object elemento);
    public void suprime(Object posicion); 
    public void modifica(Object posicion, Object elemento); 
  
}
