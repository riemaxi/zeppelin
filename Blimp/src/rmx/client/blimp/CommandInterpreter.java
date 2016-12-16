/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.client.blimp;

import bsh.Interpreter;

/**
 *
 * @author Samuel
 */
public class CommandInterpreter {
    private final Interpreter in7er;
    
    public CommandInterpreter(){
        in7er = new Interpreter();
    }
    
    public Object load(String path){
        try{
            return in7er.source(path);
        }catch(Exception e){
            return e;
        }
    }
    
    public String execute(String command){
        try{
            String[] args = command.split(" ",2);
            Object result = null;
            if (args.length>1)
                result = in7er.eval(String.format("%s(%s)", args[0], args[1] ) );
            else
                result = in7er.eval(String.format("%s()", command ) );

            return result.toString();
        }catch(Exception e){
            return e.toString();
        }
    }
}
