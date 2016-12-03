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
    
    private final HashMap<String, String> pair;
    private boolean comment;
    
    public Parser(){
        pair = new HashMap<>();
        pair.put(C.PARSER_PAR_OR_CLOSE,C.PARSER_PAR_OR_OPEN);
        pair.put(C.PARSER_PAR_AND_CLOSE,C.PARSER_PAR_AND_OPEN);        
        pair.put(C.PARSER_SERIAL_CLOSE,C.PARSER_SERIAL_OPEN);
    }
    
    protected String normalize(String source){
        return source
                .replace(C.PARSER_SERIAL_OPEN,String.format(" %s ",C.PARSER_SERIAL_OPEN))
                .replace(C.PARSER_SERIAL_CLOSE,String.format(" %s ",C.PARSER_SERIAL_CLOSE))
                .replace(C.PARSER_PAR_OR_OPEN,String.format(" %s ",C.PARSER_PAR_OR_OPEN))
                .replace(C.PARSER_PAR_OR_CLOSE,String.format(" %s ",C.PARSER_PAR_OR_CLOSE))
                .replace(C.PARSER_PAR_AND_OPEN,String.format(" %s ",C.PARSER_PAR_AND_OPEN))
                .replace(C.PARSER_PAR_AND_CLOSE,String.format(" %s ",C.PARSER_PAR_AND_CLOSE))
                .replace(C.PARSER_COMMENT_OPEN,String.format(" %s ",C.PARSER_COMMENT_OPEN))
                .replace(C.PARSER_COMMENT_CLOSE,String.format(" %s ",C.PARSER_COMMENT_CLOSE));
    }
    
    protected void push(String token, Stack<String> stack, Builder builder){
        if (!comment)
            stack.push(token); builder.open(token);
    }
    
    protected boolean pop(String token, Stack<String> stack, Builder builder){
        if (comment)
            return false;
        
        if (stack.isEmpty() || !pair.get(token).equals(stack.peek())){
            builder.error(token, P.s("ui.message.error.close_symbol_unmatching"));
            return true;
        }
        
        stack.pop();
        builder.close(token);
        return false;
    }
    
    protected void open_comment(String token){
        comment = true;
    }

    protected void close_comment(String token){
        comment = false;
    }
    
    protected boolean symbol(String token, Builder builder){
        if (comment)
            return false;
        
        return builder.symbol(token);
    }
    
    public void load(String source, Builder builder){
        comment = false;
        Stack<String> stack = new Stack();
        source = normalize(source);
        Pattern pattern = Pattern.compile(C.PARSER_CONTENT_PATTERN);
        try(Scanner scanner = new Scanner(new ByteArrayInputStream(source.getBytes(P.s("encoding","UTF-8"))))){
            boolean error = false;
            String token = "";
            while(scanner.hasNext() && !error){
                token = scanner.next(pattern);
                switch(token){
                    case C.PARSER_PAR_OR_OPEN :;
                    case C.PARSER_PAR_AND_OPEN :;
                    case C.PARSER_SERIAL_OPEN: push(token, stack, builder); break;        
                    case C.PARSER_PAR_OR_CLOSE :;
                    case C.PARSER_PAR_AND_CLOSE :;
                    case C.PARSER_SERIAL_CLOSE : error = pop(token, stack, builder); break;                        
                    case C.PARSER_COMMENT_OPEN : open_comment(token); break;
                    case C.PARSER_COMMENT_CLOSE : close_comment(token); break;
                    default : error = symbol(token, builder);
                }
            }
            
            if (!stack.isEmpty() || comment)
                builder.error(token, P.s("ui.message.error.unmatched_open_symbols", "Unmatched open symbols"));
            
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
