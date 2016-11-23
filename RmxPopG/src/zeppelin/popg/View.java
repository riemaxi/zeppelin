/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin.popg;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;

/**
 *
 * @author Samuel
 */
public class View{
    private Pane varea;
    private int populations;
    private int generations;
    private Color fixedColor;
    private Color defaultColor;
    private Color lostColor;
    private double[] lastFreq;
    
    public View(Pane varea){
        this.varea = varea;
    }
    
    public void wredraw(Number ov, Number nv){
        varea.setPrefWidth(nv.intValue());
        scaleChart(ov.doubleValue(), nv.doubleValue(), varea.getPrefHeight(), varea.getPrefHeight());
    }

    public void hredraw(Number ov, Number nv){
        varea.setPrefHeight(nv.intValue());
        scaleChart(varea.getPrefWidth(), varea.getPrefWidth(), ov.doubleValue(), nv.doubleValue());
    }
    
    protected void scaleChart(double owidth, double width, double oheight, double height){
        varea.getChildren()
                .stream()
                .forEach(n -> {
                    Polyline p = (Polyline)n;
                    ObservableList<Double> points = p.getPoints();
                    IntStream
                            .range(0, points.size()/2)
                            .forEach(i -> {
                                double v = points.get(2*i);
                                points.set(2*i, v*width/owidth);

                                v = points.get(2*i+1);
                                points.set(2*i+1, v*height/oheight);

                            });
                });
    }
    
    public void reset(){
        populations = G.p.i("populations");        
        generations = G.p.i("generations");
        
        lastFreq = DoubleStream.generate(()->G.p.d("initFreq")).limit(populations).toArray();
        
        fixedColor = Color.GREEN;
        lostColor = Color.RED;
        defaultColor = Color.BLUE;
        
        varea.getChildren().clear();
        IntStream
                .range(0,populations)
                .forEach( i ->{
                    Polyline p = new Polyline();
                    p.setStroke(defaultColor);
                    p.setStrokeWidth(1);
                    p.setStrokeLineCap(StrokeLineCap.ROUND);
                    p.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                        Polyline pol = (Polyline)e.getTarget();
                        pol.setStrokeWidth(4);} 
                    );
                    p.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                        Polyline pol = (Polyline)e.getTarget();
                        pol.setStrokeWidth(1);
                    });

                    varea.getChildren().add(p);
                });
    }
    
    public void addGeneration(int genr, double[] freq){
        ObservableList<Node> nodes = varea.getChildren();
        IntStream
                .range(0, freq.length)
                .parallel()
                .forEach(i -> {
                    Polyline pol = (Polyline)nodes.get(i);
                    if (freq[i]>0){
                        pol.getPoints().add( (genr * varea.getPrefWidth()) / (generations-1));
                        pol.getPoints().add((1-freq[i]) * varea.getPrefHeight());
                        if (freq[i] == 1)
                            pol.setStroke(fixedColor);
                    }else{
                       if (lastFreq[i]!=0){
                            pol.getPoints().add( (genr * varea.getPrefWidth()) / (generations-1));
                            pol.getPoints().add(varea.getPrefHeight());
                           
                            pol.setStroke(lostColor);
                       }
                    }
            });
        lastFreq = Arrays.copyOf(freq, freq.length);
    }
    
}
