/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator.ListaSimpleEnlazada;

/**
 *
 * @author Herbyn
 */
class Posicion {
    protected Object elemento;
    protected Posicion sig;
    public boolean equals(Object o) {
        Posicion p = (Posicion)o;
        return (p==this);
    }
} 
