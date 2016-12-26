/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service;

import java.io.BufferedInputStream;
import java.util.Scanner;
import rmx.P;
import rmx.C;

/**
 *
 * @author Samuel
 */
public class Main {
    static StreamServer ss;
    static UXServer uxs;
    
    static void prompt(String commandQuit){
        try(Scanner sc = new Scanner(new BufferedInputStream(System.in))){
            System.out.println("UX service listening on port " + uxs.port);
            System.out.print(">");
            for(    String command = sc.nextLine(); 
                    !commandQuit.equals(command); 
                    command = sc.nextLine()){
                
                System.out.print(">");
            }
           
        }catch(Exception e){
            
        }
    }
    
    public static void main(String[] args){
        //ss = new StreamServer(P.i("stream.port", C.STREAM_PORT ), P.s("stream.websocketend", C.STREAM_WEBSOCKET_END));
        uxs = new UXServer(P.i("ux.port", C.UX_PORT ),P.i("ux.maxcontentsize", C.UX_MAX_CONTENTSIZE),  P.s("ux.www",C.UX_WWW));
        
        //ss.start();
        uxs.start();
        
        prompt(P.s("command.quit", C.COMMAND_QUIT));
        
        uxs.stop();
        //ss.stop();
    }
    
}
