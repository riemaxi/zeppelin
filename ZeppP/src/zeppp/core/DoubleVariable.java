/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppp.core;

import java.math.BigDecimal;

/**
 *
 * @author Samuel
 */
public class DoubleVariable extends NumericVariable {

    public DoubleVariable() {
        this(BigDecimal.valueOf(Double.MIN_EXPONENT), BigDecimal.valueOf(Double.MAX_EXPONENT));
    }

    public DoubleVariable(double min, double max) {
        super(BigDecimal.valueOf( min), BigDecimal.valueOf(max));
    }
    
    public DoubleVariable(BigDecimal min, BigDecimal max) {
        super(min, max);
    }
    
}
