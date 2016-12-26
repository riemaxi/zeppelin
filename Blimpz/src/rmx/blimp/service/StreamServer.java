/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service;

/**
 *
 * @author Samuel
 */
public class StreamServer extends Server{
    protected final String websocketEnd;
    public StreamServer(int port, String websocketEnd){
        super(port);
        this.websocketEnd = websocketEnd;
    }
}
