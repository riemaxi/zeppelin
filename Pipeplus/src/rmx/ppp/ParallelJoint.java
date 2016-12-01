/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

import java.util.ArrayList;
import java.util.List;

class Process extends Thread{
    Joint joint;
    public Process(Joint joint){
        this.joint = joint;
    }
    
    public void run(){
        joint.execute();
    }
}

/**
 *
 * @author Samuel
 */
public class ParallelJoint extends GroupJoint{
    protected final List<Process> processes;
    
    public ParallelJoint(){
        processes = new ArrayList<>();
    }
    
    protected void pause(){
        try{
            Thread.sleep(C.PARAMETER_PAUSE_TIME);
        }catch(Exception e){
            
        }
               
    }
    
    protected boolean mount(int i, Joint joint){
        boolean mounted = joint.mount();
        if (mounted)
            processes.add(new Process(joint));
        
        return mounted;
    }
    
    @Override 
    public void execute(){
        processes.stream().forEach(p -> p.start());
        
        while(!done()){
            pause();
        }
    }
}
