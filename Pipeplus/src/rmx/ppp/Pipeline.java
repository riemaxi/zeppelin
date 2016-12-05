/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import rmx.ppp.protocol.*;

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
    
    protected boolean done(){
        return line.get(line.size()-1).done();
    }
    
    public void execute(){
        execute(Integer.MAX_VALUE);
    }    
    
    public void execute(int times){
        boolean aborted = false;        
        while(!aborted && times-->0 && line.size()>0 && !done()){
            for(Joint joint:line){
                if (aborted = joint.execute())
                    break;
            }
        }
        
        if (aborted)
            abort();
        else
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
