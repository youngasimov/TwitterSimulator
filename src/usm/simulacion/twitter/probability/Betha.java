/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.probability;

/**
 *
 * @author camilovera
 */
public class Betha extends BaseProbabilisticFunction {
    
    private float a;
    private float b;
    
    public Betha(float a, float b){
        super();
        this.a = a;
        this.b = b;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }
    
    @Override
    public int nextInt() {
        return 1;
    }

    @Override
    public float nextFloat() {
        return 1;
    }

    @Override
    public double nextDuble() {
        return 1.0;
    }
    
}
