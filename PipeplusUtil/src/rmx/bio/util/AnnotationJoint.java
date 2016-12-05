/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmx.bio.util;

import java.util.function.Consumer;
import java.util.function.Function;
import rmx.ppp.protocol.*;

/**
 *
 * @author Samuel
 */
public class AnnotationJoint implements Joint{

    private String label;
    private String prefix;
    private String suffix;
    private Consumer<String> reporter;
    public AnnotationJoint(String label, Consumer<String> reporter){
        this("@",":", label, reporter);
    }
    
    public AnnotationJoint(String prefix, String suffix, String label, Consumer<String> reporter){
        this.prefix = prefix;
        this.suffix = suffix;
        this.label = label;
        this.reporter = reporter;
    }
    @Override
    public boolean mount() {
        return true;
    }

    @Override
    public boolean execute() {
        reporter.accept(String.format("%s%s%s",prefix, label, suffix));
        return false;
    }

    @Override
    public void abort() {
    }

    @Override
    public void success() {
    }

    @Override
    public boolean done() {
        return true;
    }
    
}
