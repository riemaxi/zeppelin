/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp.protocol;

/**
 *
 * @author Samuel
 */

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Samuel
 */
class Parameter extends HashMap<String, String> {
    public Parameter(){
        this("");
    }
    
    public Parameter(String prefix){
        init(C.PARAMETER_FILE,C.PARAMETER_COMMENT,C.PARAMETER_DELIMITER,prefix.trim());
    }

    
    public Parameter(String path, String comment, String del, String prefix){
        init(path,comment, del, prefix);
    }
    
    private void init(String path, String comment, String del, String prefix){
        try(Scanner sc = new Scanner(new File(path))){
            while(sc.hasNext()){
                String line = sc.nextLine().trim();
                if (!line.startsWith(comment) && line.length()>0){
                    String[] pair = line.split(del, 2);
                    String key = pair[0].trim();
                    if (key.startsWith(prefix)){
                        String value = pair.length>1 ? pair[1].trim() : "";
                        put(key.replace(prefix, ""),value);
                    }
                 }
            }
        }catch(Exception e){
        }
    }

    public int i(String key){
        return i(key,0);
    }

    public double d(String key){
        return d(key,0);
    }
    
    public int i(String key, int def){
        try{
            return Integer.parseInt( get(key) );
        }catch(Exception e){
            return def;
        }
    }

    public long l(String key, long def){
        try{
            return Long.parseLong( get(key) );
        }catch(Exception e){
            return def;
        }
    }
    
    public long l(String key){
        return l(key);
    }
    
    public double d(String key, double def){
        try{
            return Double.parseDouble( get(key) );
        }catch(Exception e){
            return def;
        }
    }

    public String s(String key){
        return s(key,"");
    }
    
    public String s(String key, String def){
        try{
            return containsKey(key)? get(key) : def;
        }catch(Exception e){
            return def;
        }
    }
    
    public String getValues(String prefix){
        StringBuilder str = new StringBuilder();
        keySet().stream().filter((key) -> (key.startsWith(prefix))).forEach((key) -> {
            str.append(str.length()>0 ? "\n" : "").append(get(key));
        });
        
        return str.toString();
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        keySet().stream().forEach((key) -> {
            str.append(str.length()>0 ? "\n" : "").append(String.format("%s=%s",key,get(key)));
        });
        
        return str.toString();
    }
}

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
    
    public static String getValues(String prefix){
        return p.getValues(prefix);
    }

    public static String getString(){
        return p.toString();
    }
}