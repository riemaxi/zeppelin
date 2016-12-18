/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service.protocol;
import com.sun.net.httpserver.HttpHandler;
import rmx.P;
import rmx.Parameter;

/**
 *
 * @author riemaxi
 */
public interface AdvancedHandler extends HttpHandler {
   String UNKNOWN_ACTION = "UNKNOWN";
    
   String getPath();
   
   void init(Parameter p);
}
