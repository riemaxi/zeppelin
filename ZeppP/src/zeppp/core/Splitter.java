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
public interface Splitter<S extends Space, C extends Constraint, Solution> {
    void split(Solver parent, S space, C constraint);
}
