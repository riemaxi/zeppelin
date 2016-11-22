/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin.popg;

import java.text.DecimalFormat;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;


/**
 *
 * @author Samuel
 */
public class Background {
    private final Pane xaxis;
    private final Pane yaxis;
    private final AnchorPane varea;
    public Background(AnchorPane varea, Pane xaxis, Pane yaxis ){
        this.varea = varea;
        this.xaxis = xaxis;
        this.yaxis = yaxis;
    }
    
    public void draw(){
        drawXaxis(xaxis.getPrefWidth(),xaxis.getPrefWidth());
        drawYaxis(yaxis.getPrefHeight(),yaxis.getPrefHeight());
    }
    
    public void draw(Number ov, Number nv){
        drawXaxis(ov,nv);
        drawYaxis(ov,nv);
    }
    
    public void drawXaxis(Number ow, Number nw){
        xaxis.getChildren().clear();
        xaxis.setPrefWidth(nw.doubleValue());
        int gens = G.p.i("generations");
        int step = gens/5;
        double scale = xaxis.getPrefWidth()/gens;
        for(int i=1; i<=gens; i+=step){
            Text text = new Text(String.format("%s", i));
            text.setY(xaxis.getPrefHeight() - 8);
            text.setX(scale * i);
            xaxis.getChildren().add(text);
        }
        
        //draw line
        Line line = new Line(0,4, xaxis.getPrefWidth(), 4);
        line.setStroke(Color.BLACK);
        xaxis.getChildren().add(line);
    }
    
    protected void drawYaxis(Number oh, Number nh){
        yaxis.getChildren().clear();
        yaxis.setPrefHeight(nh.doubleValue());
        double scale = yaxis.getPrefHeight()/10;
        for(int i=1; i<=9; i++){
            String str = String.format("0.%s",i);
            Text text = new Text(str);
            text.setX(8);
            text.setY(yaxis.getPrefHeight() - i*scale);
            yaxis.getChildren().add(text);
        }
        
        //draw line
        Line line = new Line(yaxis.getPrefWidth(),0, yaxis.getPrefWidth(), yaxis.getPrefHeight());
        line.setStroke(Color.BLACK);
        yaxis.getChildren().add(line);
    }
}
