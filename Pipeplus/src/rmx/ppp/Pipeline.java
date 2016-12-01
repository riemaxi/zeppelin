/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class Pipeline {
    private final List<Joint> line;
    public Pipeline(){
        line = new ArrayList<>();
    }
    
    public List<Joint> mount(Joint ... joints){
        List<Joint> umlist = new ArrayList<>();
        for(int i=0; i<joints.length; i++){
            Joint joint = joints[i];
            if (joint.mount())
                line.add(joint);
            else
                umlist.add(joint);
        }
        
        return umlist;
    }
    
    public void execute(){
        G.state = C.STATE_STARTED;
        while(G.state != C.STATE_SUCCESS)
            for(Joint joint : line){
                joint.execute();
                if (G.state == C.STATE_ABORTED){
                    abort();
                    return;                    
                }
            }
        
        success();
    }
    
    protected void abort(){
        for(Joint joint: line)
            joint.abort();
    }

    protected void success(){
        for(Joint joint: line)
            joint.success();
    }
}
