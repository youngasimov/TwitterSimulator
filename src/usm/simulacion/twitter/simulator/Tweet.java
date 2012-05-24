/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package usm.simulacion.twitter.simulator;

/**
 *
 * @author camilovera
 */
public class Tweet{
    
    private String messagee;
    private int id;
    private int steps;
    private User owner;
    
    public Tweet(int id){
        this.id = id;
    }
    
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
    
    public String getMessage(){
        return messagee;
    }
    
    public int getSteps(){
        return steps;
    }
    
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
