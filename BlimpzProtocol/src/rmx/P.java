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

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Samuel
 */

public class P{
    private static Parameter p;
    static{
        p = new Parameter();
    }
    
    public static int i(String key){
        return i(key,0);
    }

    public static int i(String key, int def){
        return p.i(key, def);
    }

    public static long l(String key, long def){
        return p.l(key, def);
    }
    
    public static long l(String key){
        return l(key);
    }
   
    public double d(String key){
        return d(key,0);
    }
    
    public static double d(String key, double def){
        return p.d(key, def);
    }

    public static String s(String key){
        return s(key,"");
    }
    
    public static String s(String key, String def){
        return p.s(key, def);
    }
    
    public static void list(String prefix, Consumer<String> consumer){
        p.list(prefix, consumer);
    }
    
    public static String getValues(String prefix){
        return p.getValues(prefix);
    }

    public static String getString(){
        return p.toString();
    }
    
    public static Parameter p(){
        return p;
    }
}