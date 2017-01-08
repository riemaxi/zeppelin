/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppp.solver;

import java.util.concurrent.Executors;
import java.util.function.Consumer;
import zeppp.core.Constraint;
import zeppp.core.Splitter;
import zeppp.core.Solver;

/**
 *
 * @author Samuel
 */
public class LinearSolver implements Solver<LinearSpace, LinearSolution>, Splitter<LinearSpace, LinearConstraint, LinearSolution>, Consumer<LinearSolution> {
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
    public Splitter getSplitter() {
        return this;
    }

    @Override
    public void split(Solver parent, LinearSpace space, LinearConstraint constraint) {
        try{
            Executors.callable(() -> lowSplitting(space)).call();
            Executors.callable(() -> highSplitting(space)).call();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void lowSplitting(LinearSpace space){
        new LinearSolver(this, getConstraint(), space.low());
    }
    
    private void highSplitting(LinearSpace space){
        new LinearSolver(this, getConstraint(), space.high());
    }

    public LinearSolver getParent() {
        return parent;
    }

    @Override
    public Consumer<LinearSolution> getSink() {
        return this;
    }

    @Override
    public void accept(LinearSolution s) {
        LinearSolver parent = getParent();
        if (parent != null){
            parent.accept(s);
        }
    }
}
