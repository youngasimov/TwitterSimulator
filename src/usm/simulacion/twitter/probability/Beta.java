package usm.simulacion.twitter.probability;

import org.apache.commons.math3.distribution.BetaDistribution;

/**
 *
 * @author camilovera
 */
public class Beta extends ComplexUniform {
    
    private double a;
    private double b;
    private BetaDistribution bd;
    
    public Beta(double a, double b){
        super();
        this.a = a;
        this.b = b;
        bd = new BetaDistribution(a, b);
    }
    
    public Beta(double a, double b, double seed){
        super((long)seed);
        this.a = a;
        this.b = b;
        bd = new BetaDistribution(a, b);
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
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
        return bd.inverseCumulativeProbability(super.nextDuble());
    }
    
}
