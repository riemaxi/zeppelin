

import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import rmx.Parameter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Samuel
 */
public class Simulator{
    public static class Generation{
        public int genr;
        public double[] frequency;
        public Generation set(double[] frequency){
            this.frequency = frequency;
            return this;
        }
        
        public Generation set(int genr, double[] frequency){
            this.genr = genr;
            this.frequency = frequency;
            return this;
        }
    }
    
    private final Generation generation = new Generation();
    
    private double[] frequency;
    private Random random;
    
    private int generations;
    private int populations;
    private double A_a;
    private double a_A;
    private double sAA;
    private double sAa;
    private double saa;
    private int popSize;
    private double initFreq;
    
    public void init(Parameter p){
        A_a = p.d("A_a",0.0);
        a_A = p.d("a_A",0.0);
        sAA = p.d("fAA",1.0);
        sAa = p.d("fAa",1.0);
        saa = p.d("faa",1.0);
        popSize = p.i("popSize",50);
        initFreq = p.d("initFreq", 0.5);
        
        populations = p.i("populations",10);
        generations = p.i("generations",100);
        
        long seed = p.l("seed",-1);
        random = seed >= 0 ? new Random(seed) : new Random();
        
        frequency = DoubleStream
                .generate(() -> initFreq)
                .parallel()
                .limit(populations)
                .toArray();
    }
    
    public Generation nextGeneration(int g){
        update();
        return generation.set(g,frequency);
    }
    
    public void start(Parameter p, Consumer<Generation> consumer){
        init(p);
        new Thread(
                ()->{
                    for(int g=0; g<generations; g++ ){
                        consumer.accept(nextGeneration(g));
                    }
                }
        ).start();
    }
    
    private int binomial(int n, double pp){
        return IntStream
                .generate( () -> random.nextFloat() < pp ? 1 : 0)
                .parallel()
                .limit(n)
                .sum();
    }

    protected double nextFrequency(double p){
            p = (1 - A_a) * p + a_A * (1 - p);

            double q = 1 - p;
            double w = (p * p * sAA) + (2.0 * p * q * sAa) + (q * q * saa);
            
            double pp1 = (p * p * sAA) / w;
            double pp2 = (2.0 * p * q * sAa) / w;

            int nx = binomial(popSize, pp1);
            double ny = pp1 < 1.0 && nx < popSize ? binomial(popSize - nx, (pp2 / (1.0 - pp1)))/2.0 : 0;

            return (nx + ny) / popSize; 
    }
    
    protected void update(){
        IntStream
                .range(0, frequency.length)
                .parallel()
                .forEach(pop -> frequency[pop] = nextFrequency(frequency[pop]));
    }
}
