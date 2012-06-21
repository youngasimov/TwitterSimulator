/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.probability;

/**
 *
 * @author camilovera
 */
public abstract class BaseProbabilisticFunction implements ProbabilisticFunction {
    
    private int seed;
    private int min;
    private int max;
    private ProbabilisticFunction uniform;
    
    public BaseProbabilisticFunction(){
        uniform = new BasicUniform();
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

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public ProbabilisticFunction getUniform() {
        return uniform;
    }

    public void setUniform(ProbabilisticFunction uniform) {
        this.uniform = uniform;
    }
    
    public static int getIntFromDouble(double value){
        
        double aux = value%1;
        int potencia = 1;
        while(aux!=0){
            aux = aux%10;
            potencia = potencia*10;
        }
        return (int)value*potencia;
    }
    
}
