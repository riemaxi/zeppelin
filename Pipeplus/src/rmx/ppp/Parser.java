/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 *
 * @author Samuel
 */
public class Parser {
    public interface Builder{
        void open(String token);
        void close(String token);
        boolean symbol(String name);
        void error(String token, String message);
    }
    
    private String langpattern = "[a-zA-Z_][a-zA-Z0-9]+|[{}\\[\\]\\(\\)]";
    private HashMap<String, String> pair;
    
    public Parser(){
        pair = new HashMap<>();
        pair.put("]","[");
        pair.put(")","(");
        pair.put("}","{");
    }
    
    protected String normalize(String source){
        return source
                .replace("(", " ( ")
                .replace(")", " ) ")
                .replace("[", " [ ")
                .replace("]", " ] ")
                .replace("{", " { ")
                .replace("}", " } ");
    }
    
    protected boolean pop(String token, Stack<String> stack, Builder builder){
        if (stack.isEmpty() || !pair.get(token).equals(stack.peek())){
            builder.error(token, P.s("ui.message.error.close_symbol_unmatching"));
            return true;
        }
        
        stack.pop();
        builder.close(token);
        return false;
    }
    
    public void load(String source, Builder builder){
        Stack<String> stack = new Stack();
        source = normalize(source);
        Pattern pattern = Pattern.compile(langpattern);
        try(Scanner scanner = new Scanner(new ByteArrayInputStream(source.getBytes(P.s("encoding","UTF-8"))))){
            boolean error = false;
            String token = "";
            while(scanner.hasNext() && !error){
                token = scanner.next(pattern);
                switch(token){
                    case "[" : stack.push(token); builder.open(token); break;
                    case "]" : error = pop(token, stack, builder); break;
                    case "(" : stack.push(token); builder.open(token); break;                        
                    case ")" : error = pop(token, stack, builder); break;
                    case "{" : stack.push(token); builder.open(token); break;
                    case "}" : error = pop(token, stack, builder); break;
                    default : error = builder.symbol(token);
                }
            }
            
            if (!stack.isEmpty())
                builder.error(token, P.s("ui.message.error.unmatched_open_symbols", "Unmatched open symbols"));
            
        }catch(Exception e){
        }
    }
}
