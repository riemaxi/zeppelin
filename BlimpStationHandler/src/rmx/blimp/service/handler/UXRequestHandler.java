/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service.handler;

import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import rmx.Parameter;

/**
 *
 * @author Samuel
 */
public class UXRequestHandler extends StandardHandler{
    private String scripts;
    
    private String getContent(String name){
        try(BufferedReader reader = new BufferedReader(new FileReader(scripts + name))){
            StringBuffer content = new StringBuffer();
            reader.lines().forEach(l -> content.append(String.format("%s%n", l)));
            return content.toString();
        }catch(Exception e){
            return e.toString();
        }
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
        String script = getParams(he).get("script");
        send(he, "hello from UX ... " + getContent(script + ".bsh") );
    }

    @Override
    public void init(Parameter p) {
        this.path = p.s("rmx.blimp.handler.ux.path");
        this.scripts = p.s("rmx.blimp.handler.ux.script");
    }
    
}
