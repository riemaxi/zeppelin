
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Samuel
 */
public abstract class SolutionSource implements Consumer<String>{

    private List<String> solution = new ArrayList<>();
    
    public SolutionSource(){
        solve();
    }
    
    public void solve(){
        try{
            Executors.callable(()->{
                for(long i=0; i<9000000; i++){
                    accept("solution: " + i);
                }
            }).call();
        }catch(Exception e){
            
        }
    }

    @Override
    public abstract void accept(String t);
}
