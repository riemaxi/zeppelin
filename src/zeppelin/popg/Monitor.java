/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin.popg;

import javafx.scene.control.TextArea;

/**
 *
 * @author Samuel
 */
public class Monitor {
    private TextArea varea;
    public Monitor(TextArea varea, String hint){
        this.varea = varea;
        display(hint);
        this.varea.textProperty().addListener((ob,o,n) -> {
            varea.selectPositionCaret(varea.getLength());
            varea.deselect();
        });
    }
    public void display(Object text){
        varea.setText( String.format("%s%s%n", text, varea.getText()) );
    }
    
    public void clear(){
        varea.clear();
    }
}
