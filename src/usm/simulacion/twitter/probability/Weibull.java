/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.probability;

/**
 *
 * @author camilovera
 */
public class Weibull extends BaseProbabilisticFunction{

    
    private float k;
    private float lambda;
    
    public Weibull(float k, float lambda){
        super();
        this.k = Math.abs(k);
        this.lambda=Math.abs(lambda);
    }

    public float getK() {
        return k;
    }

    public void setK(float k) {
        this.k = Math.abs(k);
    }

    public float getLambda() {
        return lambda;
    }

    public void setLambda(float lambda) {
        this.lambda = Math.abs(lambda);
    }
    
    @Override
    public int nextInt() {
        return BaseProbabilisticFunction.getIntFromDouble(nextDuble());
    }

    @Override
    public float nextFloat() {
        return (float)nextDuble();
    }

    @Override
    public double nextDuble() {
        return -lambda*Math.pow(Math.log(1-getUniform().nextDuble()), 1/k);
    }
    
}
