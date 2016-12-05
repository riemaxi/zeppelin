/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp.protocol;

/**
 *
 * @author Samuel
 */
public interface Joint {
    boolean mount();
    boolean execute();
    void abort();
    void success();
    boolean done();
}
