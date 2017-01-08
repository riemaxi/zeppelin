package zeppp.solver;

import zeppp.core.Variable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Samuel
 */
public class NumericVariable implements Variable<Number> {
    private Number min;
    private Number max;
    
    public NumericVariable(Number min, Number max){
        this.min = min;
        this.max = max;
    }
    
    @Override
    public Number get() {
        return null;
    }

    @Override
    public Number min() {
        return min;
    }

    @Override
    public Number max() {
        return max;
    }
    
    @Override
    public void set(Number min, Number max){
        this.min = min;
        this.max = max;
    }

    @Override
    public Variable<Number> clone() {
        return new NumericVariable(min, max);
    }
    
}
