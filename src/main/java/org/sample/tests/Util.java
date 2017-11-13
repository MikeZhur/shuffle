package org.sample.tests;

import java.util.Arrays;

public class Util {
    public static void printCards(int[] cards){
        System.out.println("suffled");
        for(int cardCode : cards){
            System.out.println(decodeCard(cardCode));
        }
        System.out.println("------------");
        System.out.println("sorted");
        Arrays.sort(cards);
        for(int cardCode : cards){
            System.out.println(decodeCard(cardCode));
        }
    }

    private static String decodeCard(int cardCode){
        int stripe = (cardCode - 6) / 9;
        int cardValue = cardCode - stripe*9;
        StringBuilder sb = new StringBuilder();
        switch (stripe) {
            case 0:
                sb.append("S");
                break;
            case 1:
                sb.append("C");
                break;
            case 2:
                sb.append("D");
                break;
            case 3:
                sb.append("H");
                break;
        }
        if (cardValue < 11){
            sb.append(cardValue);
        } else{
            switch (cardValue){
                case 11:
                    sb.append("V");
                    break;
                case 12:
                    sb.append("D");
                    break;
                case 13:
                    sb.append("K");
                    break;
                case 14:
                    sb.append("T");
                    break;
            }
        }
        return sb.toString();
    }
}
