/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.client.blimp;

import bsh.Interpreter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Samuel
 */
public class RootInterpreter {
    private int status;
    private final Interpreter in7r = new Interpreter();
    private String ui;
    
    public RootInterpreter(){
        
    }
    
    protected void extractUI(String source) throws Exception{
        String uitag = in7r.eval("ui()").toString();
        String begintag = "/*" + uitag;
        String endtag = uitag + "*/";
        
        BufferedReader br = new BufferedReader(new StringReader(source));
        String line = br.readLine();
        while(line != null){
            if (begintag.equals(line.trim()))
                break;
            line = br.readLine();
        }

        StringBuffer uib = null;
        if(line != null){
          uib = new StringBuffer();
          line = br.readLine();
          while(line != null){
              line = line.trim();
              if (line.equals(endtag))
                  break;
              uib.append(uib.length()>0?"\n":"").append(line);
              line = br.readLine();
          }
        }
        ui = uib != null && line.equals(endtag) ? uib.toString() : null;
    }
    
    public RootInterpreter load(String urlpath){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new URL(urlpath).openStream()))){
            String source = br.lines().reduce("", (sum,s) -> sum.isEmpty() ? s : String.format("%s%n%s", sum, s));
            status  = 0;
            in7r.eval(source);
            in7r.eval("loaded()");
            extractUI(source);
            return this;
        }catch(Exception e){
            System.out.println(e);
            status = 1;
            return null;
        }
    }
    
    public String getUIaddress(){
        try{
            return in7r.eval("uiaddress()").toString();
        }catch(Exception e){
            return null;
        }
    }

    
    public String getUI(){
        return ui;
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
