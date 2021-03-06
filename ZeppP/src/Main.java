
import java.math.BigInteger;
import zeppp.solver.IntegerVariable;
import zeppp.solver.LinearConstraint;
import zeppp.solver.LinearSolution;
import zeppp.solver.LinearSolver;
import zeppp.solver.LinearSpace;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Samuel
 */
public class Main {
    public static  void print(IntegerVariable[] x){
        for(IntegerVariable v : x)
            System.out.print(v.get() + "  ");
        
        System.out.println();
    }
    
    public static void main(String ... args) throws Exception{
        IntegerVariable[] x = {new IntegerVariable(-4,6), new IntegerVariable(-5,12), new IntegerVariable(-3,4)};
       
        BigInteger[] c = { BigInteger.valueOf(1),BigInteger.valueOf(1),BigInteger.valueOf(2)};
        
        new LinearSolver(new LinearConstraint(c),new LinearSpace(x)){
            public void accept(LinearSolution s){
                print(s.x);
            }
        };
        
    }
}
