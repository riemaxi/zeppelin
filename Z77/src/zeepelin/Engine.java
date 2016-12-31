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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Samuel
 */
public class Engine {
    static class Point{
        double x;
        double y;
        public Point(double x, double y){
            this.x = x;
            this.y = y;
        }
    }
    
    static class Segment{
        Point point;
        Color color;
        double width = 1;
        public Segment(double x, double y, Color color){
            this.point = new Point(x, y);
            this.color = color;
        }
        
        public Segment(Segment s){
            this(s.point.x, s.point.y, s.color);
        }
        
        void set(Segment s){
            this.point = new Point(s.point.x, s.point.y);
            this.color = new Color(s.color.getRed(),s.color.getGreen(),s.color.getBlue(),s.color.getOpacity());
        }

        void set(Color c){
            this.color = new Color(c.getRed(),c.getGreen(),c.getBlue(),c.getOpacity());
        }
    }
    
    static class Track{
        List<Segment> segments = new ArrayList<>();
        void setColor(Color c){
            for (Segment s: segments)
                s.set(c);
        }
    }
    
    static class EvolutionGraph{
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
    private EvolutionGraph graph;
    private int speed = 3;
    private double xscale;
    private double yscale;
    
    public Engine(Canvas varea){
        this.varea = varea;
        bmargin = AnchorPane.getBottomAnchor(varea).doubleValue();
        rmargin = AnchorPane.getRightAnchor(varea).doubleValue();
        graph = new EvolutionGraph();
    }
    
    public void reset(){
        populations =  G.p.i("populations",30);        
        generations = G.p.i("generations",1200);
        
        lastFreq = DoubleStream.generate(()->G.p.d("initFreq")).limit(populations).toArray();
        
        fixedColor = Color.GREEN;
        lostColor = Color.RED;
        defaultColor = Color.BLUE;
        
        graph.reset();
        Stream
                .generate(() -> new Track())
                .limit(populations)
                .forEach( track -> graph.tracks.add(track));
        
        setScale();        
    }
    
    protected void setScale(){
       xscale = varea.getWidth()/(generations-1);
       yscale = varea.getHeight();
    }
    
    public void adjust(Number o, Number n, boolean width){
        if (width)
            varea.setWidth(n.doubleValue() - 2*rmargin);
        else
            varea.setHeight(n.doubleValue() - 2*bmargin);

        setScale();
        
        paint(0);        
    }
    
    protected void paintTracks(int genr){
        GraphicsContext gc = varea.getGraphicsContext2D();
        graph
                .tracks
                .stream()
                .forEach(track -> {
                    IntStream
                            .range(genr, track.segments.size()-1)
                            .forEach(i -> {
                                Segment a = track.segments.get(i);
                                Segment b = track.segments.get(i+1);
                                gc.setStroke(a.color);
                                gc.setLineWidth(a.width);
                                gc.strokeLine(a.point.x * xscale ,a.point.y * yscale, b.point.x * xscale, b.point.y * yscale);
                            });
                    });
    }
    
    protected void clearBackground(){
        GraphicsContext gc = varea.getGraphicsContext2D();
        gc.setFill(Color.valueOf(G.p.s("gui.background.color",C.BACKGROUND)));
        gc.fillRect(0, 0, varea.getWidth(), varea.getHeight());
    }
    
    protected void paint(int genr){
        clearBackground();
       paintTracks(genr);
    }
    
    protected void addGeneration(Simulator.Generation g){
                    double[] freq = g.frequency;

                    IntStream
                            .range(0, populations)
                            .parallel()
                            .forEach(i -> {
                                Track track = graph.tracks.get(i);
                                if (freq[i]>0){
                                    track.segments.add(new Segment(g.genr,  1-freq[i], defaultColor));
                                    
                                    if (freq[i] == 1)
                                        track.setColor(fixedColor);
                                }else{
                                   if (lastFreq[i]!=0){
                                        track.segments.add(new Segment(g.genr,1-freq[i], lostColor));
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
        Player(){}
        
        Player(Function<Integer, Simulator.Generation> genf){
            this.genf = genf;
            start();
        }

        public void start(Function<Integer, Simulator.Generation> genf){
            clearBackground();
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
                    paintTracks(0);
                    genr++;
                    
                    //if all either fixed or lost then stop playing
                    if (DoubleStream.of(lastFreq).filter(f -> 0<f && f<1).count() == 0){
                        stop();
                    }
                }
            }
            else{
                stop();
                paintTracks(0);
            }

            counter = (counter+1) % speed;
        }
    };
    
    private Player player = new Player();
    
    public void play(Function<Integer, Simulator.Generation> genf){
        player.start(genf);        
    }
}
