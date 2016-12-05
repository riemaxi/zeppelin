/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Samuel
 */
public class Pipeline {
    private final List<Joint> line;
    public Pipeline(){
        line = new ArrayList<>();
    }

    public Pipeline(List<Joint> joints, Consumer<Joint> reporter){
        line = new ArrayList<>();
        mount(joints, reporter);
    }
    
    public Pipeline mount(List<Joint> joints, Consumer<Joint> reporter){
        for(int i=0; i<joints.size(); i++){
            Joint joint = joints.get(i);
            if (joint.mount())
                line.add(joint);
            else
                reporter.accept(joint);
        }
        return this;
    }
    
    public void execute(){
        execute(Integer.MAX_VALUE);
    }    
    
    public void execute(int times){
        G.state = C.STATE_STARTED;
        while(G.state != C.STATE_SUCCESS && line.size()>0 && times-->0)
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
