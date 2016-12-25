/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.client.blimp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rmx.P;
import rmx.Parameter;

/**
 *
 * @author Samuel
 */
public class Main extends Application {
    private RootInterpreter ri;
    
    public Parent root;
    public Scene scene;
    public Stage stage;
    public Parameter p;
    
    public String load(){
        return load(p.s("home"));
    }
    
    public String load(String urladdress){
        try{
            ri.load(urladdress);

            FXMLLoader loader = null;
            String ui = ri.getUIaddress();
            if (ui != null){
                loader = new FXMLLoader( new URL(ui));
                loader.setController(ri.getController());                
                root = loader.load();                                
            }
            else{
                loader = new FXMLLoader();
                try(BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(ui.getBytes()))){
                    loader.setController(ri.getController());
                    root = loader.load(bis);
                }catch(Exception e){
                    return e.toString();
                }
            }
            
            scene = new Scene(root);
            stage.setScene(scene);
            
            ri.init(this);
            stage.show();
           
            return "";
        }catch(Exception e){
            return e.toString();
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        p = P.p();
        ri = new RootInterpreter();
        load();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
