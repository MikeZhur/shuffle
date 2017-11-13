package org.sample.tests;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Shuffle2 {
    /**
     * Недостаток SimpleSuffle в том что в конце заполнения колоды очень много времени тратится
     * на подбор карты, которой еще нет в колоде. При этом для проверки есть ли она каждый раз выполняется
     * поиск почти по всему массиву.
     * Используем HashSet для хранения кодов карт.
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
        Set<Integer> cardSet = new HashSet<>(36);
        for (int i=0; i<36; i++) {
            int cardCode = generateCardCode(random);
            //повторяем пока не получим значение, которого нет в сете
            while (!cardSet.add(cardCode)) {
                cardCode = generateCardCode(random);
            }
            cards[i] = cardCode;
        }
        return cards;
    }

    private static int generateCardCode(Random random){
        //выкидываем случайное число значения карты от 0 (включительно) до 8
        int cardValue = random.nextInt(9);
        //выкидываем случайное число для масти от 0 до 3
        int stripe = random.nextInt(4);
        return cardValue + 6 + 9 * stripe;
    }

    public static void main(String[] args) throws Exception{
        /*int[] cards = shuffle();
        Util.printCards(cards);*/
        Options opt = new OptionsBuilder()
                .include(Shuffle2.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
