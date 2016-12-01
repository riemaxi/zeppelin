/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.ppp;

/**
 *
 * @author Samuel
 */
public interface C {
    int STATE_SUCCESS = 1000;
    int STATE_STARTED = 0;
    int STATE_ABORTED = -1000;
    
    String PARAMETER_FILE = "parameter.txt";
    String PARAMETER_COMMENT = "#";
    String PARAMETER_DELIMITER = ":";
}
