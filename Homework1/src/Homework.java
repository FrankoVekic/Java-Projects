
import java.util.Scanner;

public class Homework {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int[] counters = new int[5];

        System.out.println("Give me a string:");

        String userInput = scanner.nextLine().toLowerCase();

        // looping through the given string
        for (int i = 0; i < userInput.trim().length(); i++) {

            // keeping track of each char/vowel that is written in userInput
            switch (userInput.charAt(i)) {
                case 'a':
                    counters[0]++;
                    break;
                case 'e':
                    counters[1]++;
                    break;
                case 'i':
                    counters[2]++;
                    break;
                case 'o':
                    counters[3]++;
                    break;
                case 'u':
                    counters[4]++;
                    break;
            }

        }

        int vowels = 0;

        for (int j = 0; j < counters.length; j++) {
            vowels += counters[j];
        }
        System.out.println("Number of vowels: " + vowels);

        //printing out each vowel and its counter if it was written in the string
        if (counters[0] > 0) {
            System.out.println("a: " + counters[0]);
        }
        if (counters[1] > 0) {
            System.out.println("e: " + counters[1]);
        }
        if (counters[2] > 0) {
            System.out.println("i: " + counters[2]);
        }
        if (counters[3] > 0) {
            System.out.println("o: " + counters[3]);
        }
        if (counters[4] > 0) {
            System.out.println("u: " + counters[4]);
        }

        //printing the result with compare method
        if (compare(6, 1)) {
            System.out.println("First number is bigger");
        } else {
            System.out.println("First number is smaller or same as second number");
        }
    }

    // method that compares two numbers and returns a boolean value
    public static boolean compare(int a, int b) {

        return a > b;
    }
}
