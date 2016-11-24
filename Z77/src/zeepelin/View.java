/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeepelin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;

/**
 *
 * @author Samuel
 */
public class View {
    static class Point{
        double _x;
        double _y;
        double scx = 1;
        double scy = 1;
        public Point(double x, double scx, double y, double scy){
            this._x = x;
            this._y = y;
            this.scx = scx;
            this.scy = scy;
        }
        
        void scale(double scx, double scy){
            this.scx = scx;
            this.scy = scy;
        }
        
        double x(){
            return _x * scx;
        }
        double y(){
            return _y * scy;
        }
    }
    static class Segment{
        Point point;
        Color color;
        double width = 1;
        public Segment(double x, double scx, double y, double scy, Color color){
            this.point = new Point(x, scx, y, scy);
            this.color = color;
        }
        
        public Segment(Segment s){
            this(s.point._x, s.point.scx, s.point._y, s.point.scy, s.color);
        }
        
        void set(Segment s){
            this.point = new Point(s.point._x, s.point.scx, s.point._y, s.point.scy);
            this.color = new Color(s.color.getRed(),s.color.getGreen(),s.color.getBlue(),s.color.getOpacity());
        }

        void set(Color c){
            this.color = new Color(c.getRed(),c.getGreen(),c.getBlue(),c.getOpacity());
        }
        
        void scale(double scx, double scy){
            point.scale(scx,scy);
        }
    }
    
    static class Track{
        List<Segment> segments = new ArrayList<>();
        void setColor(Color c){
            for (Segment s: segments)
                s.set(c);
        }
    }
    
    static class Evolution{
        List<Track> tracks = new ArrayList<>();
        void reset(){
            tracks.clear();
        }
    }
    
    private Canvas varea;
    private double bmargin;
    private double rmargin;
    
    private int populations;
    private int generations;
    private Color fixedColor;
    private Color defaultColor;
    private Color lostColor;
    private double[] lastFreq;
    private Evolution evolution;
    private int speed = 100;
    
    public View(Canvas varea){
        this.varea = varea;
        bmargin = AnchorPane.getBottomAnchor(varea).doubleValue();
        rmargin = AnchorPane.getRightAnchor(varea).doubleValue();
        evolution = new Evolution();

    }
    
    public void reset(){
        populations =  G.p.i("populations",30);        
        generations = G.p.i("generations",1200);
        
        lastFreq = DoubleStream.generate(()->G.p.d("initFreq")).limit(populations).toArray();
        
        fixedColor = Color.GREEN;
        lostColor = Color.RED;
        defaultColor = Color.BLUE;
        
        evolution.reset();
        Stream
                .generate(() -> new Track())
                .limit(populations)
                .forEach( track -> evolution.tracks.add(track));
    }
    
    protected void adjustTracks(){
        evolution
                .tracks
                .stream()
                .forEach(track -> {
                    track
                            .segments
                            .stream()
                            .forEach(segment -> {
                               segment.scale(varea.getWidth()/(generations-1),varea.getHeight());
                            });
                });
    }
    
    public void adjust(Number o, Number n, boolean width){
        if (width)
            varea.setWidth(n.doubleValue() - 2*rmargin);
        else
            varea.setHeight(n.doubleValue() - 2*bmargin);

        adjustTracks();
        paint();        
    }
    
    protected void paintTracks(){
        GraphicsContext gc = varea.getGraphicsContext2D();
        evolution
                .tracks
                .stream()
                .forEach(track -> {
                    if (track.segments.size()>1){
                        Segment a = new Segment(track.segments.get(0));
                        for(int i=1; i<track.segments.size(); i++){
                            Segment b = track.segments.get(i);
                            gc.setStroke(a.color);
                            gc.setLineWidth(a.width);
                            gc.strokeLine(a.point.x(),a.point.y(),b.point.x(), b.point.y());
                            
                            a.set(b);
                        }
                    }
                });
    }
    
    protected void paint(){
        GraphicsContext gc = varea.getGraphicsContext2D();
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(0, 0, varea.getWidth(), varea.getHeight());
        
       paintTracks();
    }
    
    protected void addGeneration(Simulator.Generation g){
                    double[] freq = g.frequency;

                    IntStream
                            .range(0, populations)
                            .parallel()
                            .forEach(i -> {
                                Track track = evolution.tracks.get(i);
                                if (freq[i]>0){
                                    track.segments.add(new Segment(g.genr, varea.getWidth()/(generations-1), 
                                                                    1-freq[i], varea.getHeight(), defaultColor));
                                    
                                    if (freq[i] == 1)
                                        track.setColor(fixedColor);
                                }else{
                                   if (lastFreq[i]!=0){
                                        track.segments.add(new Segment(g.genr, varea.getWidth() / (generations-1), 
                                                                    1-freq[i], varea.getHeight(), lostColor));
                       
                                        track.setColor(lostColor);
                                   }
                                }
                            });
                    
                    lastFreq = Arrays.copyOf(freq, freq.length);
    }
    

    private class Player extends AnimationTimer {
        int genr = 0;
        long counter;
        Function<Integer, Simulator.Generation> genf;
        Player(Function<Integer, Simulator.Generation> genf){
            this.genf = genf;
            start();
        }

        public void start(Function<Integer, Simulator.Generation> genf){
            this.genf = genf;
            genr = 0;
            counter = 0;
            super.start();
        }
        
        @Override
        public void handle(long now) {
            if (genr<generations){
                if (counter == 0){
                    addGeneration(genf.apply(genr));
                    paint();
                    genr++;
                }
            }
            else{
                this.stop();
                paint();
            }

            counter %= speed;
        }
    };
    
    private Player player;
    
    public void play(Function<Integer, Simulator.Generation> genf){
        if (player == null)
            player = new Player(genf);
        else
            player.start(genf);        
    }
}
