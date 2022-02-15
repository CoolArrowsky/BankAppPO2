package put.bankapp.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Helper {
    public static int numberLength(long number) {
        int length = 1;
        while (true) {
            if (number >= 10000) {
                length += 4;
                number /= 10000;
            }
            if (number >= 100) {
                length += 2;
                number /= 100;
            }
            if (number >= 10) {
                length += 1;
                number /= 10;
            }
            if (number < 10) {
                return length;
            }
        }
    }

    public static int numberLength(String number) {
        return number.length();
    }

    public static String zerosToAdd(int number) {
        return new String(new char[number]).replace('\0', '0');
    }

    public static int[] stringToIntArray(String str) {
        int[] array = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            array[i] = Character.digit(str.charAt(i), 10);
        }
        return array;
    }

    public static double roundNumber(double number, int decimals) {
        if (decimals < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(number);
        bigDecimal = bigDecimal.setScale(decimals, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

}
