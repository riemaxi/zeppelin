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
public class Util {
    public static IntegerVariable[] iclone(IntegerVariable[] x){
        IntegerVariable[] copy = new IntegerVariable[x.length];
        for(int i=0; i<copy.length; i++)
            copy[i] = x[i].clone();
        return copy;
    }
    
    public static void print(IntegerVariable[] x){
        for(IntegerVariable v : x)
            System.out.print(v.min() + "," + v.max() + "  ");
        
        System.out.println();
    }
    
}
