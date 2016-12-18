/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service.handler;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import rmx.Parameter;

/**
 *
 * @author Samuel
 */
public class CommandRequestHandler extends StandardHandler{
    @Override
    public void handle(HttpExchange he) throws IOException {
        send(he, "hello from command");
    }

    @Override
    public void init(Parameter p) {
        this.path = p.s("rmx.blimp.handler.command.path");
    }
    
}
