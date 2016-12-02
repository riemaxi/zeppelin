/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Samuel
 */
public class Builder implements Parser.Builder{
    private List<Joint> line;
    private List<Joint> umlíst;
    private Stack<GroupJoint> stack;
    private HashMap<String, Joint> catalog;
    
    public Builder(HashMap<String, Joint> catalog){
        this.catalog = catalog;
    }
    
    protected GroupJoint getGroupJoint(String token){
        switch(token){
            case C.PARSER_SERIAL_OPEN : return catalog.containsKey(C.SERIAL_JOINT_ID) ? (GroupJoint)catalog.get(C.SERIAL_JOINT_ID) : new SerialJoint();
            case C.PARSER_PAR_OR_OPEN : return catalog.containsKey(C.PAR_OR_JOINT_ID) ? (GroupJoint)catalog.get(C.PAR_OR_JOINT_ID) : new ParallelOrJoint();
            case C.PARSER_PAR_AND_OPEN : return catalog.containsKey(C.PAR_AND_JOINT_ID) ? (GroupJoint)catalog.get(C.PAR_AND_JOINT_ID) : new ParallelAndJoint();
            default: return new GroupJoint();
        }
    }
    
    public Pipeline build(String source){
        stack = new Stack<>();
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
        stack.push(getGroupJoint(token));
    }

    @Override
    public void close(String token) {
        if (!stack.isEmpty())
            line.add(stack.pop());
    }

    @Override
    public boolean symbol(String name) {
        if (catalog.containsKey(name)){
            if (stack.isEmpty())
                line.add(catalog.get(name));
            else
                stack.peek().add(catalog.get(name));
            
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
