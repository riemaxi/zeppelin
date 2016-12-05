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
public class SerialJoint extends GroupJoint{

    @Override
    public boolean done(){
           return line.get(line.size()-1).done();
    }
    
    @Override
    public boolean execute() {
        boolean aborted = false;
        while(!aborted  && line.size()>0 && !done())
            for(Joint joint:line){
                if (aborted = joint.execute())
                    break;
            }
        
        return aborted;
    }
    
}
