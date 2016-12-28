/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service;

import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import rmx.P;
import rmx.C;
import rmx.StreamSource;

/**
 *
 * @author Samuel
 */
public class Main {
    static StreamService ss;
    static UXService uxs;
    static ExplorerService xs;
    static AdminService adms;
    
    static void start(Service ... services){
        for(Service s: services){
            s.start( __ -> System.out.println(s.name + " stopped") );
            System.out.println(s.name + " service listening on port " + s.port);
        }
    }

    static void stop(Service ... services){
        for(Service s: services){
            System.out.println(s.name + " service closing");
            s.stop( __ -> System.out.println(s.name + " service closed"));
        }
    }
    
    static void prompt(String commandQuit){
        try(Scanner sc = new Scanner(new BufferedInputStream(System.in))){
            System.out.print(">");
            for(    String command = sc.nextLine(); 
                    !commandQuit.equals(command); 
                    command = sc.nextLine()){
                
                System.out.print(">");
            }
           
        }catch(Exception e){
            
        }
    }
    
    static HashMap<String, StreamSource> getSources(){
       HashMap<String, StreamSource> sources = new HashMap<>();        
       P.list("stream.source.", str -> {
           String[] entry = str.split(" ");
           try{
                StreamSource source = (StreamSource)Class.forName(entry[1]).newInstance();
                sources.put(entry[0], source);
           }catch(Exception e){
               System.out.println(e);
           }
       });
       
       return sources;
    }
    
    public static void main(String[] args){
        //ss = new StreamService(P.i("stream.port", C.STREAM_PORT), getSources(), P.s("stream.websocketend", C.STREAM_WEBSOCKET_END));
        uxs = new UXService(P.i("ux.port", C.UX_PORT ),P.i("ux.maxcontentsize", C.UX_MAX_CONTENTSIZE),  P.s("ux.www",C.UX_WWW));
        //xs = new ExplorerService(P.i("x.port", C.X_PORT));
        //adms = new AdminService(P.i("admin.port", C.ADMIN_PORT ),P.i("admin.maxcontentsize", C.ADMIN_MAX_CONTENTSIZE),  P.s("admin.www",C.ADMIN_WWW));
        
        start(uxs);
        
        prompt(P.s("command.quit", C.COMMAND_QUIT));
        
        stop(uxs);
    }
    
}
