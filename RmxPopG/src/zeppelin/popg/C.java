/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin.popg;

/**
 *
 * @author Samuel
 */
public interface C {
    String CMD_SIM = "simulator";
    String CMD_STATS = "stats";
    String CMD_SET = "set";
    String CMD_GET = "get";
    String CMD_CLEAR = "clear";
    String CMD_HINT = "hint";
    
    Command NOP = new Command("nop");
}
