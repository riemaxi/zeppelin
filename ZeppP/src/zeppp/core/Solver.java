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
 */
public interface Solver<S extends Space, Solution> extends Consumer<Solution> {
    Solution getSolution();
    S getSpace();
    Constraint<S> getConstraint();
    Splitter getSplitter();
    Solver getParent();
    
    default  void propagateConstraint(){}
    default  void preprocess(){}

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
                accept(getSolution());
            else
                split();
        }
    }
    
    //Consumer interface
    default void accept(Solution s){
        Solver parent = getParent();
        if (parent != null)
            parent.accept(s);
    }
}
