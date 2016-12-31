
import java.util.Timer;
import java.util.TimerTask;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Samuel
 */

class Player extends TimerTask{
    private int generations;
    private int generation;
    private Object data;
    private Timer timer;
    private int speed = 100;
    public Player(){
        
    }
    
    public void updateData(int newg, Object newd){
        generation = newg;
        data = newd;
    }
    
    public void start(int generations){
        this.generations = generations;
        generation = 0;
        timer = new Timer(true);
        timer.scheduleAtFixedRate(this, 0, speed);
    }
    
    public void stop(){
        timer.cancel();
    }
    
    @Override
    public void run() {
        if (generation < generations)
            System.out.println(data);
        else
            stop();
    }
    
}

public class Main {

    static void play(Player player){
        player.start(10);
        
        for(int i=0; i<10; i++){
            
            try{
                player.updateData(i,"data " + i);
                Thread.sleep(200);
            }catch(Exception e){
                
            }
        }
        
        player.stop();
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[] a = {2,3,5};
        double[] b = a.clone();
        
        a[0] = 10;
        for(int i=0; i<a.length; i++)
            System.out.println(a[i] + "---" + b[i]);
    }
    
}
