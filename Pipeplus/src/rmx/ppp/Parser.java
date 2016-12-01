/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
    
    private Pattern pattern;
    
    public void load(String source, Builder builder){
        try(
        Scanner scanner = new Scanner(new ByteArrayInputStream(source.getBytes(P.s("encoding","UTF-8"))))){
            boolean error = false;
            while(scanner.hasNext() && !error){
                //String token = scanner.next(pattern);
                String token = scanner.next();
                switch(token){
                    case "[" : builder.open(token); break;
                    case "]" : builder.close(token); break;
                    case "(" : builder.open(token); break;                        
                    case ")" : builder.close(token); break;
                    case "{" : builder.open(token); break;
                    case "}" : builder.close(token); break;
                    default : error = builder.symbol(token);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
