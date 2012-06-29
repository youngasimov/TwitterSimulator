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
    
    private double seed;
    private int min;
    private int max;
    private Random r;
    
    public Uniform(){
        r = new Random(-275920);
    }
    
    public Uniform(double seed){
        r = new Random((long)seed);
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public double getSeed() {
        return seed;
    }

    public void setSeed(double seed) {
        if(seed>1){
            seed = seed/Math.ulp(seed);
        }
        this.seed = seed;
        r = new Random((long)seed);
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
    
    public static int getIntFromDouble(double value){
        
        double aux = value;
        double potencia = 1;
        double resto = value%1;
        while(resto!=0 && aux<Integer.MAX_VALUE){
            aux = aux*10;
            resto = aux%1;
            potencia = potencia*10;
        }
        return (int)(value*(potencia/10));
    }
    
}
