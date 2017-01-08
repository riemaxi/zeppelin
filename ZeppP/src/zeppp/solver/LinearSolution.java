/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppp.solver;

/**
 *
 * @author Samuel
 */
public class LinearSolution {
    public LinearSolver solver;
    public IntegerVariable[] x;
    
    public LinearSolution(LinearSolver solver, IntegerVariable[] x){
        this.solver = solver;
        this.x = x;
    }
}
