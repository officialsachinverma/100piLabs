package in.co.sachinverma.task;

import java.util.ArrayList;
import java.util.HashMap;

public class InterviewTask {

    static String[] ones = {"", "one", "two","three","four","five","six", "seven", "eight",
            "nine", "ten", "elven","twelve","thirteen","fourteen","fifteen","sixteen",
            "seventeen", "eighteen", "nineteen"};

    static String[] tens = {"", "", "twenty","thirty","fourty","fivety","sixty", "seventy", "eighty",
            "ninety"};

    public static void main(String[] args){
        /*System.out.println("i am sachin and i am a android developer.");
        String input = "i am sachin and i am a android developer";
        //ArrayList<HashMap<String, Integer>> wordsCountArrayList = new ArrayList<>();
        HashMap<String, Integer> hashMap = new HashMap<>();
        String[] words = input.split(" ");
        for (String str: words){
            if (hashMap.containsKey(str)){
                int count  = hashMap.get(str);
                hashMap.put(str, ++count);
            } else {
                hashMap.put(str, 1);
            }
        }
        System.out.println(hashMap.toString());*/

        // int into words 456

        int input = 9999;
        String result = convert(input);
        System.out.println(result);


    }

    static String convert(int number){ // 456/10 || 456%10 so on
        String numberInWords = "";

        if (number < 0){
            return numberInWords = "";
        }

        if (number < 20) { // ones 7
            return numberInWords = ones[number];
        }
        if (number < 100) { // tens
            return numberInWords = tens[number/10] + ((number%10 != 0) ? " " : "") + ones[number%10]; // 50 56
        }
        if (number < 1000) { // hundred
//            numberInWords = ones[number/100] + " hundred " + tens[number%100] + " " + ones[(number - (number/100))/10%10]; // 456/100 four hundred
            return numberInWords = ones[number/100] + " hundred " + convert(number%100); // 456/100 four hundred 456%100 56
        }
        if (number < 10000){ // thousand
            return numberInWords = ones[number/1000] + " thousand " + convert(number%1000); // 5456/1000
        }
        return numberInWords;
    }

}
