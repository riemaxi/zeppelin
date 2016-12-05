/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp.protocol;

import java.util.HashMap;

/**
 *
 * @author Samuel
 */
class Data extends HashMap<String, Object> {
    public Data(){
    }
    
    public int i(String key){
        return i(key,0);
    }

    public double d(String key){
        return d(key,0);
    }
    
    public int i(String key, int def){
        try{
            return Integer.parseInt( get(key).toString() );
        }catch(Exception e){
            return def;
        }
    }

    public long l(String key, long def){
        try{
            return Long.parseLong( get(key).toString() );
        }catch(Exception e){
            return def;
        }
    }
    
    public long l(String key){
        return l(key);
    }
    
    public double d(String key, double def){
        try{
            return Double.parseDouble( get(key).toString() );
        }catch(Exception e){
            return def;
        }
    }

    public String s(String key){
        return s(key,"");
    }
    
    public String s(String key, String def){
        try{
            return containsKey(key)? get(key).toString() : def;
        }catch(Exception e){
            return def;
        }
    }
    
    public Object o(String key){
        return containsKey(key)? get(key) : null;
    }
}

public class D{
    private static Data data;
    static{
        data = new Data();
    }
    
    public static Object o(String key){
        return data.o(key);
    }
    
    public static int i(String key){
        return i(key,0);
    }

    public static int i(String key, int def){
        return data.i(key, def);
    }

    public static long l(String key, long def){
        return data.l(key, def);
    }
    
    public static long l(String key){
        return l(key);
    }
   
    public double d(String key){
        return d(key,0);
    }
    
    public static double d(String key, double def){
        return data.d(key, def);
    }

    public static String s(String key){
        return s(key,"");
    }
    
    public static String s(String key, String def){
        return data.s(key, def);
    }
}