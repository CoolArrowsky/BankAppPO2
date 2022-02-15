package put.bankapp.models;

import put.bankapp.utils.Connect;
import put.bankapp.utils.Helper;
import put.bankapp.utils.State;

import java.sql.Connection;
import java.util.Random;


public class CardID {
    private String cardNumber;
    private String pin;
    private final static String cardPrefix = "400000";
    private final static Random rand = new Random();

    public CardID() {
        generateCardNumber();
        generatePIN();
    }

    public CardID(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPin() {
        return pin;
    }

    private void generateCardNumber() {
        while (true) {
            int firstHalf = rand.nextInt(100000);
            int secondHalf = rand.nextInt(10000);
            int numberCount;
            numberCount = Helper.numberLength(firstHalf) + Helper.numberLength(secondHalf);
            int howManyZeros = 9 - numberCount;
            String zeros;

            if (howManyZeros == 0) {
                zeros = "";
            } else {
                zeros = Helper.zerosToAdd(howManyZeros);
            }

            String cardNumber = String.format("%s%s%d%d", cardPrefix, zeros, firstHalf, secondHalf);
            int lastDigit = luhnAlgorithm(cardNumber);
            cardNumber = String.format("%s%d", cardNumber, lastDigit);
            //check if there is same number in db
            Connection connection = Connect.getConn();
            boolean isInDBS = State.checkIfValueExists(connection, cardNumber);
            Connect.deleteConn(connection);

            if (!isInDBS) {
                this.cardNumber = cardNumber;
                break;
            }
        }
    }

    /*
    public static boolean checkLuhnAlgorithm(String cardNumber){
        try {
            String cardNumberWithNoLastLetter = cardNumber.substring(0, cardNumber.length() - 1);
            int lastNumber = luhnAlgorithm(cardNumberWithNoLastLetter);
            int x = Integer.parseInt(cardNumber.substring(cardNumber.length()-1));
            if(lastNumber==x){
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    */
    private static int luhnAlgorithm(String cardNumber) {
        int[] intArray = Helper.stringToIntArray(cardNumber);
        multiplyOddDigits(intArray);
        substractOverNine(intArray);
        return getLastDigit(intArray);
    }

    private static void multiplyOddDigits(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if ((i + 1) % 2 == 1) {
                array[i] *= 2;
            }
        }
    }

    private static void substractOverNine(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] > 9) {
                array[i] -= 9;
            }
        }
    }

    private static int getLastDigit(int[] array) {
        int sum = 0;
        for (int j : array) {
            sum += j;
        }
        int rest = sum % 10;
        if (rest == 0) {
            return 0;
        } else {
            return 10 - rest;
        }
    }

    private void generatePIN() {
        int pin = rand.nextInt(10000);
        int numberCount = Helper.numberLength(pin);
        int howManyZeros = 4 - numberCount;
        String stringPin = Integer.toString(pin);
        if (howManyZeros == 0) {
            this.pin = stringPin;
        } else {
            String zeros = Helper.zerosToAdd(howManyZeros);
            this.pin = String.format("%s%s", zeros, stringPin);
        }
    }


}
