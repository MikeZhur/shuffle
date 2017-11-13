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

/**
 * Перемешивание "в лоб"
 */
public class Shuffle1 {
    /**
     * Создаем массив размерностью 36,
     * проходим цикл по всем элементам массива,
     * в цикле выкидываем случайное число, соответствующее номеру карты,
     * выкидваем случайное число, соответсвующее масти,
     * проверяем есть ли такая карта,
     * если нет - добавляем.
     * выкидываем числа пока не выпадет карта, которой нет
     * повторяем цикл.
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
            boolean found = false;
            int cardCode;
            do {
                found = false;
                //выкидываем случайное число значения карты от 0 (включительно) до 8
                int cardValue = random.nextInt(9);
                //выкидываем случайное число для масти от 0 до 3
                int stripe = random.nextInt(4);
                cardCode = cardValue + 6 + 9 * stripe;
                //ищем есть ли у нас уже такой код карты в массиве?
                for (int n = 0; n < i; n++) {
                    if (cards[n] == cardCode) {
                        //уже есть такая карта
                        found = true;
                        break;
                    }
                }
            }while (found);
            if (!found){
                cards[i] = cardCode;
            }
        }
        return cards;
    }

    public static void main(String[] args) throws Exception{
        /*int[] cards = shuffle();
        Util.printCards(cards);*/
        Options opt = new OptionsBuilder()
                .include(Shuffle1.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
