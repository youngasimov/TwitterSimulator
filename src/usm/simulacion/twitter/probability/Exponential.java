/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.probability;

import org.apache.commons.math3.distribution.ExponentialDistribution;

/**
 *
 * @author camilovera
 */
public class Exponential extends ComplexUniform{
    
    
    private ExponentialDistribution exponential;
    

    public Exponential(double mean){
        exponential = new ExponentialDistribution(mean);
    }
    
    public Exponential(double mean, double seed){
        super((long)seed);
        exponential = new ExponentialDistribution(mean);
    }
    
    @Override
    public int nextInt() {
        double d = nextDuble();
        return (int)Math.round(d);
    }

    @Override
    public float nextFloat() {
        return (float)nextDuble();
    }

    @Override
    public double nextDuble() {
        return exponential.inverseCumulativeProbability(super.nextDuble());
    }
    
}
