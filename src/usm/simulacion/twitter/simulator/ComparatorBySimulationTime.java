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
        Double time1 = new Double(t1.getCurrentTime()+t1.getDeltaTime());
        Double time2 = new Double(t2.getCurrentTime()+t2.getDeltaTime());
        return time1.compareTo(time2);
    }

    
    
}
