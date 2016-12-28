/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.client.blimp;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.HashMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rmx.Parameter;

/**
 *
 * @author Samuel
 */
public class MainContext {
    private RootInterpreter ri;
    
    public HashMap data;
    public Parent root;
    public Scene scene;
    public Stage stage;
    public Parameter p;
    
    public String load(){
        return load(p.s("home"));
    }
    
    public String load(String scriptAddress){
        try{
            ri.load(scriptAddress);

            FXMLLoader loader = null;
            String ui = ri.getUIaddress();
            if (ui != null){
                loader = new FXMLLoader( new URL(ui));
                loader.setController(ri.getController());                
                root = loader.load();                                
            }
            else{
                loader = new FXMLLoader();
                try(BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(ri.getUI().getBytes()))){
                    loader.setController(ri.getController());
                    root = loader.load(bis);
                }catch(Exception e){
                    return e.toString();
                }
            }
            
            scene = new Scene(root);
            stage.setScene(scene);
            
            ri.init(this);
           
            return null;
        }catch(Exception e){
            return e.toString();
        }
    }
    
    public Object loadAndshow(){
        return loadAndshow(p.s("home"));
    }
    
    public Object loadAndshow(String scriptaddress){
        Object error = load(scriptaddress);
        if (error == null)
            stage.show();

        return error;
    }
    
    public MainContext(Stage stage, Parameter p, HashMap data){
        this.stage = stage;
        this.p = p;
        this.data = data;
        ri = new RootInterpreter();        
    }
}
