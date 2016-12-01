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
    public void execute() {
        for(int i=0; i<line.size() && G.state != C.STATE_ABORTED && G.state != C.STATE_SUCCESS ; i++){
            line.get(i).execute();
        }
    }
    
}
