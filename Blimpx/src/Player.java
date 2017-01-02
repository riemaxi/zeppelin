
import java.util.function.Consumer;
import java.util.function.Supplier;
import javafx.animation.AnimationTimer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Samuel
 */
public class Player extends AnimationTimer {
    private Consumer endlessHandler = new Consumer<Long>() {

        @Override
        public void accept(Long now) {
            if (counter == 0)
                  consumer.accept(now);
            
            counter = (counter + 1) % speed;
        }
    };

    private Consumer limitedHandler = new Consumer<Long>() {

        @Override
        public void accept(Long now) {
            if (counter == 0){
                if (times>0){
                    consumer.accept(now);
                    times--;
                }else
                    stop();
            }
            counter = (counter + 1) % speed;
        }
    };
    
    private Consumer<Long> handler;
    private int times;
    private int speed;
    private int counter;
    private Consumer consumer;
    
    public void play(int speed, Consumer consumer){
        play(speed,-1,consumer);
    } 
    
    public void play(int speed, int times, Consumer consumer){
        this.speed = speed;
        this.times = times;
        handler = times>0? limitedHandler : endlessHandler;
        counter = 0;
        this.consumer = consumer;
        start();
    }

    @Override
    public void handle(long now) {
        handler.accept(now);
    }
}
