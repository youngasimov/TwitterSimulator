/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

/**
 *
 * @author camilovera
 */
public interface TweetBase {
    UserBase getOwner();
    String getMessage();
    int getSteps();
    int getId();
}
