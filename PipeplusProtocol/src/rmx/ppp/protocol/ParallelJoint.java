/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp.protocol;

import java.util.ArrayList;
import java.util.List;

class Process extends Thread{
    Joint joint;
    public Process(Joint joint){
        this.joint = joint;
    }
    
    public void abort(){
        try{
            joint.abort();
        }catch(Exception e){
            
        }
    }
    
    @Override
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
    private final int pauseTime = P.i(C.PARAMETER_PAUSE_TIME_NAME,C.PARAMETER_PAUSE_TIME);
    
    public ParallelJoint(){
        processes = new ArrayList<>();
    }
    
    protected void pause(){
        try{
            Thread.sleep(pauseTime);
        }catch(Exception e){
            
        }
    }
    
    @Override
    protected boolean mount(int i, Joint joint){
        if (!joint.mount())
            return false;

        processes.add(new Process(joint));
        return true;
    }
    
    @Override
    public void abort(){
        processes.stream().forEach(p -> p.abort());
        processes.clear();
    }
    
    @Override 
    public boolean execute(){
        if (processes.isEmpty())
            mount();
 
        processes.stream().forEach(p -> p.start() );

        while(!done()){
            pause();
        }

        abort();
        
        return false;
    }
}
