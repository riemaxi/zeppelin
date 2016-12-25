/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.client.blimp;

import bsh.Interpreter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 *
 * @author Samuel
 */
public class RootInterpreter {
    private int status;
    private final Interpreter in7r = new Interpreter();
    
    public RootInterpreter(){
        
    }
    
    public RootInterpreter load(String urlpath){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new URL(urlpath).openStream()))){
            status  = 0;
            in7r.eval(br);
            in7r.eval("loaded()");
            return this;
        }catch(Exception e){
            status = 1;
            return null;
        }
    }
    
    public String getUIaddress(){
        try{
            return in7r.eval("ui()").toString();
        }catch(Exception e){
            return null;
        }
    }
    
    public Object getController(){
        try{
            return in7r.eval("controller()");
        }catch(Exception e){
            return null;
        }
    }
    
    public String init(Object context){
        try{
            in7r.set("context", context);
            in7r.eval("init()");
            return "";
        }catch(Exception e){
            return e.toString();
        }               
    }
    
    public int getStatus(){
        return status;
    }
}
