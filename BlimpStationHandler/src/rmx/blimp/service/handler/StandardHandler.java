/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.blimp.service.handler;

/**
 *
 * @author Samuel
 */
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import rmx.Parameter;
import rmx.blimp.service.protocol.AdvancedHandler;
/**
 *
 * @author riemaxi
 */
public class StandardHandler implements AdvancedHandler{

    protected String path;

     public StandardHandler(){
        this("/");
    }
     
    public String hexDecode(String data){
        StringBuilder str = new StringBuilder();
         String[] chars = data.split(",");
        for(int i=0; i<chars.length; i++){
            str.append((char)Integer.parseInt(chars[i]));
        }
        return str.toString();
    }
    
    public String hexEncode(String data){
        StringBuilder str = new StringBuilder();
        for(int  i=0; i<data.length(); i++){
            str.append(i==0?"":",").append(data.codePointAt(i));
        }
        return str.toString();
    }
     
    protected HashMap<String, String> getParams(HttpExchange t){
        return getParams(t.getRequestURI().toString());
    }
     
    protected HashMap<String, String> getParams(String request){
        request = request.replaceAll("^" + path, "");
        String[] pairs = request.split("/");
        HashMap<String, String> params = new HashMap<String, String>();
        for(String str : pairs){
            String[] pair = str.split("=");
            params.put(pair[0], pair.length>1 ? pair[1] : "");
        }
        
        return params;
    }

    
    public StandardHandler(String path){
        this.path = path;
    }
    
    protected void send(HttpExchange t, String response){
        try{
            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch(Exception e){
            
        }
    }
    
    @Override
    public void handle(HttpExchange he) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public void init(Parameter p) {
    }
}
