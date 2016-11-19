/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin.popg;

import java.util.Arrays;
import javafx.scene.control.TextArea;

/**
 *
 * @author Samuel
 */
public class Interpreter {
    private TextArea varea;
    public Interpreter(TextArea varea){
        this.varea = varea;
    }
    
    public Command process(String command){
        command = command.trim();
        if (command.endsWith(";")){
            String[] cmd = command.replace(";","").split("\\s+");
            switch(cmd[0]){
                case "hint" : return new Command(C.CMD_HINT);
                case "clear" : return new Command(C.CMD_CLEAR);
                case "get" : return new Command(C.CMD_GET, cmd);
                case "set" : return new Command(C.CMD_SET, cmd);
                case "sim" : return new Command(C.CMD_SIM);
                case "stats" : return new Command(C.CMD_STATS);
            }
        }
        return C.NOP;
    }
}
