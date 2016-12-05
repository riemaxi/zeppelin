/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp.protocol;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Samuel
 */
public class F {
    public static class O{
        public String name;
        public Object item;
    }
    
    public static class C{
        public String name;
        public Class item;
    }
    
    private static Map<String, Object> objects;
    private static Map<String, Class> classes;
    
    public static void init(Map<String, Object> olist, Map<String, Class> clist){
        objects = olist;
        classes = clist;
    }
    
    public static void addObjects(O ... olist){
        for(O o : olist){
            objects.put(o.name, o.item);
        }
    }

    public static void addClasses(C ... clist){
        for(C c : clist){
            classes.put(c.name, c.item);
        }
    }
    
    public static Object o(String name){
        return objects.containsKey(name) ? objects.get(name) : null;
    }
    
    public static Object n(String name){
        try{
            return (classes.containsKey(name)) ? classes.get(name).newInstance() : null;
        }catch(Exception e){
            return null;
        }
    }
    
    public static Object single(String name){
        try{
            if (objects.containsKey(name))
                return objects.get(name);

            if (classes.containsKey(name)){
                Object o = classes.get(name).newInstance();
                objects.put(name, o);
                return o;
            }
            
            return null;
                
        }catch(Exception e){
            return null;
        }
    }
}