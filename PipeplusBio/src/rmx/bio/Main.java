/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.bio;

import java.util.HashMap;
import rmx.ppp.Builder;
import rmx.ppp.C;
import rmx.ppp.G;
import rmx.ppp.Joint;

/**
 *
 * @author Samuel
 */
public class Main {
    static class StandardJoint implements Joint{
        protected boolean workdone;
        @Override
        public boolean mount() {
             return true;
        }

        @Override
        public void execute() {
        }

        @Override
        public void abort() {
        }

        @Override
        public void success() {
        }

        @Override
        public boolean done() {
            return workdone;
        }
        
    }
    
    static class JointA extends StandardJoint{
        public void execute() {
            System.out.println("Hello from JointA");
            workdone = true;
        }
    }

    static class JointB extends StandardJoint{
        public void execute() {
            System.out.println("Hello from JointB");
            workdone = true;
        }
    }
    
    static class JointC extends StandardJoint{
        public void execute() {
            System.out.println("Hello from JointC");
            G.state = C.STATE_SUCCESS;
            workdone = true;
        }
    }
    
    
    static HashMap<String, Joint> getCatalog(){
        HashMap<String, Joint> catalog = new HashMap<>();
        
        catalog.put("jointA", new JointA());
        catalog.put("jointB", new JointB());
        catalog.put("jointC", new JointC());
        
        return catalog;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String source = "jointA jointB JointX jointC";
        
        new Builder(getCatalog())
                .build(source)
                .execute();
    }
}
