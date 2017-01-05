/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppp.core;

import java.util.function.Consumer;

/**
 *
 * @author Samuel
 */
public interface Solver<S extends Space, Solution> {
    Solution getSolution();
    S getSpace();
    Constraint<S> getConstraint();
    Propagator getPropagator();
    
    default boolean inside(S space, Constraint<S> constraint){
        return constraint.holdsFor(space);
    }
    
    default boolean solved(){
        return getSpace().isFixed() && getConstraint().holdsFor(getSpace());
    }
    
    default void solve(Consumer<Solution> collector){
        if (solved())
            collector.accept(getSolution());
        else
            if (inside(getSpace(), getConstraint()))
                getPropagator().propagate(this, getSpace(), getConstraint(), collector);
    }
}
