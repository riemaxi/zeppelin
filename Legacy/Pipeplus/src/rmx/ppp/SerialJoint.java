/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

/**
 *
 * @author Samuel
 */
public class SerialJoint extends GroupJoint{

    @Override
    public boolean done(){
           return line.get(line.size()-1).done();
    }
    
    @Override
    public void execute() {
        while(G.state != C.STATE_SUCCESS && line.size()>0 && !done())
            line.stream().forEach(joint -> joint.execute() );
    }
    
}
