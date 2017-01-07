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
public interface Propagator<S extends Space, Constraint, Solution> {
    void propagate(Solver parent, S space, Constraint constraint);
}
