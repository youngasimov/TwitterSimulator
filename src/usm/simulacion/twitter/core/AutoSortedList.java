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
        if(this.size()==0){
            super.add(e);
        }else{
            for(int i = this.size()-1;i>=0;i--){
                H h = this.get(i);
                int c = comparator.compare(h, e);
                if(c==-1){
                    if(i == this.size()-1){
                        super.add(e);
                        return true;
                    }else{
                        this.add(i+1, e);
                        return true;
                    }
                }else if(c == 0){
                    return false;
                }
            }
            super.add(0,e);
        }
        return true;
    }
    
    public H first(){
        if(this.isEmpty()){
            return null;
        }
        return this.get(0);
    }
    
    public H pollFirst(){
        if(this.isEmpty()){
            return null;
        }
        return this.remove(0);
    }
    
}
