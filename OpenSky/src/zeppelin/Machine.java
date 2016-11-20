/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin;

import bsh.Interpreter;
import javafx.application.Platform;
import javafx.scene.control.TextArea;

/**
 *
 * @author Samuel
 */
public class Machine {
    private final Interpreter bsh;
    private final Monitor monitor;
    public Machine(TextArea intArea, TextArea monArea){
        monitor = new Monitor(monArea);
        bsh = new Interpreter();
        bsh.setStrictJava(false);
        
        init();
        
        Platform.runLater(() -> intArea.requestFocus());
    }
    
    protected void init(){
        try{
            bsh.set("monitor", monitor);
            bsh.set("result", "");
        }catch(Exception e){
            monitor.display(e);
        }
    }
    
    public void execute(String command){
        try{
            bsh.eval(command);
            monitor.display( bsh.get("result"));
        }catch(Exception e){
            monitor.display(e);
        }
    }
}
