/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.client.blimp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import rmx.P;

/**
 *
 * @author Samuel
 */
public class Console implements Initializable {

    @FXML
    AnchorPane root;
    @FXML
    SplitPane splitter;
    @FXML
    TextArea view;
    @FXML
    TextArea command;
    
    CommandInterpreter interpreter;
    
    @FXML
    void processCommand(KeyEvent ev){
        if (ev.getCode() == KeyCode.ENTER){
            String[] list = command.getText().split("\n");
            execute(list[list.length-1]);
        }
    }

    private void execute(String command){
        view.setText(String.format("%s%n%s",interpreter.execute(command), view.getText()));
    }
    
    private URL getSourceURL(String urlstr){
        try{
           return new URL(urlstr);
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(() -> command.requestFocus());
        interpreter = new CommandInterpreter();
        
        interpreter.load( getSourceURL(P.s("script")) );
    }    
}
