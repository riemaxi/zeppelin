/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


/**
 *
 * @author Samuel
 */
public class ConsoleController implements Initializable {

    @FXML
    TextArea monitor;
    @FXML
    TextArea interpreter;
    
    private Machine machine;
    
    @FXML
    void processCommand(KeyEvent ev) {
        if (ev.getCode() == KeyCode.ENTER){
            machine.execute(getCommand());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        machine = new Machine(interpreter, monitor);
    }    
    
    protected String getCommand(){
        String[] str = interpreter.getText().split("\n");
        return str[str.length-1].trim();
    }

}
