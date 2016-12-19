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
    private String type;
    
    private String getContent(String name){
        try(BufferedReader reader = new BufferedReader(new FileReader(scripts + name + type))){
            return reader.lines().reduce("", (sum,s) -> sum.isEmpty() ? s : String.format("%s%n%s", sum, s));
        }catch(Exception e){
            return e.toString();
        }
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
        String script = getParams(he).get("script");
        send(he, getContent(script) );
    }

    @Override
    public void init(Parameter p) {
        this.path = p.s("rmx.blimp.handler.ux.path");
        this.scripts = p.s("rmx.blimp.handler.ux.script");
        this.type = p.s("rmx.blimp.handler.ux.scripttype");
    }
    
}
