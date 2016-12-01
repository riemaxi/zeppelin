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
public class GroupJoint implements Joint{
    protected final List<Joint> line;
    public GroupJoint(Joint ... joints){
        line = new ArrayList<>();
        add(joints);
    }
    
    protected boolean mount(int i, Joint joint){
        return joint.mount();
    }

    protected void abort(int i, Joint joint){
        joint.abort();
    }

    protected void success(int i, Joint joint){
        joint.success();
    }
    
    public void add(Joint ... joints){
        for(int i=0; i<joints.length; i++){
            line.add(joints[i]);
        }
    }
    
    @Override
    public boolean done(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean mount() {
        int count = 0;
        for(int i=0; i<line.size(); i++){
            Joint joint = line.get(i);
            if (mount(i,joint)){
                count++;
            }
        }
        
        return count == line.size();
    }

    @Override
    public void execute() {
        line.stream().forEach(joint -> joint.execute() );
    }

    @Override
    public void abort() {
        for(int i=0; i<line.size(); i++)
            abort(i, line.get(i));
    }

    @Override
    public void success() {
        for(int i=0; i<line.size(); i++)
            success(i, line.get(i));
    }

}
