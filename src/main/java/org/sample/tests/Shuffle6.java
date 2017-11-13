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

public class Shuffle6 {
    /**
     * Алгоритм  Fisher–Yates shuffle или Knuth shuffle.
     * Перемешивает массив.
     *
     * To simultaneously initialize and shuffle an array,
     * a bit more efficiency can be attained by doing an "inside-out"
     * version of the shuffle.
     * In this version, one successively places element number i
     * into a random position among the first i positions in the array,
     * after moving the element previously occupying that position to position i.
     * In case the random position happens to be number i, this "move" (to the same place)
     * involves an uninitialised value, but that does not matter, as the value is then immediately
     * overwritten. No separate initialization is needed, and no exchange is performed.
     * In the common case where source is defined by some simple function, such as the
     * integers from 0 to n − 1, source can simply be replaced with the function since source
     * is never altered during execution.
     *
     * To initialize an array a of n elements to a randomly shuffled copy of source, both 0-based::
     * for i from 0 to n − 1 do
     * j ← random integer such that 0 ≤ j ≤ i
     * if j ≠ i
     * a[i] ← a[j]
     * a[j] ← source[i]
     * @return массив с номерами карт 6-10, 11-Валет, 12-Дама, 13-Король, 14-Туз
     * + 0 пики, +9 крести, +18 бубны, +27 черви
     * 38 - валет червей: 38-27 = 11
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public static int[] shuffle(){
        int[] cards = new int[36];

        Date date = new Date();
        long time = date.getTime();
        Random random = new Random(time);

        for (int i=0; i<36; i++){
            //j ← random integer such that i ≤ j < n
            int j = random.nextInt(i+1);
            if (i != j){
                cards[i] = cards[j];
            }
            cards[j] = i + 6;
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
