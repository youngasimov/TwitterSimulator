/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import usm.simulacion.twitter.core.Event;

/**
 *
 * @author camilovera
 */
public interface EventGeneratorAlgoritm {
    
    public void setNetworkManager(NetworkManager network);
    public Event generateEvent(Event procecedEvent);
    public long getDeltaTime();
    
}
