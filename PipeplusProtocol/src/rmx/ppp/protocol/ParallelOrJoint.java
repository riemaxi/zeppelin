/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp.protocol;

/**
 *
 * @author Samuel
 */
public class ParallelOrJoint extends ParallelJoint{
    
    @Override
    public boolean done(){
        return line
                .stream()
                .filter(joint -> joint.done())
                .count() > 0;
                
    }
}
