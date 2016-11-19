package zeppelin.popg;


import zeppelin.Parameter;
import java.util.Random;
import java.util.function.Consumer;
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
    
    static int run;
    static int gen;
    static double[] frequency;
    static Random random;
    
    static double A_a;
    static double a_A;
    static double sAA;
    static double sAa;
    static double saa;
    static int popSize;
    
    public static void init(){
        int populations = G.p.i("populations");
        double freq = G.p.d("initFreq");
        
        frequency = new double[populations];
        for(int i=0; i<frequency.length; i++)
            frequency[i] = freq;
    }
    
    public static void run(Consumer<Generation> consumer){
       Parameter p = new Parameter();
       IntStream
               .range(0,p.i("runs"))
               .forEach( run -> {
                   init();
                   IntStream
                           .range(0, p.i("generations"))
                           .forEach(gen -> consumer.accept(nextGeneration(run+1, gen + 1, p)) );
               });
        
    }
    
    public static Generation nextGeneration(int r, int g, Parameter p){
        run = r;
        gen = g;
        A_a = p.d("A_a");
        a_A = p.d("a_A");
        sAA = p.d("fAA");
        sAa = p.d("fAa");
        saa = p.d("faa");
        popSize = p.i("popSize");
        
        long seed = p.l("seed",-1);
        random = seed >= 0 ? new Random(seed) : new Random();
        
        update();
        
        return generation.set(r, g,  frequency);
    }
    
    private static  int binomial(int n, double pp){
        return IntStream
                .range(0,n)
                .map(x -> random.nextFloat()<pp ? 1: 0)
                .sum();
    }
    
    protected static void update(){
        for(int pop=0; pop<frequency.length; pop++){
            double p = frequency[pop];
            p = (1 - A_a) * p + a_A * (1 - p);

            double q = 1 - p;
            double w = (p * p * sAA) + (2.0 * p * q * sAa) + (q * q * saa);
            double pp1 = (p * p * sAA) / w;
            double pp2 = (2.0 * p * q * sAa) / w;

            int nx = binomial(popSize, pp1);
            int ny = pp1 < 1.0 && nx < popSize ? binomial(popSize - nx, (pp2 / (1.0 - pp1))) : 0;

            frequency[pop] = ((nx * 2.0) + ny) / (2.0*popSize);
        }
    }
    
    
    public static String getString(){
        StringBuilder sb = new StringBuilder();
        sb.append(run).append("\t").append(gen).append("\t").append(frequency[0]);
        for(int i=1; i<frequency.length; i++)
            sb.append("\t").append(frequency[i]);
        return sb.toString();
    }
}
