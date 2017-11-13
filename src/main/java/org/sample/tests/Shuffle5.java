package org.sample.tests;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Shuffle5 {
    /**
     * Алгоритм  Fisher–Yates shuffle или Knuth shuffle.
     * Перемешивает массив.
     * -- To shuffle an array a of n elements (indices 0..n-1):
     * for i from 0 to n−2 do
     * j ← random integer such that i ≤ j < n
     * exchange a[i] and a[j]
     * https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
     * @return массив с номерами карт 6-10, 11-Валет, 12-Дама, 13-Король, 14-Туз
     * + 0 пики, +9 крести, +18 бубны, +27 черви
     * 38 - валет червей: 38-27 = 11
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public static int[] shuffle(){
        int[] cards = new int[36];
        //заполняем массив картами
        for (int i=0; i<36; i++){
            cards[i] = i + 6;
        }
        Date date = new Date();
        long time = date.getTime();
        Random random = new Random(time);

        for (int i=35; i>=0; i--){
            //j ← random integer such that i ≤ j < n
            int j = (int)(random.nextFloat() * (36 - i) + i);
            //exchange a[i] and a[j]
            int t = cards[i];
            cards[i] = cards[j];
            cards[j] = t;
        }
        return cards;
    }

    public static void main(String[] args) throws Exception {
        /*int[] cards = shuffle();
        Util.printCards(cards);*/
        Options opt = new OptionsBuilder()
                .include(Shuffle1.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}
