package zeepelin;

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

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
    
    public static final Generation generation = new Generation();
    
    static double[] frequency;
    static Random random;
    
    static int generations;
    static double A_a;
    static double a_A;
    static double sAA;
    static double sAa;
    static double saa;
    static int popSize;
    
    public static void init(){
        A_a = G.p.d("A_a",0.0);
        a_A = G.p.d("a_A",0.0);
        sAA = G.p.d("fAA",1.0);
        sAa = G.p.d("fAa",1.0);
        saa = G.p.d("faa",1.0);
        popSize = G.p.i("popSize",700);
        
        generations = G.p.i("generations",1200);
        
        long seed = G.p.l("seed",-1);
        random = seed >= 0 ? new Random(seed) : new Random();
        
        frequency = DoubleStream
                .generate(() -> G.p.d("initFreq", 0.5))
                .parallel()
                .limit(G.p.i("populations",30))
                .toArray();
    }
    
    public static Generation nextGeneration(int g){
        update();
        return generation.set(g,frequency);
    }
    
    private static  int binomial(int n, double pp){
        return IntStream
                .generate( () -> random.nextFloat() < pp ? 1 : 0)
                .parallel()
                .limit(n)
                .sum();
    }

    protected static double nextFrequency(double p){
            p = (1 - A_a) * p + a_A * (1 - p);

            double q = 1 - p;
            double w = (p * p * sAA) + (2.0 * p * q * sAa) + (q * q * saa);
            
            double pp1 = (p * p * sAA) / w;
            double pp2 = (2.0 * p * q * sAa) / w;

            int nx = binomial(popSize, pp1);
            int ny = pp1 < 1.0 && nx < popSize ? binomial(popSize - nx, (pp2 / (1.0 - pp1))) : 0;

            return ((nx * 2.0) + ny) / (2.0*popSize);
    }
    
    protected static void update(){
        IntStream
                .range(0, frequency.length)
                .parallel()
                .forEach(pop -> frequency[pop] = nextFrequency(frequency[pop]));
    }
}
