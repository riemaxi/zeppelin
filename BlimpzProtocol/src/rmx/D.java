/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx;

import java.util.HashMap;

/**
 *
 * @author Samuel
 */
public class D {
    private static HashMap data;
    static{
        data = new HashMap();
    }
    
    public static Object o(Object key){
        return data.get(key);
    }
    
    public static HashMap o(Object key, Object value){
        data.put(key, value);
        return data;
    }
    
    public static HashMap data(){
        return data;
    }
}
