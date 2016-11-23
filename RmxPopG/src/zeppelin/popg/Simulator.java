package zeppelin.popg;

import java.util.Random;
import java.util.function.Consumer;
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
        public int runr;
        public int genr;
        public double[] frquency;
        public Generation set(int runr, int genr, double[] frequency){
            this.runr = runr;
            this.genr = genr;
            this.frquency = frequency;
            return this;
        }
    }
    
    public static final Generation generation = new Generation();
    
    static double[] frequency;
    static Random random;
    
    static int runs;
    static int generations;
    static double A_a;
    static double a_A;
    static double sAA;
    static double sAa;
    static double saa;
    static int popSize;
    
    public static void init(){
        A_a = G.p.d("A_a");
        a_A = G.p.d("a_A");
        sAA = G.p.d("fAA");
        sAa = G.p.d("fAa");
        saa = G.p.d("faa");
        popSize = G.p.i("popSize");
        
        generations = G.p.i("generations");
        
        long seed = G.p.l("seed",-1);
        random = seed >= 0 ? new Random(seed) : new Random();
        
        frequency = DoubleStream
                .generate(() -> G.p.d("initFreq"))
                .parallel()
                .limit(G.p.i("populations"))
                .toArray();
    }
    
    public static void run(Consumer<Generation> consumer){
        for(int run=0; run<G.p.i("runs"); run++){
            init();
            for(int gen=0; gen<generations; gen++)
                consumer.accept(nextGeneration(run+1, gen + 1));
        }
    }
    
    public static void _run(Consumer<Generation> consumer){
       IntStream
               .range(0,G.p.i("runs"))
               .forEach( run -> {
                   init();
                   IntStream
                           .range(0, generations)
                           .forEach(gen -> consumer.accept(nextGeneration(run+1, gen + 1)) );
               });
        
    }
    
    public static Generation nextGeneration(int r, int g){
        update();
        return generation.set(r, g,  frequency);
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
