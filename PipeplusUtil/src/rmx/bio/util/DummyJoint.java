/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.bio.util;

import rmx.ppp.Joint;

/**
 *
 * @author Samuel
 */
public class DummyJoint implements Joint {
    private boolean workdone;
    public DummyJoint(){
        this(false);
    }
    public DummyJoint(boolean workdone){
        this.workdone = workdone;
    }
    @Override
    public boolean mount() {
        return true;
    }

    @Override
    public void execute() {
    }

    @Override
    public void abort() {
    }

    @Override
    public void success() {
    }

    @Override
    public boolean done() {
        return workdone;
    }
    
}
