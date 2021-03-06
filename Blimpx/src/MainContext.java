/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.function.Consumer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
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
    public Player player;

    private Simulator sim = new Simulator();
    public void startStreaming(String source, Consumer consumer){
        sim.start(p, generation -> consumer.accept(generation));
    }
    
    public void stopStreaming(){
        sim.stop();
    }
    
    public void startPlayer(int speed, Consumer consumer){
        player.play(speed, consumer);
    }
    
    public void stopPlayer(){
        player.stop();
    }
    
    public Object get(String id){
        return scene.lookup(String.format("#%s",id));
    }
    
    public String load(){
        return load(p.s("home"));
    }
    
    protected void createStyle(){
        File file = new File(ri.getStylePath());
        try(FileWriter fw = new FileWriter(file)){
            fw.write(ri.getStyleContent());
            fw.flush();
            
            root.getStylesheets().clear();
            root.getStylesheets().add("file:///" + file.getAbsolutePath().replace("\\", "/"));
        }catch(Exception e){
        }
    }
    
    public String load(String scriptAddress){
        try{
            ri.load(scriptAddress);

            FXMLLoader loader = null;
            String ui = ri.getUIaddress();
            if (ui != null){
                loader = new FXMLLoader( new URL(ui));
                root = loader.load(); 
            }
            else{
                loader = new FXMLLoader();
                try(BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(ri.getUI().getBytes()))){
                    root = loader.load(bis);
                }catch(Exception e){
                    return e.toString();
                }
            }

            createStyle();
            scene = new Scene(root);
            stage.setScene(scene);

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
        if (error == null){
            stage.show();
            ri.init(this);
        }

        return error;
    }
    
    public MainContext(Stage stage, Parameter p, HashMap data){
        this.stage = stage;
        this.p = p;
        this.data = data;
        player = new Player();
        ri = new RootInterpreter();        
    }
}
