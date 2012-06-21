/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.probability;

import java.util.Random;

/**
 *
 * @author camilovera
 */
public class BasicUniform extends BaseProbabilisticFunction {

    private Random r;
    
    public BasicUniform(){
        r = new Random(1);
    }
    
    public BasicUniform(long seed){
        r = new Random(seed);
    }
    
    @Override
    public int nextInt() {
        return r.nextInt();
    }

    @Override
    public float nextFloat() {
        return r.nextFloat();
    }

    @Override
    public double nextDuble() {
        return r.nextDouble();
    }
    
}
