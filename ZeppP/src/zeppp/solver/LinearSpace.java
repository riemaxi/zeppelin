/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppp.solver;

import java.math.BigInteger;
import zeppp.core.IntegerVariable;
import zeppp.core.Space;
import zeppp.core.Util;

/**
 *
 * @author Samuel
 */
public class LinearSpace implements Space{

    public final IntegerVariable[] x;
    public LinearSpace(IntegerVariable[] x){
        this.x = x;
    }
    
    protected LinearSpace split_low( int i){
        IntegerVariable[] newx = Util.iclone(x);
        newx[i].set(newx[i].min(), middle(newx[i].min(), newx[i].max(), 0) );
        
        return new LinearSpace(newx);        
    }
    
    protected LinearSpace split_high(int i){
        IntegerVariable[] newx = Util.iclone(x);
        newx[i].set(middle(newx[i].min(), newx[i].max(), 1), newx[i].max() );
        
        return new LinearSpace(newx);
    }
    
    protected BigInteger middle(Number a, Number b, int shift){
        BigInteger x = (BigInteger)a;
        BigInteger y = (BigInteger)b;
        return x.add(   x.subtract(y).abs().
                        divide(BigInteger.valueOf(2))).
                        add(BigInteger.valueOf(shift));
    }
    
    
    public LinearSpace low(){
        for(int i=0; i<x.length; i++){
            if (x[i].get() == null)
                return split_low(i);
        }
        return this;
    }

    public LinearSpace high(){
        for(int i=0; i<x.length; i++){
            if (x[i].get() == null)
                return split_high(i);
        }
        return this;
    }
    
    
    @Override
    public boolean isFixed() {
        for(IntegerVariable v:x)
            if (v.get() == null)
                return false;
        
        return true;
    }
    
}
