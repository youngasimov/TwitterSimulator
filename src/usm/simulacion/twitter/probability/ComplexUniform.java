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
public class ComplexUniform extends Uniform {

    private static final long IA =  16807;
    private static final long IM =  2147483647;
    private static final double AM = (1.0/IM);
    private static final long IQ = 127773;
    private static final long IR =  2836;
    private static final int NTAB = 32;
    private static final double NDIV = (1+(IM-1)/NTAB);
    private static final double EPS = 1.2e-7;
    private static final double RNMX = (1.0-EPS);
    
    
    private long seed;
    private Random r; 
    
    public ComplexUniform(){
        this(-275920);
        r= new Random(-275920);
    }
    
    public ComplexUniform(long seed){
        this.seed = seed;
        r= new Random(seed);
    }
    
    @Override
    public int nextInt() {
        return (int)Math.round(nextDuble());
    }

    @Override
    public float nextFloat() {
        return (float)nextDuble();
    }

    @Override
    public double nextDuble() {
        return r.nextDouble();
        /*int j;
        long k;
        double iy = 0;
        long[] iv = new long[NTAB];
        double temp;
        seed = -1*Math.abs(seed);
        if (seed<= 0 || iy!=0){

                if (-seed < 1){ seed=1;} //Be sure to prevent idum = 0
                else {seed = -seed;}
                for (j=NTAB+7;j>=0;j--){		//Load the shu.e table (after 8 warm-ups)
                    k=seed/IQ;
                    seed=IA*(seed-k*IQ)-IR*k;
                    if (seed < 0){seed += IM;}
                    if (j < NTAB){iv[j] = seed;}
                }
                iy=iv[0];
        }
        k=seed/IQ;					//Start here when not initializing.
        seed=IA*(seed-k*IQ)-IR*k;
        if (seed < 0){seed += IM;}
        j=(int) (iy/NDIV);
        iy=iv[j];
        iv[j] = seed;
        if ((temp=AM*iy) > RNMX){ return Math.abs(RNMX);}
        else{ return Math.abs(temp);}*/
    }
}