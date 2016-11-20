/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin;

import javafx.scene.control.TextArea;

/**
 *
 * @author Samuel
 */
public class Monitor {
    private TextArea varea;
    public Monitor(TextArea varea){
        this.varea = varea;
    }
    
    public void display(Object text){
        varea.setText(String.format("%s%n%s", text, varea.getText()));
    }
    
    public void clear(){
        varea.clear();
    }
}
