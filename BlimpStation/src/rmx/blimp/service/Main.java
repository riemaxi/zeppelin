/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.util.function.Consumer;
import java.util.stream.Stream;
import rmx.P;
import rmx.blimp.service.protocol.AdvancedHandler;


/**
 *
 * @author riemaxi
 */
final class Deamon {
    String name;
    HttpServer server;
    public Deamon(String path, int port, HttpHandler handler) throws Exception{
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext(path, handler);
        server.setExecutor(null); // creates a default executor
        server.start();
    }
    
    public Deamon(int port, String name, AdvancedHandler ... handlers ) throws Exception{
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(null); // creates a default executor
        this.name = name;
        
        for(AdvancedHandler h:handlers){
            addHandler(h.getPath(), h);
        }
    }
    
    public void addHandler(String path, HttpHandler handler){
        server.createContext(path, handler);
    }
    
    public void start(){
        server.start();
    }
    
    public void stop(){
        server.stop(0);
    }
}

public class Main{
    static Deamon[] deamons;
    
    static void start(){
        Stream.
                of(deamons).
                forEach(d -> d.start());
                     
    }
    
    static void stop(String name){
        Stream.
                of(deamons).
                filter( d -> "".equals(name) || d.name.equals(name) ).
                forEach( d -> d.stop());
    }
    
    static AdvancedHandler createHandler(String classname) throws Exception{
        AdvancedHandler handler = (AdvancedHandler)Class.forName(classname).newInstance();
        handler.init(P.p());
        return handler;
    }
    
    static void prompt(Consumer<String> c){
        System.out.print("Press any key to stop ...");
        try{
            System.in.read();
        }catch(Exception e){
            
        }
        
        c.accept("");
    }
    
    public static void main(String ... args) throws Exception{
        deamons = new Deamon[]{
          new Deamon(
            P.i("rmx.blimp.handler.ux.port"),
            "ux",                  
            createHandler(P.s("rmx.blimp.handler.ux.class"))
          ),
          new Deamon(
            P.i("rmx.blimp.handler.command.port"),
            "command",
            createHandler(P.s("rmx.blimp.handler.command.class"))
          )
        };
        
        start();
        
        prompt(name -> stop(name));
    }
}