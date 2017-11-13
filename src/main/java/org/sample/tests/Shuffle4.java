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

public class Shuffle4 {
    /**
     * Попробуем другой алгоритм:
     * Будем брать карты по очереди и размещать их в случайных местах массива.
     * Берем карту,
     * выкидываем случайное число с индексом в массиве,
     * проверяем что в массиве в этом месте 0 и записываем туда код карты.
     * Если там не 0 (уже есть карта), то записываем в следующую позицию.
     * К массиву обращаемся как к циклическому буфферу.
     * Диапазон случайных чисел на каждом шаге уменьшаем на 1 (нам уже нужно меньше возможных вариантов индекса).
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
        int randomRange = 36;
        for (int i=6; i<42; i++){
            int randomIndex = random.nextInt(randomRange);
            while (cards[randomIndex] != 0){
                randomIndex = (randomIndex + 1) % 36;
            }
            cards[randomIndex] = i;
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
