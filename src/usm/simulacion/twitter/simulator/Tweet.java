/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

/**
 *
 * @author camilovera
 */
public class Tweet implements TweetBase{
    
    private String messagee;
    private int id;
    private int steps;
    private User owner;
    
    public Tweet(int id){
        this.id = id;
    }
    
    @Override
    public int getId(){
        return id;
    }

    public void setMessagee(String messagee) {
        this.messagee = messagee;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
    
    @Override
    public String getMessage(){
        return messagee;
    }
    
    @Override
    public int getSteps(){
        return steps;
    }
    
    @Override
    public User getOwner(){
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Tweet && ((Tweet)o).getId() == this.id){
            return true;
        }else{
            return false;
        }
    }
}
