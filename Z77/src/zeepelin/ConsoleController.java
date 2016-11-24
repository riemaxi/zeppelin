/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeepelin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Samuel
 */
public class ConsoleController implements Initializable {
    
    @FXML
    AnchorPane frame;
    @FXML
    Canvas view;
    View v;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        v = new View(view);
        
        frame.heightProperty().addListener((ob,o,n) -> v.adjust(o, n, false ));
        frame.widthProperty().addListener((ob,o,n) -> v.adjust(o, n, true ));
    }
    
    @FXML
    void mouseClicked(){
        runSimulation();
    }
    
    protected void runSimulation(){
        v.reset();
        Simulator.init();
        v.play(Simulator::nextGeneration);
    }
    
}
