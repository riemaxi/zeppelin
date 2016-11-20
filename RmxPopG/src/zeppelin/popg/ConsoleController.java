/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin.popg;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import zeppelin.Parameter;

/**
 *
 * @author Samuel
 */
public class ConsoleController implements Initializable {
    
    @FXML
    TextArea monitor;
    @FXML
    TextArea interpreter;
    @FXML
    Pane view;
    @FXML
    Pane xaxis;
    @FXML
    Pane yaxis;
    @FXML
    AnchorPane background;
    
    Machine machine;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        machine = new Machine(interpreter, monitor, view, background, xaxis, yaxis);
    }
    
    @FXML
    void processCommand(KeyEvent ev ){
        if (ev.getCode() == KeyCode.ENTER){
            String[] list = interpreter.getText().split("\n");
            machine.execute(list[list.length-1]);
        }
    }
    
}
