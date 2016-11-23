/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin.popg;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import zeppelin.Parameter;

/**
 *
 * @author Samuel
 */
public class Machine {
    private final View view;
    private final Monitor monitor;
    private final Interpreter interpreter;
    private final Background background;
    private final Stats stats;
    
    public Machine(
            TextArea intArea, 
            TextArea monArea, 
            Pane viewArea,
            AnchorPane backArea,
            Pane xaxis,
            Pane yaxis){
        view = new View(viewArea);
        monitor = new Monitor(monArea, G.p.getValues("gui.cmd."));
        interpreter = new Interpreter(intArea);
        background = new Background(backArea, xaxis, yaxis);
        stats = new Stats();
        
        viewArea.widthProperty().addListener((v,ov,nv) -> wredraw(ov, nv));
        viewArea.heightProperty().addListener((v,ov,nv) -> hredraw(ov, nv));
    }
    
    protected void wredraw(Number ov, Number nv){
        view.wredraw(ov, nv);
        background.drawXaxis(ov,nv);
    }

    protected void hredraw(Number ov, Number nv){
        view.hredraw(ov, nv);
        background.drawYaxis(ov,nv);
    }
    
    public void execute(String command){
        Command cmd = interpreter.process(command);
        switch(cmd.id){
            case C.CMD_HINT : monitor.display(G.p.getValues("gui.cmd.")); break;
            case C.CMD_CLEAR : monitor.clear(); break;
            case C.CMD_SIM : monitor.display("running...");  runSimulator(); break;
            case C.CMD_STATS : monitor.display(getStats()); break;
            case C.CMD_SET : monitor.display(setParameter(cmd)); break;
            case C.CMD_GET : monitor.display(getParameter(cmd)); break;
            default: monitor.display(cmd.id);
        }
    }
     
    //Instructions
     protected String getParameter(Command cmd){
         return cmd.args.length>0 ? 
                 String.format("%s=%s", cmd.args[0], G.p.s(cmd.args[0])) : 
                 G.p.toString();
     }
     
     protected String setParameter(Command cmd){
         if (cmd.args.length>1 && G.p.containsKey(cmd.args[0])){
             G.p.put(cmd.args[0], cmd.args[1]);
             return String.format("%s=%s", cmd.args[0], G.p.s(cmd.args[0]));
         }
            
         return "";
     }
     
     protected String getStats(){
         return stats.toString();
     }
     
     protected void runSimulator(){
         int run = G.p.i("run",1);
         background.draw();
         view.reset();
         stats.init();
         
         Simulator.run(g -> {
             if (g.runr == run){
                view.addGeneration(g.genr-1, g.frquency);
                stats.gather(g.genr, g.frquency);
             }
         });
         monitor.display(getStats());
     }
}
