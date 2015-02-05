/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.probability;

import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author camilovera
 */
public class Normal extends ComplexUniform{
    
    private double center;
    private double standardDeviation;
    private NormalDistribution nd;
    
    
    public Normal(double center, double standardDeviation){
        this.center = Math.abs(center);
        this.standardDeviation = Math.abs(standardDeviation);
        if(center <= 0){
            center = 1;
        }
        if(standardDeviation <= 0){
            standardDeviation = 1;
        }
        nd = new NormalDistribution(center, standardDeviation);  
    }
    
    public Normal(double center, double standardDeviation, double seed){
        super((long)seed);
        this.center = Math.abs(center);
        this.standardDeviation = Math.abs(standardDeviation);
        if(center <= 0){
            center = 1;
        }
        if(standardDeviation <= 0){
            standardDeviation = 1;
        }
        nd = new NormalDistribution(center, standardDeviation);  
    }

    public double getCenter() {
        return center;
    }

    public void setCenter(double center) {
        this.center = center;
        nd = new NormalDistribution(center, standardDeviation);
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
        nd = new NormalDistribution(center, standardDeviation);
    }
    
    @Override
    public int nextInt() {
        Double d = nextDuble();
        return (int)Math.round(d);
    }

    @Override
    public float nextFloat() {
        return (float)nextDuble();
    }

    @Override
    public double nextDuble() {
        return nd.inverseCumulativeProbability(super.nextDuble());
    }
    
}
