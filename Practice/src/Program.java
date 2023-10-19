
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Program {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //calculateEvenNumbers(scanner);
        //largestNumInArray();
        //reverseString(scanner);
        averageOfNumsInArray();
    }

    // calculate the sum of all even numbers from 1 - n
    public static void calculateEvenNumbers(Scanner scanner) {

        System.out.println("Enter a number:");
        int num = scanner.nextInt();
        int sum = 0;

        for (int i = 0; i < num; i++) {
            if (i % 2 == 0) {
                sum += i;
            }
        }
        System.out.println("Sum is: " + sum);
    }

    // generate an array and find the highest number in that array
    public static void largestNumInArray() {

        int[] arr = new int[10];
        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(1, 101);

        }

        System.out.println(Arrays.toString(arr));

        for (int j = 0; j < arr.length; j++) {
            if (arr[j] > counter) {
                counter = arr[j];
            }
        }
        System.out.println("Highest number in the array is: " + counter);

    }

    // takes a input and reverses a string
    public static void reverseString(Scanner scanner) {

        System.out.println("Enter a string:");
        String userInput = scanner.nextLine();
        String result = "";
        for (int i = userInput.length() - 1; i >= 0; i--) {
            result += userInput.charAt(i);
        }
        System.out.println(result);
    }

    // average of numbers in array
    public static void averageOfNumsInArray() {

        int[] arr = new int[10];
        double average = 0;
        int temp = 0;

        for (int i = 0; i < arr.length; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(1, 6);
        }

        System.out.println(Arrays.toString(arr));

        for (int j = 0; j < arr.length; j++) {
            temp += arr[j];
        }

        average = (double) temp / 10;
        System.out.println("Average: " + average);
    }
}
