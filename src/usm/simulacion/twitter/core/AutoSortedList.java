/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.core;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author camilovera
 */
public class AutoSortedList<H extends FutureEventEvent> extends ArrayList<H>{
    
    private Comparator<H> comparator;
    
    
    public AutoSortedList(Comparator<H> comparator){
        this.comparator = comparator;
    }

    @Override
    public boolean add(H e) {
        
        for(int i = this.size()-1;i>=0;i++){
            int c = comparator.compare(this.get(i), e);
            if(c==1){
                this.add(i, e);
                return true;
            }
            if(c == 0){
                return false;
            }
        }
        
        return true;
    }
    
    public H first(){
        return this.get(0);
    }
    
    public H pollFirst(){
        return this.remove(0);
    }
    
}
