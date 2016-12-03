/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.bio;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import rmx.bio.util.AnnotationBuilder;
import rmx.bio.util.DummyJoint;
import rmx.ppp.Builder;
import rmx.ppp.C;
import rmx.ppp.G;
import rmx.ppp.Joint;
import rmx.ppp.XParser;

/**
 *
 * @author Samuel
 */
public class Main {
    static class StandardJoint implements Joint{
        protected boolean workdone;
        @Override
        public boolean mount() {
            workdone = false;
            return true;
        }

        @Override
        public void execute() {
        }

        @Override
        public void abort() {
            workdone = true;
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
            workdone = true;
        }
    }

    static class JointEnd extends StandardJoint{
        private int duration = 3;
        public void execute() {
            workdone = duration == 0;
            if (workdone){
                G.state = C.STATE_SUCCESS;
                System.out.println("End ...");
            }else
                System.out.println("Still working ..." + duration);
            
            duration--;
        }
    }
    
    static class JointLoop extends StandardJoint{
        private int duration;
        private int timeleft;
        private String name;
        public JointLoop(String name, int duration){
            this.name = name;
            this.duration = duration;
            this.timeleft = duration;
        }
        
        protected void pause(){
            try{
                Thread.sleep(500);
            }catch(Exception e){
                
            }
        }
        
        @Override
        public void abort(){
            timeleft = duration;
            super.abort();
        }
        
        @Override
        public boolean mount(){
            timeleft = duration;
            return super.mount();
        }
        
        @Override
        public void execute() {
            while (!workdone && timeleft>0){
                System.out.println("Loop " + name + ", time left: " + timeleft);
                pause();
                timeleft--;
            }
            workdone = true;
        }
    }

    
    static HashMap<String, Joint> getCatalog(){
        HashMap<String, Joint> catalog = new HashMap<>();
        
        catalog.put("jointA", new JointA());
        catalog.put("jointB", new JointB());
        catalog.put("jointC", new JointC());
        catalog.put("loopA", new JointLoop("A", 5));
        catalog.put("loopB", new JointLoop("B", 8));
        catalog.put("loop", new DummyJoint());
        catalog.put("jointEnd", new JointEnd());        
        
        return catalog;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //String source = " jointA (  [ jointB]    {jointC} )";
        //String source = "jointA jointB jointC";
        //String source = "(jointB jointC)";
        String source = "jointA [loop loopA loopB] <this ### is the end of\n --- this pipeline\n......>\n jointEnd";
        
        //new AnnotationBuilder().build(source, l -> System.out.println(l + "annotation for " + l.replace("@","").replace(":","")  + " \nand more"),"loopA", "loopB","jointA","loop","jointEnd");
        
        /*new Builder(getCatalog())
                .build(source)
                .execute();*/
        

        XParser.Builder builder = new XParser.Builder() {

            @Override
            public void open(String token) {
            }

            @Override
            public void close(String token) {
            }

            @Override
            public boolean symbol(String name) {
                return false;
            }

            @Override
            public void error(String token, String message) {
            }
        };
        
        new XParser().parse(new InputStreamReader(new ByteArrayInputStream(source.getBytes())) , builder);
    }
}
