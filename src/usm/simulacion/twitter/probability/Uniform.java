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
public class Uniform implements ProbabilisticFunction {
    
    private long seed;
    private double min;
    private double max;
    private Random r;
    
    public Uniform(){
        this(-275920);
    }
    
    public Uniform(long seed){
        r = new Random(seed);
        max = 1;
        min = 0;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public long getSeed() {
        return seed;
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
        r = new Random((long)seed);
    }

    @Override
    public int nextInt() {
        double delta = max - min;
        return (int)(Math.round((r.nextDouble()%delta)+min)%Integer.MAX_VALUE);
    }

    @Override
    public float nextFloat() {
        float delta = (float)(max - min);
        return (r.nextFloat()%delta)+(float)min;
    }

    @Override
    public double nextDuble() {
        double delta = max - min;
        return (r.nextDouble()%delta)+min;
    }
    
}
