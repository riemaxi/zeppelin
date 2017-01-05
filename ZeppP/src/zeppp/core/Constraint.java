/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppp.core;

/**
 *
 * @author Samuel
 */
public interface Constraint<S extends Space> {
    boolean holdsFor(S space);
}
