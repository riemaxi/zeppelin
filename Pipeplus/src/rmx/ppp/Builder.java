/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class Builder implements Parser.Builder{
    private List<Joint> line;
    private List<Joint> umlíst;
    private HashMap<String, Joint> catalog;
    
    public Builder(HashMap<String, Joint> catalog){
        this.catalog = catalog;
    }
    
    public Pipeline build(String source){
        umlíst = new ArrayList<>();
        line = new ArrayList<>();
        Parser parser = new Parser();
        parser.load(source, this);
        
        return new Pipeline(
                line,
                joint -> umlíst.add(joint) );
    }
    
    @Override
    public void open(String token) {
    }

    @Override
    public void close(String token) {
    }

    @Override
    public boolean symbol(String name) {
        if (catalog.containsKey(name)){
            line.add(catalog.get(name));
            return false;
        }

        error(name,P.s("ui.message.error.symbol_not_found","Symbol not found"));
        return true;
    }

    @Override
    public void error(String token, String message) {
        System.out.println(String.format("%s: %s", token, message));
        line.clear();
    }
    
}
