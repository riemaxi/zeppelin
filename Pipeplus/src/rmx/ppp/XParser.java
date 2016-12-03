/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;


class Transition extends HashMap<String, String>{
    private String stateSymbolFormat;
    private String errorState;
    private String initState;
    private String state;    
    public Transition(String initState, String errorState, String stateSymbolFormat){
        this.errorState = errorState;
        this.initState = initState;
        this.state = initState;
        this.stateSymbolFormat = stateSymbolFormat;
    }
    
    public void add(String state, String tokentype, String nextState){
        put(String.format(stateSymbolFormat, state, tokentype), nextState);
    }
    
    public String step(String tokenType){
        String key = String.format(state, tokenType);
        return containsKey(key) ? state = get(key) : errorState;
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
        transition.put(C.PARSER_STATE_START, C.PARSER_STATE_BLOCK_PAR_AND);
        transition.put(C.PARSER_STATE_START, C.PARSER_STATE_BLOCK_PAR_OR);
        transition.put(C.PARSER_STATE_START, C.PARSER_STATE_BLOCK_SERIAL);
        transition.put(C.PARSER_STATE_BLOCK_SERIAL, C.PARSER_STATE_START);
        transition.put(C.PARSER_STATE_BLOCK_PAR_AND, C.PARSER_STATE_START);
        transition.put(C.PARSER_STATE_BLOCK_PAR_OR, C.PARSER_STATE_START);
    }
    
    protected String nextToken(StringBuffer token, InputStreamReader reader){
        try{
            token.setLength(0);
            int ch = reader.read();
            while(ch == '\n' || ch == '\t' || ch == ' '){
                ch = (char)reader.read();
            }
            
            while(ch != -1 && ch != '\n' && ch != '\t' && ch != ' '){
                
                if (C.PARSER_BLOCK_CHARS.indexOf(ch)>=0)
                    return Character.toString((char)ch);
                
                token.append((char)ch);
                ch = reader.read();
            }
            
            return ch == -1 ? null : token.toString();
            
        }catch(Exception e){
            System.out.println(e);
            return null;
        }
    }
    
    
    protected void open(String token, Builder builder){
        
        level++;
    }

    protected void close(String token, Builder builder){
        
        level--;
    }
    
    protected void skipComment(InputStreamReader reader){
    }
    
    protected void symbol(String token, Builder builder){
    }
    
    protected void error(String token, Builder builder){
        builder.error(token, P.s("ui.message.error.unmatched_open_symbols", "Unmatched open symbols"));
    }
    
    public void parse(InputStreamReader reader, Builder builder){
        transition.init();
        StringBuffer token = new StringBuffer();
        level = 0;
        for(    String nxtoken = nextToken(token, reader); 
                transition.ok() && token != null; 
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
        
        if (level > 0)
            error("",builder);
    }
    
}
