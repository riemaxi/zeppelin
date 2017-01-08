/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppp.core;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author Samuel
 * @param <S>
 * @param <Solution>
 */
public interface Solver<S extends Space, Solution>{
    Solution getSolution();
    S getSpace();
    Constraint<S> getConstraint();
    Splitter getSplitter();
    Consumer<Solution> getSink();
    
    default  void propagateConstraint(){}
    default  void preprocess(){}
    
    default void sinkSolution(){
        getSink().accept(getSolution());
    }

    default void split(){
        getSplitter().split(this, getSpace(), getConstraint());        
    }
    
    default boolean inside(){
        return getConstraint().holdsFor(getSpace());
    }
    
    default boolean solved(){
        return getSpace().isFixed();
    }
    
    default void solve(){
        preprocess();
        if (inside()){
            propagateConstraint();
            if (solved())
                sinkSolution();
            else
                split();
        }
    }
}
