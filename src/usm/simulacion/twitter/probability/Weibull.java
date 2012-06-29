/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.probability;

/**
 *
 * @author camilovera
 */
public class Weibull extends ComplexUniform{

    
    private double k;
    private double lambda;
    
    public Weibull(double k, double lambda){
        super();
        this.k = Math.abs(k);
        this.lambda=Math.abs(lambda);
    }
    
    public Weibull(double k, double lambda,double seed){
        super((long)seed);
        this.k = Math.abs(k);
        this.lambda=Math.abs(lambda);
    }

    public double getK() {
        return k;
    }

    public void setK(double k) {
        this.k = Math.abs(k);
    }

    public double getLambda() {
        return lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = Math.abs(lambda);
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
        return -lambda*Math.pow(Math.log(1-super.nextDuble()), 1/k);
    }
    
}
