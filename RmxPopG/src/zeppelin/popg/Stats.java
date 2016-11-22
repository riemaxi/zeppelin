/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zeppelin.popg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Samuel
 */
public class Stats {
    protected double[] frequency;
    protected int[] lossGen;
    protected int[] fixGen;
    
    public void init(){
        fixGen = new int[G.p.i("generations")];
        lossGen = new int[G.p.i("populations")];
    }
    
    public void gather(int genr, double[] freq){
        frequency = Arrays.copyOf(freq, freq.length);
        
        for(int i=0; i<freq.length; i++){
            if (freq[i]==1 && fixGen[i] == 0)
                fixGen[i] = genr;
           if (freq[i]==0 && lossGen[i] == 0)
               lossGen[i] = genr;
        }
    }

    protected long getAveDestiny(int[] list){
        return Math.round( IntStream
                .of(list)
                .filter(n -> n>0)
                .average()
                .orElse(0));
    }
    
    
    protected String getDestinyString(int[] list){
        StringBuffer str = new StringBuffer();
        IntStream
                .of(list)
                .filter(n -> n>0)
                .sorted()
                .forEachOrdered( a -> str.append(str.length()>0 ? "," : "").append(a));
        
        return str.toString();
    }

    @Override
    public String toString(){
        long fixed =  DoubleStream.of(frequency).mapToInt(v -> v==1.0 ? 1 : 0 ).sum();
        long lost =  DoubleStream.of(frequency).mapToInt(v -> v==0.0 ? 1 : 0 ).sum();
        
        return String.format("fixed: %s%nlost: %s%nfix generations: %s%nfix ave: %s%nloss generations: %s%nloss ave: %s", 
                fixed, lost, getDestinyString(fixGen), getAveDestiny(fixGen), getDestinyString(lossGen), getAveDestiny(lossGen));
    }
}
