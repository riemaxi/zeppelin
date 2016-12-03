/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;


class Transition extends HashMap<String, String>{
    private String stateSymbolFormat;
    private String errorState;
    private String initState;
    public String state;
    public String message;
    
    public Transition(String initState, String errorState, String stateSymbolFormat){
        this.errorState = errorState;
        this.initState = initState;
        this.state = initState;
        this.stateSymbolFormat = stateSymbolFormat;
    }
    
    public void add(String state, String tokentype, String nextState){
        put(String.format(stateSymbolFormat, state, tokentype), nextState);
    }
    
    public void set(String state, String message){
        this.message = message;
        this.state = state;
    }
    
    public String step(String tokenType, String message){
        String key = String.format(stateSymbolFormat , state, tokenType);
        state = containsKey(key) ? get(key) : errorState;
        if (state.equals(errorState))
            this.message = message;
        return state;
    }
    
    public void init(){
        state = initState;
    }
    
    public boolean ok(){
        return !state.equals(errorState);
    }
}
/**
 *
 * @author Samuel
 */
public class XParser {
        public interface Builder{
        void open(String token);
        void close(String token);
        boolean symbol(String name);
        void error(String token, String message);
    }
        
    private Transition transition;
    private int level;
    
    public XParser(){
        transition = new Transition(C.PARSER_STATE_START,C.PARSER_STATE_ERROR , "%s_%s");
        transition.add(C.PARSER_STATE_START,C.PARSER_SYMBOL, C.PARSER_STATE_START);
        transition.add(C.PARSER_STATE_START,C.PARSER_PAR_AND_OPEN,  C.PARSER_STATE_BLOCK_PAR_AND);
        transition.add(C.PARSER_STATE_START,C.PARSER_PAR_OR_OPEN,  C.PARSER_STATE_BLOCK_PAR_OR);        
        transition.add(C.PARSER_STATE_START,C.PARSER_SERIAL_OPEN,  C.PARSER_STATE_BLOCK_SERIAL);
        
        //State: Block par and
        transition.add(C.PARSER_STATE_BLOCK_PAR_AND,C.PARSER_PAR_AND_OPEN,  C.PARSER_STATE_BLOCK_PAR_AND);
        transition.add(C.PARSER_STATE_BLOCK_PAR_AND,C.PARSER_SYMBOL,  C.PARSER_STATE_BLOCK_PAR_AND);
        transition.add(C.PARSER_STATE_BLOCK_PAR_AND,C.PARSER_PAR_AND_CLOSE,  C.PARSER_STATE_START);
        
        //State: Block par or
        transition.add(C.PARSER_STATE_BLOCK_PAR_OR,C.PARSER_PAR_OR_OPEN,  C.PARSER_STATE_BLOCK_PAR_OR);
        transition.add(C.PARSER_STATE_BLOCK_PAR_OR,C.PARSER_SYMBOL,  C.PARSER_STATE_BLOCK_PAR_OR);
        transition.add(C.PARSER_STATE_BLOCK_PAR_OR,C.PARSER_PAR_OR_CLOSE,  C.PARSER_STATE_START);
        
        //State: Block serial
        transition.add(C.PARSER_STATE_BLOCK_SERIAL,C.PARSER_SERIAL_OPEN,  C.PARSER_STATE_BLOCK_SERIAL);
        transition.add(C.PARSER_STATE_BLOCK_SERIAL,C.PARSER_SYMBOL, C.PARSER_STATE_BLOCK_SERIAL);
        transition.add(C.PARSER_STATE_BLOCK_SERIAL,C.PARSER_SERIAL_CLOSE,  C.PARSER_STATE_START);
    }
    
    private int leftBehind;
    protected String nextToken(StringBuffer token, InputStreamReader reader){
        if (leftBehind != -10){
            String next = Character.toString((char)leftBehind);
            leftBehind = -10;
            return next;
        }
        
        try{
            token.setLength(0);
            int ch = reader.read();
            while(ch == '\n' || ch == '\t' || ch == ' '){
                ch = (char)reader.read();
            }
            
            while(ch != -1 && ch != '\n' && ch != '\t' && ch != ' ' && C.PARSER_BLOCK_CHARS.indexOf(ch)==-1){
                token.append((char)ch);
                ch = reader.read();
            }
            
            if (C.PARSER_BLOCK_CHARS.indexOf(ch)>=0){
                if (token.length()>0){
                    leftBehind = ch;
                    return token.toString();
                }else
                    return Character.toString((char)ch);
            }
            
            return ch == -1 ? (token.length()>0 ? token.toString() : null) : token.toString();
            
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    protected void open(String token, Builder builder){
        transition.step(token, C.PARSER_ERROR_UNEXPECTED);
        
        if (transition.ok()){
            level++;            
            builder.open(token);
        }
    }

    protected void close(String token, Builder builder){
        if (level==0){
            transition.set(C.PARSER_STATE_ERROR, C.PARSER_ERROR_BLOCK_NOT_CLOSED);
        }
        
        transition.step(token, C.PARSER_ERROR_UNEXPECTED);
        if (transition.ok()){
            level--;
            builder.close(token);
        }
    }
    
    protected void skipComment(InputStreamReader reader){
        try{
            int ch = reader.read();
            char commentclose = C.PARSER_COMMENT_CLOSE.charAt(0);
            while(ch != -1 && ch != commentclose)
                ch = reader.read();
            
            if (ch == -1)
                transition.set(C.PARSER_STATE_ERROR, C.PARSER_ERROR_COMMENT_NOT_CLOSED);
        }catch(Exception e){
            transition.set(C.PARSER_STATE_ERROR, C.PARSER_ERROR_UKNOWN);
        }
    }
    
    protected void symbol(String token, Builder builder){
        transition.step(C.PARSER_SYMBOL, C.PARSER_ERROR_UNEXPECTED);
        if (transition.ok())
            if (builder.symbol(token))
                transition.set(C.PARSER_STATE_ERROR, C.PARSER_ERROR_SYMBOL_NOT_FOUND);
    }
    
    protected void error(String token, String message, String defMessage, Builder builder){
        builder.error(token, P.s(message, defMessage));
    }
    
    protected void checkError(String token, Builder builder){
        if (!transition.ok())
            error(token, transition.message, transition.message, builder);
        else
            if (level > 0)
                error("","ui.message.error.block_open","Block open",builder);
    }
    
    public void parse(String source, Builder builder){
        try(InputStreamReader reader = new InputStreamReader(new ByteArrayInputStream(source.getBytes()))){
            parse(reader, builder);
        }catch(Exception e){
        }
    }
    
    public void parse(InputStreamReader reader, Builder builder){
        leftBehind = -10;
        String nxtoken;
        transition.init();
        StringBuffer token = new StringBuffer();
        level = 0;
         for(   nxtoken = nextToken(token, reader); 
                nxtoken != null && transition.ok(); 
                nxtoken = nextToken(token, reader)){
             
                switch(nxtoken){
                    case C.PARSER_PAR_OR_OPEN :;
                    case C.PARSER_PAR_AND_OPEN :;
                    case C.PARSER_SERIAL_OPEN: open(nxtoken, builder); break;        
                    case C.PARSER_PAR_OR_CLOSE :;
                    case C.PARSER_PAR_AND_CLOSE :;
                    case C.PARSER_SERIAL_CLOSE : close(nxtoken, builder); break;                        
                    case C.PARSER_COMMENT_OPEN : skipComment(reader); break;
                    default : symbol(nxtoken, builder);
                }
                
        }
         
        checkError(nxtoken, builder);
     }
    
}
