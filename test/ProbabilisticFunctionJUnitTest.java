/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import usm.simulacion.twitter.probability.*;

/**
 *
 * @author camilovera
 */
public class ProbabilisticFunctionJUnitTest extends TestCase {
    
    long seed;
    double iterations;
    
    
    public ProbabilisticFunctionJUnitTest(String testName) {
        super(testName);
        seed = 10000;
        iterations = 500;
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    
    public void testUniform(){
        function(new Uniform(seed),"Uniform");
        
    }
    
    public void testComplexUniform(){
        function(new ComplexUniform(seed),"Complex Uniform");
    }
    
    
    
    public void testBeta(){
        function(new Beta(0.5,1.2,seed),"Beta");
    }
    
    public void testWiebull(){
        function(new Weibull(0.5,1.2,seed),"Weibull");
    }
    
    
    public void testNormal(){
        function(new Normal(40,10,seed),"Normal");
    }
    
    
    public void testExponential(){
        function(new Exponential(2,seed),"Exponential");
    }
    
    private void function(ProbabilisticFunction pf , String name){
        Double value;
        System.err.println(name+":  ");
        double media = 0;
        double varianza = 0;
        List<Double> values = new ArrayList<Double>();
        for(int i =0; i < iterations;i++){
            value = pf.nextDuble();
            media = media+value;
            values.add(value);
            System.err.println(value);
        }
        media = media/iterations;
        for(int i =0; i < iterations;i++){
            varianza = varianza + Math.pow(values.get(i)-media,2);
        }
        varianza = varianza/iterations;
        System.err.println("media = "+media);
        System.err.println("varianza = "+varianza);
    }
}
