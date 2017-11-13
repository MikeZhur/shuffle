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

public class Shuffle3 {
    /**
     * Shuffle2 оказался чуть медленнее Shuffle1. Пробуем улучшить. Откажемся от HashSet
     * Используем HashSet для хранения кодов карт.
     * Будем использовать массив boolean где для каждого индекса, соответсвующее выпавшей карте будем сохранять true.
     * Для быстрого определения что такая карта уже есть.
     * @return массив с номерами карт 6-10, 11-Валет, 12-Дама, 13-Король, 14-Туз
     * + 0 пики, +9 крести, +18 бубны, +27 черви
     * 38 - валет червей: 38-27 = 11
     */
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public static int[] shuffle(){
        int[] cards = new int[36];
        //сюда будем писать true если карта с нужным индексом уже есть
        boolean[] cardsState = new boolean[36];
        Date date = new Date();
        long time = date.getTime();
        Random random = new Random(time);
        Set<Integer> cardSet = new HashSet<>(36);
        for (int i=0; i<36; i++) {
            int cardCode = generateCardCode(random);
            //повторяем пока не получим значение, которого нет в сете
            while (cardsState[cardCode - 6]) {
                cardCode = generateCardCode(random);
            }
            cards[i] = cardCode;
            cardsState[cardCode - 6] = true;
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
                .include(Shuffle3.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
