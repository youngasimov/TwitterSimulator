/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

import java.util.Comparator;
import usm.simulacion.twitter.core.FutureEventEvent;

/**
 *
 * @author camilovera
 */
public class ComparatorBySimulationTime implements Comparator<FutureEventEvent> {

    @Override
    public int compare(FutureEventEvent t1, FutureEventEvent t2) {
        Long time1 = new Long(t1.getCurrentTime()+t1.getDeltaTime());
        Long time2 = new Long(t2.getCurrentTime()+t2.getDeltaTime());
        return time1.compareTo(time2);
    }

    
    
}
