/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx;

/**
 *
 * @author Samuel
 */
public interface C {
    int STREAM_PORT = 9999;
    String STREAM_WEBSOCKET_END = "/websocket";
    
    int UX_PORT = 8888;
    String UX_WWW = ".";
    int UX_MAX_CONTENTSIZE = 65536;

    int ADMIN_PORT = 7777;
    String ADMIN_WWW = "admin";
    int ADMIN_MAX_CONTENTSIZE = 65536;
    
    String COMMAND_QUIT = "quit";
    
    int X_PORT = 7777;
}
