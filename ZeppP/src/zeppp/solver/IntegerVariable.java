/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppp.solver;

import java.math.BigInteger;

/**
 *
 * @author Samuel
 */
public class IntegerVariable extends NumericVariable{

    public IntegerVariable(){
        this(BigInteger.valueOf(Long.MIN_VALUE), BigInteger.valueOf(Long.MAX_VALUE));
    }

    public IntegerVariable(long min, long max) {
        this(BigInteger.valueOf(min), BigInteger.valueOf(max));
    }
    
    
    public IntegerVariable(BigInteger min, BigInteger max) {
        super(min, max);
    }
    
    @Override
    public Number get(){
        BigInteger min = (BigInteger)min();
        BigInteger max = (BigInteger)max();
        
        return min.compareTo(max)>=0? min : null;
    }
    
    
    @Override
    public IntegerVariable clone() {
        return new IntegerVariable(new BigInteger( min().toString() ),new BigInteger(max().toString()));
    }
    
}
