/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.bio.util;

import java.util.HashMap;
import java.util.function.Consumer;
import rmx.ppp.Builder;
import rmx.ppp.Joint;

/**
 *
 * @author Samuel
 */
public class AnnotationBuilder{
    public AnnotationBuilder(){
    }
    
    protected HashMap<String, Joint> getCatalog(Consumer<String> reporter, String ... symbols){
        HashMap<String, Joint> catalog = new HashMap<>();
        for(String symbol: symbols)
            catalog.put(symbol, new AnnotationJoint(symbol, reporter));
        
        return catalog;
    }

    public void build(String source, Consumer<String> reporter,  String ... symbols){
        new Builder(getCatalog(reporter,symbols))
                .build(source)
                .execute(1);
    }
}
