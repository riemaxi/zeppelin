/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.client.blimp;

import bsh.Interpreter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.function.Consumer;

/**
 *
 * @author Samuel
 */
public class CommandInterpreter {
    private final Interpreter in7er;
    
    public CommandInterpreter(){
        in7er = new Interpreter();
    }
    
    public Object load(String path){
        try{
            return in7er.source(path);
        }catch(Exception e){
            return e;
        }
    }

    public Object load(URL url){
        try{
            return in7er.eval(new InputStreamReader(url.openStream()) );
        }catch(Exception e){
            System.out.println(e);
            return e;
        }
    }
    
    
    public Object load(String path, Consumer<String> closure){
        Object result = null;
        try{
            result = in7er.source(path);
        }catch(Exception e){
        }
        
        closure.accept(path);
        return result;
    }
    
    
    public String execute(String command){
        try{
            String[] args = command.split(" ",2);
            Object result = null;
            if (args.length>1)
                result = in7er.eval(String.format("%s(%s)", args[0], args[1] ) );
            else
                result = in7er.eval(String.format("%s()", command ) );

            return result.toString();
        }catch(Exception e){
            return e.toString();
        }
    }
}
