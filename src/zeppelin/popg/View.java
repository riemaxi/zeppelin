/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin.popg;

import java.util.function.Consumer;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

/**
 *
 * @author Samuel
 */
public class View{
    private Pane varea;
    private int populations;
    private int generations;
    private Color fixedColor;
    private Color lostColor;
    private Color defaultColor;
    
    public View(Pane varea){
        this.varea = varea;
        
        this.varea.widthProperty().addListener((v,ov,nv) -> wredraw(ov, nv));
        this.varea.heightProperty().addListener((v,ov,nv) -> hredraw(ov, nv));
    }
    
    protected void wredraw(Number ov, Number nv){
        varea.setPrefWidth(nv.intValue());
        scaleChart(ov.doubleValue(), nv.doubleValue(), varea.getPrefHeight(), varea.getPrefHeight());
    }

    protected void hredraw(Number ov, Number nv){
        varea.setPrefHeight(nv.intValue());
        scaleChart(varea.getPrefWidth(), varea.getPrefWidth(), ov.doubleValue(), nv.doubleValue());
    }
    
    protected void scaleChart(double owidth, double width, double oheight, double height){
        for(Node n : varea.getChildren()){
            Polyline p = (Polyline)n;
            ObservableList<Double> points = p.getPoints();
            for(int i=0; i<points.size(); i+=2){
                double v = points.get(i);
                points.set(i, v*width/owidth);
                
                v = points.get(i+1);
                points.set(i+1, v*height/oheight);
            }
        }
    }
    
    
    public void reset(){
        populations = G.p.i("populations");        
        generations = G.p.i("generations");
        
        fixedColor = Color.GREEN;
        lostColor = Color.RED;
        defaultColor = Color.BLUE;
        
        varea.getChildren().clear();
        for(int i=0; i<populations; i++){
            Polyline p = new Polyline();
            p.setStroke(defaultColor);
            p.setStrokeWidth(1);
            p.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                Polyline pol = (Polyline)e.getTarget();
                pol.setStrokeWidth(4);} 
            );
            p.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                Polyline pol = (Polyline)e.getTarget();
                pol.setStrokeWidth(1);
            });
                
            varea.getChildren().add(p);
        }
        
    }
    
    public void addGeneration(int genr, double[] freq){
        ObservableList<Node> nodes = varea.getChildren();
        for(int i=0; i<freq.length; i++){
            Node n = nodes.get(i);
            Polyline pol = (Polyline)n;
            if (freq[i]>0){
                pol.getPoints().add( (genr * varea.getPrefWidth()) / (generations-1));
                pol.getPoints().add((1-freq[i]) * varea.getPrefHeight());
                if (freq[i] == 1)
                    pol.setStroke(fixedColor);
            }else
               pol.setStroke(lostColor);
        }
        
        
    }
}
