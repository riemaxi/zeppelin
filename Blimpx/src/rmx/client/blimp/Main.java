/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.client.blimp;

import javafx.application.Application;
import javafx.stage.Stage;
import rmx.D;
import rmx.P;

/**
 *
 * @author Samuel
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MainContext context = new MainContext(stage, P.p(), D.data());
        context.loadAndshow();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
