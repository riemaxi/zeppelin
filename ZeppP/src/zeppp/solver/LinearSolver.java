/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppp.solver;

import java.util.concurrent.Executors;
import java.util.function.Consumer;
import zeppp.core.Constraint;
import zeppp.core.Propagator;
import zeppp.core.Solver;

/**
 *
 * @author Samuel
 */
public class LinearSolver implements Solver<LinearSpace, LinearSolution>, Propagator<LinearSpace, LinearConstraint, LinearSolution> {
    private LinearSpace space;
    private LinearConstraint constraint;
    private LinearSolver parent;

    public LinearSolver(LinearConstraint constraint, LinearSpace space){
        this(null, constraint, space);
    }
    
    public LinearSolver(LinearSolver parent, LinearConstraint constraint, LinearSpace space){
        this.parent = parent;
        this.constraint = constraint;
        this.space = space;
        
        solve();
    }
    
    @Override
    public LinearSolution getSolution() {
        return new LinearSolution(this, getSpace().x);
    }

    @Override
    public LinearSpace getSpace() {
        return space;
    }

    @Override
    public LinearConstraint getConstraint() {
       return constraint;
    }

    @Override
    public Propagator getPropagator() {
        return this;
    }

    @Override
    public void propagate(Solver parent, LinearSpace space, LinearConstraint constraint) {
        try{
            Executors.callable(() -> lowPropagation(space)).call();
            Executors.callable(() -> highPropagation(space)).call();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void lowPropagation(LinearSpace space){
        new LinearSolver(this, getConstraint(), space.low());
    }
    
    private void highPropagation(LinearSpace space){
        new LinearSolver(this, getConstraint(), space.high());
    }

    /*@Override
    public void accept(LinearSolution t) {
        if (parent != null)
            parent.accept(t);
    }*/

    @Override
    public Solver getParent() {
        return parent;
    }
}
