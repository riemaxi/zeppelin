/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin.popg;

import java.util.Arrays;

/**
 *
 * @author Samuel
 */
public class Command {
    public String id;
    public String[] args;
    
    public Command(String id, String ... args){
        this.id = id;
        this.args = new String[0];
        if (args.length>1){
            this.args = new String[args.length-1];
            for(int i=0; i<this.args.length; i++)
                this.args[i] = args[i+1];
        }
    }
}
