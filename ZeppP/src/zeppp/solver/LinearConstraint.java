/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppp.solver;

import java.math.BigInteger;
import zeppp.core.Constraint;

/**
 *
 * @author Samuel
 */
public class LinearConstraint implements Constraint<LinearSpace>{

    private BigInteger[] c;
    public LinearConstraint(BigInteger ... c){
        this.c = c;
    }
    
    @Override
    public boolean holdsFor(LinearSpace space) {
        BigInteger sum = BigInteger.ZERO;
       for(int i=0; i<Math.min(c.length, space.x.length); i++){
           BigInteger xv = (BigInteger)space.x[i].min();
           sum = sum.add(xv.multiply(c[i]));
           
           if (sum.compareTo(BigInteger.ZERO)>0)
               return false;
       }
       
       return true;
        
    }
    
}
